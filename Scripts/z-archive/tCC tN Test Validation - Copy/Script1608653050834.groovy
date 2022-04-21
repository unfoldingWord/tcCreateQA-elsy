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

// NEED TO ADD TESTS FOR THE INTRODUCTION OF ERRORS AFTER FIXING THE EXISTING ONES
dirName = '/Users/' + GlobalVariable.pcUser + '/Downloads'
println('dirName:' + dirName)

//baseDir = '/Users/' + GlobalVariable.pcUser + '/Katalon Studio/Files/Reference/'
baseDir = GlobalVariable.projectPath + '/Data Files/'

testFiles = ['en_tn_50-EPH.tsv', 'en_tn_57-TIT.tsv', 'en_tn_42-MRK.tsv', 'en_tn_43-LUK.tsv', 'en_tn_45-ACT.tsv', 'en_tn_46-ROM.tsv', 'en_tn_52-COL.tsv', 'en_tn_15-EZR.tsv']

start = 0
//start = 0

end = testFiles.size() - 1
//end = start

errorCount = 0
passCount = 0
testCount = 0

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
    WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : GlobalVariable.validateUser, ('$password') : GlobalVariable.validatePassword, ('file') : testFile], 
        FailureHandling.STOP_ON_FAILURE)

    // Run the validation
    (vSize, newContent) = runValidation(initSize, testFile)

    if (fileNum == 1) {
		
		testCount ++
        error = '916,1,1,5,xyz8,OrigQuote,,εὐσέβειαν​,Unable to find original language quote in verse text,'

        highlighted = 'rgba(255, 255, 0, 1)'

        background = WebUI.getCSSValue(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('myDiv') : 'id("header-1-1-xyz8")'
                    , ('chpt') : '1', ('verse') : '1', ('wordNum') : '17']), 'background-color')

        if (newContent.contains(error) && (background == highlighted)) {
			errorFlag = true
			errorCount ++
            println('ERROR: Validator reports "unable to find original language quote" in ' + testFile + ' on ID xyz8 even though it is highlighted')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the validator reports "unable to find original language quote" in ' + testFile + ' on ID xyz8 even though it is highlighted.')
        } else {
			passCount ++
        }
    }
    
    //    println('newContent:' + newContent)
    // Test to see if the validation results are what was expected based on the originally saved validation file
	
	testCount ++
    if (newContent != baseContent) {
		errorFlag = true
		errorCount ++
        println('ERROR: Initial validation content  in ' + testFile + ' does not match the base content')

        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the initial validation content  in ' + testFile + ' does not match the base content.')

        WebUI.closeBrowser()

        continue
		
    } else {
		passCount ++
        println('Initial content matches the base content')
    }
    
    WebUI.delay(1)
	continue

    // Show the additional required columns
    WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

    WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'ID']))

    WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'OrigQuote']))

    WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

    if (fileNum == 0) {	
        // Fix the en_tn_50-EPH.tsv validation errors
		
		testCount += 2
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

        WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), abbwNewQuote)

        testLines = [1, 2]
		
    } else if (fileNum == 1) {
        // Fix the en_tn_57-TIT.tsv validation errors
	
		testCount += 3
	
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

        rtc9NewQuote = '​κατὰ πίστιν'

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'rtc9')

        WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), rtc9NewQuote)

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), '')

        xyz8NewQuote = 'εὐσέβειαν'

        WebUI.scrollToPosition(0, 0)

        WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_SearchCloseX'))

        WebUI.delay(1)

        WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'xyz8')

        WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), xyz8NewQuote)

        testLines = [4, 6, 7]
		
    } else if (fileNum == 2) {
		// Fix the en_tn_42-MRK.tsv validation errors
	
		testCount += 2
	
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
		
		testLines = [1,3]
		
    } else if (fileNum == 3) {	
		// Check for the invalid error in en_tn_43-LUK.tsv 
	
		testCount ++
		error = '620,1,35,99,nd3z,OrigQuote,,"(""=D34/H22)Πνεῦμα …",Seems original language quote might not start at the beginning of a word,'
		if (newContent.contains(error)) {
			errorFlag = true
			errorCount ++
			println('ERROR: Validator reports a 620 error in ' + testFile + ' when the original text snippet is preceded with a double quote')
            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the validator reports a 620 error in ' + testFile + ' when the original text snippet is preceded with a double quote.')
		} else {
			passCount ++
		}
	
		continue
		
    } else if (fileNum == 4) {	
		// Fix the en_tn_45-ACT.tsv validation errors
	
		testCount ++
		WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))
	
		WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'Occurrence']))

		WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

		WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

		WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'p5cd')

		WebUI.delay(1)
	
		WebUI.setText(findTestObject('Page_tCC translationNotes/text_Occurrence_SearchId'), '1')
	
		testLines = [17]
	
    } else if (fileNum == 5) {	
		// Check for the invalid error in en_tn_46-ROM.tsv 
	
		testCount ++	
		error = '621,3,15,227,vds1,OrigQuote,33,…αι αἷμα(…=D8230/H2026),Seems original language quote might not finish at the end of a word,'
		if (newContent.contains(error)) {
			errorFlag = true
			errorCount ++
			println('ERROR: Validator reports a 621 error in ' + testFile + ' when the original text snippet is followed by an ellipsis')
            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the validator reports a 621 error in ' + testFile + ' when the original text snippet is followed by an ellipsis.')
		} else {
			passCount ++
		}
	
		continue
	
    } else if (fileNum == 6) {	
		// Fix the en_tn_52-COL.tsv validation errors
		testCount += 2
		
		WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

		WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'x5g8')

		WebUI.delay(1)
	
		noteText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OccurrenceNote_SearchId'))
			
		noteText = noteText.replace('perfectly together.','perfectly together.”')
	
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
		
		noteText = noteText.replace('fulfill it','fulfill it.”')
	
		WebUI.setText(findTestObject('Page_tCC translationNotes/text_GLQuote_SearchId'), noteText)
		
		WebUI.delay(1)
	
		testLines = [1,2]
	
    } else if (fileNum == 7) {	
		// Check for the invalid errors in en_tn_15-EZR.tsv
	
		testCount ++	
		error = '106,1,1,4,3th4,OrigQuote,,␣וּ⁠בִ⁠שְׁ…,Unexpected leading space,'
		if (newContent.contains(error)) {
			errorFlag = true
			errorCount ++
			println('ERROR: Validator reports a 106 error in ' + testFile + ' although there is no leading space in OrigQuote')
            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the validator reports a 106 error in ' + testFile + ' although there is no leading space in OrigQuote.')
		} else {
			passCount ++
		}

		testCount ++
		error = '916,1,1,4,3th4,OrigQuote,, וּ⁠ב…ָרַ֔ס,Unable to find original language quote in verse text,'
		if (newContent.contains(error)) {
			errorFlag = true
			errorCount ++
			println('ERROR: Validator incorrectly reports a 916 error in ' + testFile + ' although the OrigQuote exactly matches the highlighted snippet')
			CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the validator reports a 916 error in ' + testFile + ' although the OrigQuote exactly matches the highlighted snippet.')
		} else {
			passCount ++
		}

		// Fix the en_tn_15-EZR.tsv figs-idioms error in zb3e
		testCount ++
		
		WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

		WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'zb3e')

		WebUI.delay(1)
	
		WebUI.setText(findTestObject('Page_tCC translationNotes/text_SupportReference_SearchId'), 'figs-idiom')
		
		WebUI.delay(1)
	
		testLines = [20]
	
    }
    
    // Rerun the validation
    initSize = vSize

    (vSize, newContent, myFile) = runValidation(initSize, testFile)

    println('newContent:' + newContent)

    testOutput(testLines, baseFile, myFile)
	
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
	

    if (1 == 2) {
	    // Test to see if there are more rows than just the header/labels row
	    if (prioritys.size() > 1) {
			errorFlag = true
	        println('ERROR: Validation errors  in ' + testFile + ' remain in csv file')
	
	        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because validation errors  in ' + testFile + ' remain in csv file.')
	
	        println(newContent)
	    } else {
	        println('There are no remaining validation errors in ' + testFile)
	    }
    }
	
    if (!errorFlag) {
		println('\n=>=>=>=>=>=>=>=>=> No errors were found when processing ' + testFile + '\n')
	}
	
    WebUI.closeBrowser()
}

println('<<<<<<<<<<>>>>>>>>>>')
println(testCount + ' tests were run.')
println(passCount + ' tests passed.')
println(errorCount + ' tests failed.')
println('<<<<<<<<<<>>>>>>>>>>')

GlobalVariable.testCount = testCount
GlobalVariable.passCount = passCount
GlobalVariable.errorCount = errorCount

GlobalVariable.scriptRunning = false

WebUI.closeBrowser( // Get list of files in the Download folder
    ) // Add the validation files to a list
// Wait for the validation file to be downloaded
// Sort the list
// Get the name of the last (newest) file in the list
//	for (file in files) {
//	testLines = [4,6,7]
//	println(line)
//	println(line)
//for (baseLine in baseLines) {
//for (line in testLines) {

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
	
	WebUI.waitForElementClickable(findTestObject('Page_tCC translationNotes/button_validate'),30)
	
    WebUI.click(findTestObject('Page_tCC translationNotes/button_validate'))

    vSize = initSize

    while (vSize <= initSize) {
        WebUI.delay(5)

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

//def testOutput(def testLines, def baseFile, def testFile) {
def testOutput(def testLines, def baseFile, def myFile) {
    baseLines = []

    new File(baseFile).eachLine({ def line ->
            baseLines.add(line)
        })

    newLines = []

    new File(myFile).eachLine({ def line ->
            newLines.add(line)
        })

//	for (line in testLines) {
	testLines.each({ def line ->
		if(baseLines[line] in newLines) {
			int lineNum = line + 1
			errorFlag = true
			errorCount ++
            println('ERROR: Validation file line ' + lineNum + ' [' + baseLines[line] + ']  in ' + testFile + ' still exists after being fixed')
            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because validation file line ' + 
                lineNum + ' [' + baseLines[line] + ']  in ' + testFile + ' still exists after being fixed.')
        } else {
			passCount ++
        }
    })
}

