import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import groovy.io.FileType as FileType
import org.apache.commons.io.FileUtils as FileUtils
import groovy.time.*

// 02/16/21	Modified to allow for changing priority numbers and messages in the validation csv file
// 	- Replaced the removeExcerpts function with parseFile and modified the test to find the base file rows in the validation csv to match on
//		Chapter AND Verse AND Line AND Row ID AND Details AND Char Pos AND (Priority OR Message)

dirName = (('/Users/' + GlobalVariable.pcUser) + '/Downloads')
println(dirName)

baseDir = (GlobalVariable.projectPath + '/Data Files/')

levelHigh = 800

levelMedium = 600

levelTestFile = (baseDir + 'Validation-Test_[myLevel]_Level.tsv.csv')

testFiles = ['en_tn_50-EPH.tsv', 'en_tn_57-TIT.tsv', 'en_tn_42-MRK.tsv', 'en_tn_43-LUK.tsv', 'en_tn_45-ACT.tsv', 'en_tn_46-ROM.tsv'
    , 'en_tn_52-COL.tsv', 'en_tn_15-EZR.tsv', 'en_tn_56-2TI.tsv', 'en_tn_41-MAT.tsv', 'en_tn_16-NEH.tsv', 'en_tn_49-GAL.tsv', 'en_tn_06-JOS.tsv']

// expectedFails holds the list of rows (base 0) in the baseline csv files that currently are expected to fail when tested
//Prior to v1.1.0-rc3
//expectedFails = [('en_tn_57-TIT.tsv') : [1, 2], ('en_tn_43-LUK.tsv') : [0], ('en_tn_46-ROM.tsv') : [0], ('en_tn_15-EZR.tsv') : [
//        0, 1], ('en_tn_56-2TI.tsv') : [0], ('en_tn_41-MAT.tsv') : [0, 1]]
expectedFails = [('en_tn_57-TIT.tsv') : [2], ('en_tn_43-LUK.tsv') : [1,2,3,4],  ('en_tn_45-ACT.tsv') : [1], ('en_tn_46-ROM.tsv') : [0], ('en_tn_15-EZR.tsv') : [0, 1], ('en_tn_56-2TI.tsv') : [
        0], ('en_tn_41-MAT.tsv') : [0, 1], ('en_tn_49-GAL.tsv') : [0], ('en_tn_06-JOS.tsv') : [0]]

//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
start = 0

end = (testFiles.size() - 1)

//end = start
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
errorCount = 0

passCount = 0

testCount = 0

expectedCount = 0

defaultLevel = 'High'

first = true

timings = []

for (def fileNum : (start..end)) {
    errorFlag = false

    testFile = (testFiles[fileNum])

    println('>>>>>>>> Running test on ' + testFile)

    baseFile = (((baseDir + 'Validation-') + testFile) + '_base.csv')

	(basePrioritys, baseErrors, baseMessages) = parseFile(baseFile)
	
    println('testFile:' + testFile)

    vFiles = getValidationFiles(testFile)

    // Get the count of how many validation files already exist for the file being tested
    initSize = vFiles.size()

    // Load the project in tN
    WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : GlobalVariable.validateUser
            , ('$password') : GlobalVariable.validatePassword, ('file') : testFile], FailureHandling.STOP_ON_FAILURE)

    // If first time, verify the default validation level
    if (first) {
        testCount++

        if (!(CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.testValidationLevel'(defaultLevel))) {
            errorCount++

            println('ERROR: Initial validation level is not set to ' + defaultLevel)

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the initial validation level is not set to ' + 
                defaultLevel)
        } else {
            passCount++
        }
		
        first = false
    }
    
    // If Matthew project test for validation levels
    if (fileNum == 9) {
        levels = ['High', 'Medium']

        for (def level : levels) {
            testCount++

            error = false

            retCode = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseValidationLevel'(level)

			(vSize, newPrioritys, newErrors, newMessages, myFile) = runValidation(initSize, testFile)

            if (newContent != '') {
                // newContent will be empty if the validator did not find any errors
                new File(myFile).splitEachLine(',', { def fields ->
                        if ((fields[0]) != 'Priority') {
                            if (((level == 'High') && ((fields[0]).toInteger() < levelHigh)) || ((level == 'Medium') && 
                            ((fields[0]).toInteger() < levelMedium))) {
                                errorCount++

                                error = true

                                println(((('ERROR: Priority ' + (fields[0])) + ' errors were found in a validaiton level ') + 
                                    level) + ' csv file')

                                CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((('Test failed because priority ' + 
                                    (fields[0])) + ' errors were found in a validaiton level ') + level) + ' csv file.')
                            }
                        }
                    })
            }
            
            if (!(error)) {
                passCount++
            }
            
            // This section tests to ensure that the script will find lower level errors by adding them to the validation file
            found = false

            levelFile = levelTestFile.replace('[myLevel]', level)

            println('levelFile: ' + levelFile)

            iFile = new File(levelFile)

            String err = FileUtils.readFileToString(iFile)

            if (newContent != '') {
                oFile = new File(myFile)

                oFile.append(err) // If the validator did not report any errors, then use the level test file as the source
            } else {
                myFile = levelFile
            }
            
            pList = []

            testCount++

            new File(myFile).splitEachLine(',', { def fields ->
                    pList.add(fields[0])
                })

            pList = pList.sort()

            if ((pList[0]) == 'Priority') {
                pList.remove(0)
            }
            
            if (((level == 'High') && ((pList[0]).toInteger() < levelHigh)) || ((level == 'Medium') && ((pList[0]).toInteger() < 
            levelMedium))) {
                passCount++
            } else {
                println(('ERROR: Script did not detect a lower Priority error while testing a validaiton level ' + level) + 
                    ' csv file')

                CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('The script did not detect a lower Priority error while testing a validaiton level ' + 
                    level) + ' csv file.')

                errorCount++
            }
            
            initSize = vSize
        }
    }
    
    // Set validation level to low
    retCode = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseValidationLevel'('low')

    timeStart = new Date()

    // Run the validation
	(vSize, newPrioritys, newErrors, newMessages, myFile) = runValidation(initSize, testFile)

    timeEnd = new Date()

    TimeDuration timeElapsed = TimeCategory.minus(timeEnd, timeStart)

    timings.add(((testFiles[fileNum]) + ' : ') + timeElapsed)

    if (newPrioritys.size() == 0) {
        // The validator did not find any errors. Continue with next file.
        msg = (('The validator found no errors in ' + testFile) + '.')

        println(msg)

        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)

        continue
    }
    
    fixedRows = []
		
	for (def row : (0 .. baseErrors.size()-1)) {
		fnd = false
		int[] found = newErrors.findIndexValues({
			it == baseErrors[row]
		})
	//	println(found)
		if (found.size() > 0) {
			for (def i : (0 .. found.size()-1)) {
				if (newPrioritys[found[i]] == basePrioritys[row] || newMessages[found[i]] == baseMessages[row]) {
					fnd = true
				}
			}
		}
		if (!fnd) {
			fixedRows.add(row)
			msg = ((((('The error in row ' + (row + 1)) + ' in ') + testFile) + '_base.csv') + ' was not found by the validator.')
			println(msg)
			CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)
		}
	}
	
	println('Fixed base rows in ' + testFile + ' = ' + fixedRows)

    // Show the additional required columns
	
	columns = ['columns_ID', 'columns_OrigQuote']
	CustomKeywords.'unfoldingWord_Keywords.ManageTNColumns.toggleColumn'(columns)

    if (fileNum == 0) {
        // Fix the en_tn_50-EPH.tsv validation errors
        if (!fixedRows.contains(0)) {
            u2bwBaseQuote = 'ἑνὶ…ἐκάστῳ ἡμῶν ἐδόθη ἡ χάρις'

            u2bwNewQuote = 'ἑνὶ…ἑκάστῳ ἡμῶν ἐδόθη ἡ χάρις'
			
			searchFor('u2bw')

            WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), u2bwNewQuote)
        }
        
        if (!fixedRows.contains(1)) {
            abbwBaseQuote = 'ἑνὶ…ἐκάστῳ ἡμῶν ἐδόθη ἡ χάρις'

            abbwNewQuote = 'ἑνὶ…ἑκάστῳ ἡμῶν ἐδόθη ἡ χάρις'
			
			closeAndSearchFor('abbw')

            WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), abbwNewQuote)
			
			WebUI.scrollToPosition(0, 0)
			
			WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_SearchCloseX'))
		
			WebUI.delay(1)
        }
		
    } else if (fileNum == 1) {
        row = 2

        if (!fixedRows.contains(row)) {
            testCount++

            highlighted = 'rgba(255, 255, 0, 1)'

            background = WebUI.getCSSValue(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('myDiv') : 'id("header-1-1-xyz8")'
                        , ('chpt') : '1', ('verse') : '1', ('wordNum') : '17']), 'background-color')

            if (background == highlighted) {
                errorFlag = true

                errorCount++

                prefix = test4Expected(testFile, row)

                println(((prefix + 'ERROR: Validator reports "unable to find original language quote" in ') + testFile) + 
                    ' on ID xyz8 even though it is highlighted. (Issue 572)')

                CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports "unable to find original language quote" in ') + 
                    testFile) + ' on ID xyz8 even though it is highlighted. (Issue 572)')
            } else {
                passCount++
            }
            
			baseErrors.remove(row)
        }
        
        if (!fixedRows.contains(0)) {
            WebUI.click(findTestObject('Page_tCC translationNotes/button_Preview'))

            WebUI.click(findTestObject('Page_tCC translationNotes/text_Introduction to Titus'))

            WebUI.delay(1)

            WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_Introduction to Titus'), Keys.chord(Keys.UP, Keys.UP, 
                    Keys.RIGHT, Keys.RIGHT, Keys.RIGHT, Keys.RIGHT, Keys.RIGHT, Keys.BACK_SPACE))

            WebUI.delay(1)

            WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_Introduction to Titus'), Keys.chord(Keys.DOWN, 
                    Keys.BACK_SPACE, Keys.DOWN, Keys.BACK_SPACE))

            WebUI.delay(1)
        }
        
        if (!fixedRows.contains(1)) {
			searchFor('rtc9')
			
			NewQuote = WebUI.getText(findTestObject('Page_tCC translationNotes/text_SourceOrigQuote_SearchId'))
			
            WebUI.setText(findTestObject('Page_tCC translationNotes/text_OrigQuote_SearchId'), NewQuote)
        }        
        if (!fixedRows.contains(2)) {
//            WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), '')

            xyz8NewQuote = 'εὐσέβειαν'
			
			closeAndSearchFor('xyz8')

            WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), xyz8NewQuote)
        }
    } else if (fileNum == 2) {
		changeFlag = false
        if (!fixedRows.contains(0)) {
			changeFlag = true
			
			columns = ['columns_Occurrence']
			CustomKeywords.'unfoldingWord_Keywords.ManageTNColumns.toggleColumn'(columns)
			
			searchFor('eke3')
			
            WebUI.setText(findTestObject('Page_tCC translationNotes/text_Occurrence_SearchId'), '1')
        }
        
        if (!fixedRows.contains(1)) {
			if (changeFlag) {
				closeAndSearchFor('hm98')
			} else {
				searchFor('hm98')
			}
			
            WebUI.click(findTestObject('Page_tCC translationNotes/text_OrigQuote_SearchId'))

            WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_OrigQuote_SearchId'), Keys.chord(Keys.COMMAND, 
                    Keys.RIGHT))

            WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_OrigQuote_SearchId'), '.)')
        }
    } else if (fileNum == 3) {
		rows = [0, 1, 2, 3, 4]
		
		for (row in rows) {
	        testCount++
	
	        if (!fixedRows.contains(row)) {
	
	            errorFlag = true
	
	            errorCount++
	
	            prefix = test4Expected(testFile, row)
				
				if (row == 0) {
			            println(((prefix + 'ERROR: Validator reports a ' + basePrioritys[row] + ' error in ') + testFile) + ' when the original text snippet is preceded with a double quote. (Issue 574)')
			            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a ' + basePrioritys[row] + ' error in ') + 
			                testFile) + ' when the original text snippet is preceded with a double quote. (Issue 574)')
				} else if (row == 1) {
					println(((prefix + 'ERROR: Validator reports a ' + basePrioritys[row] + ' error in ') + testFile) + ' when the scripture link is correctly formated. (Issue 707)')
					CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a ' + basePrioritys[row] + ' error in ') +
						testFile) + ' when the scripture link is correctly formated. (Issue 707)')
				} else if (row == 2) {
					println(((prefix + 'ERROR: Validator reports a ' + basePrioritys[row] + ' error in ') + testFile) + ' when there is an invisible non-breaking space in the OrigQuote field. (Issue 706)')
					CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a ' + basePrioritys[row] + ' error in ') +
						testFile) + '  when there is an invisible non-breaking space in the OrigQuote field. (Issue 706)')
				} else if (row == 3) {
					println(((prefix + 'ERROR: Validator should not report ' + basePrioritys[row] + ' errors on ID fields that begin with a number as in ' + testFile) + '. (Issue 704)'))
					CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a ' + basePrioritys[row] + ' error in ') +
						testFile) + '  when the ID field begins withn a number. (Issue 704)')
				} else if (row == 4) {
					println(((prefix + 'ERROR: Validator reports a ' + basePrioritys[row] + ' error in ') + testFile) + ' when there is an invisible trailing space in the GLQuote field. (Issue 705)')
					CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a ' + basePrioritys[row] + ' error in ') +
						testFile) + '  when there is an invisible trailing space in the GLQuote field. (Issue 705)')
				}
	        } else {
	            passCount++
	        }
		}
		
    } else if (fileNum == 4) {
        if (!fixedRows.contains(0)) {
			columns = ['columns_Occurrence']
			CustomKeywords.'unfoldingWord_Keywords.ManageTNColumns.toggleColumn'(columns)

			searchFor('p5cd')

            WebUI.delay(1)

            WebUI.setText(findTestObject('Page_tCC translationNotes/text_Occurrence_SearchId'), '1')
        }
		
		row = 1
        if (!fixedRows.contains(row)) {
	        testCount++
	
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)
			
            println(((prefix + 'ERROR: Validator reports a ' + basePrioritys[row] + ' error in ') + testFile) + ' when the " is not followed by a space. (Issue 708)')
            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a ' + basePrioritys[row] + ' error in ') + 
                testFile) + ' when the " is not followed by a space. (Issue 708)')
			
        } else {
            passCount++
        }
		
    } else if (fileNum == 5) {
        testCount++

        row = 0

		if (!fixedRows.contains(row)) {
			
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports a 621 error in ') + testFile) + ' when the original text snippet is followed by an ellipsis.  (Issue 574)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 621 error in ') + 
                testFile) + ' when the original text snippet is followed by an ellipsis. (Issue 574)')
        } else {
            passCount++
        }
        
        continue
    } else if (fileNum == 6) {
        if (!fixedRows.contains(0)) {
			searchFor('x5g8')

            WebUI.delay(1)

            noteText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'))

            noteText = noteText.replace('perfectly together.', 'perfectly together.”')

            WebUI.setText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'), noteText)

            WebUI.delay(1)

            WebUI.scrollToPosition(0, 0)

            WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_SearchCloseX'))
        }
        
        if (!fixedRows.contains(1)) {
			columns = ['columns_GLQuote']
			CustomKeywords.'unfoldingWord_Keywords.ManageTNColumns.toggleColumn'(columns)

            WebUI.delay(1)
			
			searchFor('d39x')

            noteText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_GLQuote_SearchId'))

            noteText = noteText.replace('fulfill it', 'fulfill it.”')

            WebUI.setText(findTestObject('Page_tCC translationNotes/text_GLQuote_SearchId'), noteText)

            WebUI.delay(1)
        }
    } else if (fileNum == 7) {
        errors = []

        testCount++

        row = 0

		if (!fixedRows.contains(row)) {
			
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports a 106 error in ') + testFile) + ' although there is no leading space in OrigQuote. (Issue 601)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 106 error in ') + 
                testFile) + ' although there is no leading space in OrigQuote. (Issue 601)')
        } else {
            passCount++
        }
        
        testCount++

        row = 1

		if (!fixedRows.contains(row)) {
			
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator incorrectly reports a 916 error in ') + testFile) + ' although the OrigQuote exactly matches the highlighted snippet.  (Issue 600)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 916 error in ') + 
                testFile) + ' although the OrigQuote exactly matches the highlighted snippet. (Issue 600)')
        } else {
            passCount++
        }
        
		baseErrors.remove(row)
        
        if (!fixedRows.contains(2)) {
			searchFor('zb3e')
 
            WebUI.delay(1)

            WebUI.setText(findTestObject('Page_tCC translationNotes/text_SupportReference_SearchId'), 'figs-idiom')

            WebUI.delay(1)
        }
    } else if (fileNum == 8) {
        testCount++

        row = 0

		if (!fixedRows.contains(row)) {
			
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports a 95 error in ') + testFile) + ' although there is no leading trailing space in OrigQuote. (Issue 588)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 95 error in ') + 
                testFile) + ' although there is no trailing space in OrigQuote. (Issue 588)')
        } else {
            passCount++
        }
        
		baseErrors.remove(row)

        continue
    } else if (fileNum == 9) {
        errors = []

        testCount++

        row = 0

		if (!fixedRows.contains(row)) {
			
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports a 786 error in ') + testFile) + ' for rows that do not reference scripture. (Issue 603)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 786 error in ') + 
                testFile) + ' for rows that do not reference scripture. (Issue 603)')
        } else {
            passCount++
        }
        
		baseErrors.remove(row)
		
        testCount++

        row = 1

		if (!fixedRows.contains(row)) {
			
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports a 786 error in ') + testFile) + ' for rows that contain multiple links even though one is entered in SupportReference. (Issue 606)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 786 error in ') + 
                testFile) + ' for rows that contain multiple links even though one is entered in SupportReference. (Issue 606)')
        } else {
            passCount++
        }
        		
		baseErrors.remove(row)
		
        if (!fixedRows.contains(2)) {
			searchFor('mxm2')

            oNote = WebUI.getText(findTestObject('Object Repository/Page_tCC translationNotes/text_OccurrenceNote_SearchId'))

            oNote = oNote.replace('[[rc://en/ta/man/translate/figs-ellipsis]]', '[[rc://*/ta/man/translate/figs-ellipsis]]')

            WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OccurrenceNote_SearchId'), oNote)
        }
    } else if (fileNum == 10) {
        // Fix the en_tn_16-NEH.tsv validation errors
        if (!fixedRows.contains(0)) {
			searchFor('j1o7')
			
            WebUI.delay(1)

			oNote = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'))
			
			oNote.replace('(../43/11.md)','(../12/44.md)')

            WebUI.setText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'), oNote)
        }
        
        if (!fixedRows.contains(3)) {
			closeAndSearchFor('y1jl')

			oNote = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'))
			
			oNote.replace('(../05/57.md)','(../07/57.md)')

            WebUI.setText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'), oNote)
        }
		
        if (!fixedRows.contains(4)) {
			closeAndSearchFor('kr99')

			oQuote = WebUI.getText(findTestObject('Page_tCC translationNotes/text_Source_OrigQuote_SearchId'))

            WebUI.setText(findTestObject('Page_tCC translationNotes/text_OrigQuote_SearchId'), oQuote)
        }
		
    } else if (fileNum == 11) {

        testCount++

        row = 0

		if (!fixedRows.contains(row)) {
			
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports a ' + basePrioritys[row] + ' error "Unknown Bible book name in link" in ') + testFile) + '. (Issue 638)')
            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a ' + basePrioritys[row] + ' error "Unknown Bible book name in link" in ') + 
                testFile) + ' . (Issue 638)')
        } else {
            passCount++
        }
		
		WebUI.closeBrowser()
		continue
		
    } else if (fileNum == 12) {

        testCount++

        row = 0

		if (!fixedRows.contains(row)) {
			
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(prefix + 'ERROR: Validator reports a ' + basePrioritys[row] + ' error in ' + testFile + ' even though the snippet is highlighted and there are no other errors in the check. (Issue 714)')
            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(prefix + 'Test failed because the validator reports a ' + basePrioritys[row] + ' error in ' + testFile + ' even though the snippet is highlighted and there are no other errors in the check. (Issue 714)')
        } else {
            passCount++
        }
		WebUI.closeBrowser()
		continue
    }
    if (baseErrors.size() > fixedRows.size()) {
        // Rerun the validation if any of the base file rows still existing in the validation file were fixed
        initSize = vSize

		(vSize, newPrioritys, newErrors, newMessages, myFile) = runValidation(initSize, testFile)
		
        testOutput(baseErrors, myFile)
    }
    
    if (!(errorFlag)) {
        println(('\n=>=>=>=>=>=>=>=>=> No errors were found when processing ' + testFile) + '\n')
    }
    
    // Insert errors into en_tn_50-EPH.tsv
    if (fileNum == 0) {
		columns = ['columns_Occurrence']
		CustomKeywords.'unfoldingWord_Keywords.ManageTNColumns.toggleColumn'(columns)

		searchFor('ab01')

        // Insert 889 error
        WebUI.setText(findTestObject('Page_tCC translationNotes/text_SupportReference_SearchId'), 'figs-doublenegative')

        // Insert 792 error
        WebUI.setText(findTestObject('Page_tCC translationNotes/text_Occurrence_SearchId'), '2')
		
		columns = ['columns_Occurrence']
		CustomKeywords.'unfoldingWord_Keywords.ManageTNColumns.toggleColumn'(columns)

        WebUI.sendKeys(null, Keys.chord(Keys.TAB))

        // Prior to rc-3 this would cause a 450 error
        noteText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'))

        println('noteText0:' + noteText)

        noteText = noteText.replace('(See: [[rc://en/ta/man/translate/figs-doublenegatives]])', '(See: [[rc://*/ta/man/translate/figs-doublenegative]])')

        println('noteText1:' + noteText)

        WebUI.setText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'), noteText)

        noteText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'))

        println('noteText2:' + noteText)

        // Rerun the validation
        initSize = vSize

		(vSize, newPrioritys, newErrors, newMessages, myFile) = runValidation(initSize, testFile)
		
        prioritys = ['889', '886', '450', '792']

        errors = []

        expecteds = ['792']

        for (def priority : prioritys) {
            errors.add(false)
        }
        
        new File(myFile).eachLine({ def line ->
                if (line.contains('ab01')) {
                    for (def p : (0..prioritys.size() - 1)) {
                        if (line.contains(prioritys[p])) {
                            (errors[p]) = true
                        }
                    }
                }
            })

        for (def i : (0..prioritys.size() - 1)) {
            testCount++

            prefix = ''

            if ((prioritys[i]) in expecteds) {
                prefix = '<< EXPECTED >> '

                expectedCount++
            }
            
        }
    }
    
    WebUI.closeBrowser()
}

// Add the timings to a global list to be output to the log file when running from a test suite
GlobalVariable.timings = timings

header = true

println('<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>')

timings.each({ def et ->
        if (header) {
            println('VALIDATION PROCESSING TIME')

            header = false
        }
        
        println('    ' + et)
    })

println(('\n' + testCount) + ' tests were run.')

println(passCount + ' tests passed.')

println(((errorCount + ' tests failed; ') + expectedCount) + ' expected.')

println('<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>')

GlobalVariable.testCount = testCount

GlobalVariable.passCount = passCount

GlobalVariable.errorCount = errorCount

GlobalVariable.expectedErrors = expectedCount

GlobalVariable.scriptRunning = false

WebUI.closeBrowser() 

def parseFile (aFile) {
	errs = []
	priors = []
	msgs = []
	
//	println(aFile)
	File file = new File(aFile)
	file.withReader { reader ->
		while ((line = reader.readLine()) != null) {
//			println(line)
			int[] commas = line.findIndexValues({
				it == ','
			})
			
			count = commas.size()
			if (count < 7) {
				println('#################### not enough commas in line ' + line)
			}
			
			err = line.substring(commas[0]+1, commas[6])
			errs.add(err)
			
			prior = line.substring(0,commas[0])
//			println('prior:' + prior)
			priors.add(prior)
			
			if (count > 8) {
				msg = line.substring(commas[7]+1, commas[8])
			} else {
				msg = line.substring(commas[7]+1)
			}
//			println('msg:' + msg)
			msgs.add(msg)

//			println(prior + ':' + err + ':' + msg)
			
		}
	}
	return [priors,errs,msgs]
}

def test4Expected(def testFile, def row) {
    retCode = ''

    if (expectedFails.containsKey(testFile)) {
        rows = expectedFails.get(testFile)

        if (row in rows) {
            expectedCount++

            retCode = '<< EXPECTED >> '
        }
    }
    
    return retCode
}

def getValidationFiles(def testFile) {
    println('dirName1:' + dirName)

    List files = new File(dirName).list()

    vFiles = []

    files.each({ def file ->
            if (file.contains('Validation-' + testFile) && file.substring(file.length()-3) == 'csv') {
                vFiles.add(file)
            }
        })

    return vFiles
}

def runValidation(def initSize, def testFile) {
    WebUI.scrollToPosition(0, 0)

    WebUI.waitForElementClickable(findTestObject('Page_tCC translationNotes/button_validate'), 30)

    WebUI.click(findTestObject('Page_tCC translationNotes/button_validate'))

    WebUI.waitForElementPresent(findTestObject('Object Repository/Page_tCC translationNotes/alert_ValidationRunning'), 5)

    vSize = initSize

    myFile = ''

    newContent = ''

    noFile = false

    while ((vSize <= initSize) && !(noFile)) {
        vFiles = getValidationFiles(testFile)

        vSize = vFiles.size()

        if (!(WebUI.verifyAlertNotPresent(2, FailureHandling.OPTIONAL))) {
            noFile = true
        }
    }
    
    if (noFile) {
        WebUI.acceptAlert(FailureHandling.OPTIONAL)
    } else {
        vFiles = vFiles.toSorted()

        myFile = (vFiles[(vFiles.size() - 1)])

        println('myFile:' + myFile)

        println('dirName2:' + dirName)

        myFile = ((dirName + '/') + myFile)

		(newPrioritys, newErrors, newMessages) = parseFile(myFile)
		
    }
    
	return [vSize, newPrioritys, newErrors, newMessages, myFile]
}

def testOutput(def baseLines, def myFile) {
    newLines = []

    new File(myFile).eachLine({ def line ->
            newLines.add(line)
        })

    row = 0

    baseLines.each({ def line ->
            println('testing line-' + line)

            testCount++

            if (line in newLines) {
                errorFlag = true

                errorCount++

                prefix = test4Expected(testFile, row)

                println(((((prefix + 'ERROR: Validation file line [') + line) + ']  in ') + testFile) + ' still exists after being fixed')

                CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((((prefix + 'Test failed because validation file line [') + 
                    line) + ']  in ') + testFile) + ' still exists after being fixed.')
            } else {
                passCount++
            }
            
            row++
        })
}

def searchFor(value) {
	WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))
	
	WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), value)	
}

def closeAndSearchFor(value) {
	WebUI.scrollToPosition(0, 0)
	
	WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_SearchCloseX'))

	WebUI.delay(1)
	
	searchFor(value)
}