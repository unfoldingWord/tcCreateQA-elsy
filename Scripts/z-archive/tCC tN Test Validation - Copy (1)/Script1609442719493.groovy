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

dirName = (('/Users/' + GlobalVariable.pcUser) + '/Downloads')

baseDir = (GlobalVariable.projectPath + '/Data Files/')

testFiles = ['en_tn_50-EPH.tsv', 'en_tn_57-TIT.tsv', 'en_tn_42-MRK.tsv', 'en_tn_43-LUK.tsv', 'en_tn_45-ACT.tsv', 'en_tn_46-ROM.tsv'
    , 'en_tn_52-COL.tsv', 'en_tn_15-EZR.tsv', 'en_tn_56-2TI.tsv', 'en_tn_41-MAT.tsv']

// expectedFails holds the list of rows (base 0) in the baseline csv files that currently are expected to fail when tested
expectedFails = [('en_tn_57-TIT.tsv') : [1, 2], ('en_tn_43-LUK.tsv') : [0], ('en_tn_46-ROM.tsv') : [0], ('en_tn_15-EZR.tsv') : [
        0, 1], ('en_tn_56-2TI.tsv') : [0], ('en_tn_41-MAT.tsv') : [0, 1]]

start = 0

end = (testFiles.size() - 1)

//end = start

errorCount = 0

passCount = 0

testCount = 0

expectedCount = 0

for (def fileNum : (start..end)) {
    errorFlag = false

    testFile = (testFiles[fileNum])

    println('>>>>>>>> Running test on ' + testFile)

    baseFile = (((baseDir + 'Validation-') + testFile) + '_base.csv')

    bFile = new File(baseFile)

    baseContent = bFile.text

    println('baseContent:' + baseContent)

    // Get the count of how many validation files already exist for the file being tested
    println('testFile:' + testFile)

    vFiles = getValidationFiles(testFile)

    initSize = vFiles.size()

    // Load the project in tN
    WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : GlobalVariable.validateUser
            , ('$password') : GlobalVariable.validatePassword, ('file') : testFile], FailureHandling.STOP_ON_FAILURE)

    // Run the validation
    (vSize, newContent) = runValidation(initSize, testFile)

    //    println('newContent:' + newContent)
    // Test to see if the validation results are what was expected based on the originally saved validation file
    baseLines = []

    new File(baseFile).eachLine({ def line ->
            baseLines.add(line)
        })

    println(baseLines)

    newLines = []

    new File(myFile).eachLine({ def line ->
            newLines.add(line)
        })

    //	println(newLines)
    errorFlag = false

    //	for (line in baseLines) {
    baseLines.each({ def line ->
            if (!(line) in newLines) {
                errorFlag = true
            }
        })

    testCount++

    if (errorFlag) {
        errorCount++

        println(('ERROR: Initial validation content  in ' + testFile) + ' does not include the expected errors')

        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because the initial validation content  in ' + 
            testFile) + ' does not match the base content.')

        WebUI.closeBrowser()

        continue
        
        WebUI.closeBrowser()
    } else {
        passCount++

        println('Initial content matches the base content')
    }
    
    // Show the additional required columns
    WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

    WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'ID']))

    WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'OrigQuote']))

    WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

    if (fileNum == 0) {
        // Fix the en_tn_50-EPH.tsv validation errors
        u2bwBaseQuote = 'ἑνὶ…ἐκάστῳ ἡμῶν ἐδόθη ἡ χάρις'

        u2bwNewQuote = 'ἑνὶ…ἑκάστῳ ἡμῶν ἐδόθη ἡ χάρις'

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'u2bw')

        WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), u2bwNewQuote)

        abbwBaseQuote = 'ἑνὶ…ἐκάστῳ ἡμῶν ἐδόθη ἡ χάρις'

        abbwNewQuote = 'ἑνὶ…ἑκάστῳ ἡμῶν ἐδόθη ἡ χάρις'

        WebUI.scrollToPosition(0, 0)

        WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_SearchCloseX'))

        WebUI.delay(1)

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'abbw')

        WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), abbwNewQuote) // Fix the en_tn_57-TIT.tsv validation errors
        // Fix the en_tn_42-MRK.tsv validation errors
        // Check for the invalid error in en_tn_43-LUK.tsv 
        // Fix the en_tn_45-ACT.tsv validation errors
        // Check for the invalid error in en_tn_46-ROM.tsv 
        // Fix the en_tn_52-COL.tsv validation errors
        // Check for the invalid errors in en_tn_15-EZR.tsv
        //		errors.add(error)
        //		
        // Fix the en_tn_15-EZR.tsv figs-idioms error in zb3e
        // Check for the invalid errors en_tn_56-2TI.tsv
        // Check for the invalid errors en_tn_41-MAT.tsv
    } else if (fileNum == 1) {
        testCount++

        row = 2

        error = (baseLines[row])

        highlighted = 'rgba(255, 255, 0, 1)'

        background = WebUI.getCSSValue(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('myDiv') : 'id("header-1-1-xyz8")'
                    , ('chpt') : '1', ('verse') : '1', ('wordNum') : '17']), 'background-color')

        if (newContent.contains(error) && (background == highlighted)) {
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports "unable to find original language quote" in ') + testFile) + ' on ID xyz8 even though it is highlighted. (Issue 572)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports "unable to find original language quote" in ') + 
                testFile) + ' on ID xyz8 even though it is highlighted. (Issue 572)')
        } else {
            passCount++
        }
        
        baseLines.removeElement(error)

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Preview'))

        WebUI.click(findTestObject('Page_tCC translationNotes/text_Introduction to Titus'))

        WebUI.delay(1)

        WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_Introduction to Titus'), Keys.chord(Keys.UP, Keys.UP, 
                Keys.RIGHT, Keys.RIGHT, Keys.RIGHT, Keys.RIGHT, Keys.RIGHT, Keys.BACK_SPACE))

        WebUI.delay(1)

        WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_Introduction to Titus'), Keys.chord(Keys.DOWN, Keys.BACK_SPACE, 
                Keys.DOWN, Keys.BACK_SPACE))

        WebUI.delay(1)

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Preview'))

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'rtc9')

        NewQuote = WebUI.getText(findTestObject('Object Repository/Page_tCC translationNotes/text_SourceOrigQuote_SearchId'))

        WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), NewQuote)

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), '')

        xyz8NewQuote = 'εὐσέβειαν'

        WebUI.scrollToPosition(0, 0)

        WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_SearchCloseX'))

        WebUI.delay(1)

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'xyz8')

        WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), xyz8NewQuote)
    } else if (fileNum == 2) {
        WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

        WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'Occurrence']))

        WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'eke3')

        WebUI.setText(findTestObject('Page_tCC translationNotes/text_Occurrence_SearchId'), '1')

        WebUI.scrollToPosition(0, 0)

        WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_SearchCloseX'))

        WebUI.delay(1)

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'hm98')

        WebUI.click(findTestObject('Page_tCC translationNotes/text_OrigQuote_SearchId'))

        WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_OrigQuote_SearchId'), Keys.chord(Keys.COMMAND, Keys.RIGHT))

        WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_OrigQuote_SearchId'), '.)')
    } else if (fileNum == 3) {
        testCount++

        row = 0

        error = (baseLines[row])

        if (newContent.contains(error)) {
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports a 620 error in ') + testFile) + ' when the original text snippet is preceded with a double quote. (Issue 574)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 620 error in ') + 
                testFile) + ' when the original text snippet is preceded with a double quote. (Issue 574)')
        } else {
            passCount++
        }
        
        continue
        
        WebUI.closeBrowser()
    } else if (fileNum == 4) {
        WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

        WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'Occurrence']))

        WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'p5cd')

        WebUI.delay(1)

        WebUI.setText(findTestObject('Page_tCC translationNotes/text_Occurrence_SearchId'), '1')
    } else if (fileNum == 5) {
        testCount++

        row = 0

        error = (baseLines[row])

        if (newContent.contains(error)) {
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
        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'x5g8')

        WebUI.delay(1)

        noteText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'))

        noteText = noteText.replace('perfectly together.', 'perfectly together.”')

        WebUI.setText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'), noteText)

        WebUI.delay(1)

        WebUI.scrollToPosition(0, 0)

        WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_SearchCloseX'))

        WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

        WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'GLQuote']))

        WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

        WebUI.delay(1)

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'd39x')

        noteText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_GLQuote_SearchId'))

        noteText = noteText.replace('fulfill it', 'fulfill it.”')

        WebUI.setText(findTestObject('Page_tCC translationNotes/text_GLQuote_SearchId'), noteText)

        WebUI.delay(1)
    } else if (fileNum == 7) {
        errors = []

        testCount++

        row = 0

        error = (baseLines[row])

        if (newContent.contains(error)) {
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

        error = (baseLines[row])

        if (newContent.contains(error)) {
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator incorrectly reports a 916 error in ') + testFile) + ' although the OrigQuote exactly matches the highlighted snippet.  (Issue 600)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 916 error in ') + 
                testFile) + ' although the OrigQuote exactly matches the highlighted snippet. (Issue 600)')
        } else {
            passCount++
        }
        
        errors.add(error)

        for (def error : errors) {
            baseLines.removeElement(error)
        }
        
        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'zb3e')

        WebUI.delay(1)

        WebUI.setText(findTestObject('Page_tCC translationNotes/text_SupportReference_SearchId'), 'figs-idiom')

        WebUI.delay(1)
    } else if (fileNum == 8) {
        testCount++

        row = 0

        error = (baseLines[row])

        if (newContent.contains(error)) {
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports a 95 error in ') + testFile) + ' although there is no leading trailing space in OrigQuote. (Issue 588)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 95 error in ') + 
                testFile) + ' although there is no trailing space in OrigQuote. (Issue 588)')
        } else {
            passCount++
        }
        
        baseLines.removeElement(error)

        continue
    } else if (fileNum == 9) {
        errors = []

        testCount++

        row = 0

        error = (baseLines[row])

        if (newContent.contains(error)) {
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports a 786 error in ') + testFile) + ' for rows that do not reference scripture. (Issue 603)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 786 error in ') + 
                testFile) + ' for rows that do not reference scripture. (Issue 603)')
        } else {
            passCount++
        }
        
        errors.add(error)

        testCount++

        row = 1

        error = (baseLines[row])

        if (newContent.contains(error)) {
            errorFlag = true

            errorCount++

            prefix = test4Expected(testFile, row)

            println(((prefix + 'ERROR: Validator reports a 786 error in ') + testFile) + ' for rows that contain multiple links even though one is entered in SupportReference. (Issue 606)')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((prefix + 'Test failed because the validator reports a 786 error in ') + 
                testFile) + ' for rows that contain multiple links even though one is entered in SupportReference. (Issue 606)')
        } else {
            passCount++
        }
        
        errors.add(error)

        for (def error : errors) {
            baseLines.removeElement(error)
        }
        
        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'mxm2')

        oNote = WebUI.getText(findTestObject('Object Repository/Page_tCC translationNotes/text_OccurrenceNote_SearchId'))

        oNote = oNote.replace('[[rc://en/ta/man/translate/figs-ellipsis]]', '[[rc://*/ta/man/translate/figs-ellipsis]]')

        WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OccurrenceNote_SearchId'), oNote)
    }
    
    // Rerun the validation
    initSize = vSize

    (vSize, newContent, myFile) = runValidation(initSize, testFile)

    //    println('newContent:' + newContent)
    testOutput(baseLines, myFile)

    if (1 == 2) {
        // Read the file into field lists
        prioritys = []

        chapters = []

        verses = []

        lines = []

        ids = []

        detailss = []

        poss = []

        excerpts = []

        messages = []

        locations = []

        new File(myFile).splitEachLine(',', { def fields ->
                prioritys.add(fields[0])

                chapters.add(fields[1])

                verses.add(fields[2])

                lines.add(fields[3])

                ids.add(fields[4])

                detailss.add(fields[5])

                poss.add(fields[6])

                excerpts.add(fields[7])

                messages.add(fields[8])

                locations.add(fields[9])
            })
    }
    
    if (!(errorFlag)) {
        println(('\n=>=>=>=>=>=>=>=>=> No errors were found when processing ' + testFile) + '\n')
    }
    
    // Insert errors into en_tn_50-EPH.tsv
    if (fileNum == 0) {
        WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

        WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'Occurrence']))

        WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

        WebUI.scrollToPosition(0, 0)

        WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_SearchCloseX'))

        WebUI.delay(1)

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'ab01')

        WebUI.setText(findTestObject('Page_tCC translationNotes/text_SupportReference_SearchId'), 'figs-doublenegative')

        WebUI.setText(findTestObject('Page_tCC translationNotes/text_Occurrence_SearchId'), '2')
		
        WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

        WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'Occurrence']))

        WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

		WebUI.sendKeys(null, Keys.chord(Keys.TAB))

        noteText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'))
		println('noteText0:' + noteText)

        noteText = noteText.replace('(See: [[rc://en/ta/man/translate/figs-doublenegatives]])', '(See: [[rc://*/ta/man/translate/figs-doublenegative]])')
		println('noteText1:' + noteText)
		
        WebUI.setText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'), noteText)
		noteText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'))
		println('noteText2:' + noteText)

        // Rerun the validation
        initSize = vSize

        (vSize, newContent, myFile) = runValidation(initSize, testFile)
		
		prioritys = ['889', '886', '450', '792']
		
		errors = []
		
		expecteds = ['792']
		
		for (priority in prioritys) {
			errors.add(false)
		}
		
		new File(myFile).eachLine({ def line ->
			if (line.contains('ab01')) {
				for (def p : (0..prioritys.size()-1)) {
					if (line.contains(prioritys[p])) {
						errors[p] = true
					}
				}
			}
		})
		
		for (def i : (0..prioritys.size()-1)) {
			testCount ++
			prefix = ''
			if (prioritys[i] in expecteds) {
				prefix = '<< EXPECTED >> '
				expectedCount ++
			}
			if (!errors[i]) {
				println(prefix + 'ERROR: Validator did not report an inserted ' + prioritys[i] + ' error in ' + testFile)			
				CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(prefix + 'Test failed because the validator did not report an inserted ' + prioritys[i] + ' error in ' + testFile)
				errorCount ++	
			} else {
				passCount ++
			}
		}
		
    }
    
    WebUI.closeBrowser()
}

println('<<<<<<<<<<>>>>>>>>>>')

println(testCount + ' tests were run.')

println(passCount + ' tests passed.')

println(((errorCount + ' tests failed; ') + expectedCount) + ' expected.')

println('<<<<<<<<<<>>>>>>>>>>')

GlobalVariable.testCount = testCount

GlobalVariable.passCount = passCount

GlobalVariable.errorCount = errorCount

GlobalVariable.expectedErrors = expectedCount

GlobalVariable.scriptRunning = false

WebUI.closeBrowser( // Get list of files in the Download folder
    // Add the validation files to a list
    ) // Wait for the validation file to be downloaded
// Sort the list
// Get the name of the last (newest) file in the list
//	for (file in files) {
//	testLines = [4,6,7]
//	println(line)
//	println(line)
//for (baseLine in baseLines) {
//for (line in testLines) {
//def testOutput(def testLines, def baseFile, def testFile) {
//println(newLines)
//	for (line in testLines) {

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
            if (file.contains('Validation-' + testFile)) {
                vFiles.add(file)
            }
        })

    return vFiles
}

def runValidation(def initSize, def testFile) {
    WebUI.scrollToPosition(0, 0)

    WebUI.waitForElementClickable(findTestObject('Page_tCC translationNotes/button_validate'), 30)

    WebUI.click(findTestObject('Page_tCC translationNotes/button_validate'))

    if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_tCC translationNotes/alert_ValidationRunning'), 
        2)) {
        println('waiting')

        WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_tCC translationNotes/alert_ValidationRunning'), 
            60)
    }
    
    vSize = initSize

    while (vSize <= initSize) {
        WebUI.delay(1)

        vFiles = getValidationFiles(testFile)

        vSize = vFiles.size()
    }
    
    vFiles = vFiles.toSorted()

    myFile = (vFiles[(vFiles.size() - 1)])

    println('myFile:' + myFile)

    println('dirName2:' + dirName)

    myFile = ((dirName + '/') + myFile)

    nFile = new File(myFile)

    newContent = nFile.text

    return [vSize, newContent, myFile]
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

