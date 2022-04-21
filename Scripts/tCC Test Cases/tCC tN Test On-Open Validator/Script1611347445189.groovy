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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

// 02/16/21	Modified to work in both Develop and Production
// 03/04/21 Modified to test additional language files
// 05/11/21 Modified for on-open validation of both source and target files
// 05/18/21 Added support for twl files and testing correctness of error messages


showAlertText = false

testOtherLangs = true

baseDir = (GlobalVariable.projectPath + '/Data Files/')

// All error files to test
errFiles = ['en_tn_57-TIT-header_missing.tsv', 'en_tn_57-TIT-header_error.tsv', 'en_tn_57-TIT-tab_error.tsv', 'en_tn_57-TIT-all_errors.tsv',
	'en_twl_TIT-missing_header.tsv', 'en_twl_TIT-bad-header.tsv', 'en_twl_TIT-missing_tabs.tsv', 'en_twl_TIT-extra_tabs.tsv',
	'en_tn_57-TIT-dupIds.tsv', 'en_twl_TIT-dupIds.tsv']


// Error file alert messages
alertTexts = ['On line 1 Bad TSV Header, expecting:"Book, Chapter, Verse, ID, SupportReference, OrigQuote, Occurrence, GLQuote, OccurrenceNote", found:"TIT, front, intro, m2jl, , ab, 0, , # Introduction to Titus<br><br>## Part 1: Ge..."   \
On line 1 Headers different at character 1: B (x42) vs T (x54)   \
On line 1 TSV Header has incorrect length, should be 82; found 3027',

'On line 1 Bad TSV Header, expecting:"Book, Chapter, Verse, ID, SupportReference, OrigQuote, Occurrence, GLQuote, OccurrenceNote", found:"Book, Chapter, Verse, ID, SupportReference, OrigQuote, Occurrence, Quote, Occurren..."   \
On line 1 Headers different at character 61: G (x47) vs Q (x51)   \
On line 1 TSV Header has incorrect length, should be 82; found 80',

'On line 35 Not enough columns, expecting 9, found 8',

'On line 1 Bad TSV Header, expecting:"Book, Chapter, Verse, ID, SupportReference, OrigQuote, Occurrence, GLQuote, OccurrenceNote", found:"Book, Chpt, Verse, ID, SupportReference, OrigQuote, Occurrence, GLQuote, Occurrenc..."   \
On line 1 Headers different at character 8: a (x61) vs p (x70)   \
On line 1 TSV Header has incorrect length, should be 82; found 79   \
On line 22 Not enough columns, expecting 9, found 8   \
On line 26 Not enough columns, expecting 9, found 8   \
On line 98 Not enough columns, expecting 9, found 8   \
On line 187 Row ID is a duplicate of ID on row 26   \
On line 187 Too many columns, expecting 9, found 10',

'On line 1 Bad TSV Header, expecting:"Reference, ID, Tags, OrigWords, Occurrence, TWLink", found:"1:1, trr8, name, Παῦλος, 1, rc://*/tw/dict/bi..."   \
On line 1 Headers different at character 1: R (x52) vs 1 (x31)   \
On line 1 TSV Header has incorrect length, should be 45; found 54',

'On line 1 Bad TSV Header, expecting:"Reference, ID, Tags, OrigWords, Occurrence, TWLink", found:"Reference, ID, Tags, OrigWords, Occurrence, O..."   \
On line 1 Headers different at character 40: T (x54) vs O (x4F)   \
On line 1 TSV Header has incorrect length, should be 45; found 53',

'On line 12 Not enough columns, expecting 6, found 5   \
On line 18 Not enough columns, expecting 6, found 4   \
On line 47 Not enough columns, expecting 6, found 5',

'On line 19 Too many columns, expecting 6, found 7   \
On line 47 Too many columns, expecting 6, found 7',

'On line 34 Row ID j1qq is a duplicate of ID on row undefined   \
On line 191 Row ID j496 is a duplicate of ID on row 185   \
On line 199 Row ID j496 is a duplicate of ID on row 185',

'On line 11 Row ID f44k is a duplicate of ID on row undefined   \
On line 164 Row ID yu3w is a duplicate of ID on row 24   \
On line 222 Row ID yu3w is a duplicate of ID on row 24']


// Files expecting source errors
sourceErrs = []

// Alternate languages/files to test
langFiles = [('ru') : ['en_tn_57-TIT.tsv', 'en_tn_65-3JN.tsv', 'en_tn_08-RUT.tsv'], ('hi') : ['en_tn_57-TIT.tsv', 'en_tn_67-REV']]

tnSaveFile = (baseDir + 'en_tn_57-TIT-SAVE.tsv')

twlSaveFile = (baseDir + 'en_twl_TIT-SAVE.tsv')

server = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getServer'()

tnURL = (('https://' + server) + '.door43.org/translate_test/en_tn/src/branch/ElsyLambert-tc-create-1/en_tn_57-TIT.tsv')
twlURL = (('https://' + server) + '.door43.org/unfoldingWord/en_twl/src/branch/ElsyLambert-tc-create-1/twl_TIT.tsv')

targetErrorHeader = 'This file cannot be opened by tC Create as there are errors in the target file. Please contact your administrator to address the following error(s)'
sourceErrorHeader = 'This file cannot be opened by tC Create as there are errors in the Master file. Please contact your administrator to address the following error(s)'

start = 8

end = (errFiles.size() - 1)

end = start + 1
//----------------------------------------------------------------------------------------------------------------------------------
if (1 == 2) {
    // This resets the Titus text before processing the errors, but is not necessary for production
    WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : GlobalVariable.validateUser
            , ('$password') : GlobalVariable.validatePassword, ('file') : ''], FailureHandling.STOP_ON_FAILURE)

    currentWindow = WebUI.getWindowIndex()

    // The on-open validator alert is always present (and Katalon thinks visible), so use its text to determine if it is actual visible
    alertText = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'))

    println(alertText)

    if (alertText.contains('line')) {
        println('Restoring the Titus tN TSV file')

        WebUI.closeBrowser()

        CustomKeywords.'unfoldingWord_Keywords.WorkWithRepo.replaceRepoContent'(myURL, saveFile, GlobalVariable.validateUser, 
            GlobalVariable.validatePassword)
    } //	return false
}

//----------------------------------------------------------------------------------------------------------------------------------
for (def fNum : (start..end)) {
	
	if (errFiles[fNum].substring(3,5) == 'tn') {
		fileType = 'tn'
		myURL = tnURL
	} else {
		fileType = 'twl'
		myURL = twlURL
	}
	
    errFile = (baseDir + (errFiles[fNum]))

    WebUI.closeBrowser()

    println('Loading error file ' + errFile)

    CustomKeywords.'unfoldingWord_Keywords.WorkWithRepo.replaceRepoContent'(myURL, errFile, GlobalVariable.validateUser, 
        GlobalVariable.validatePassword)

    WebUI.delay(1)

	if (fileType == 'tn') {
	    WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : GlobalVariable.validateUser
	            , ('$password') : GlobalVariable.validatePassword, ('organization') : 'translate_test', ('resource') : '/en_tn', ('file') : GlobalVariable.tNFile], FailureHandling.STOP_ON_FAILURE)
	} else  {
		WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : GlobalVariable.validateUser
			, ('$password') : GlobalVariable.validatePassword, ('organization') : 'unfoldingWord', ('resource') : '/en_twl', ('file') : GlobalVariable.tWLFile], FailureHandling.STOP_ON_FAILURE)
	}
	
    currentWindow = WebUI.getWindowIndex()

    WebUI.waitForElementPresent(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'), 5)

    alertText = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'))

    alertHeader = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Msg_Header'))

    println('Alert header:' + alertHeader)

    println('Alert text:' + alertText)

	if (sourceErrs.contains(fNum)) {
		errorHeader = sourceErrorHeader
	} else {
		errorHeader = targetErrorHeader
	}
	
    if (!(alertHeader.contains(errorHeader))) {
        println('ERROR: On-open validator error does not contain expected header text')

        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the on-open validator error does not contain expected header text when testing ' + errFiles[fNum] + '.')
    }
	
	alertText = alertText.replaceAll('\n', '')
	alertText = alertText.trim()
	println('Alert text length is ' + alertText.length())
	println('Expected text length is ' + alertTexts[fNum].length())
	if (alertText != alertTexts[fNum]) {
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('\n Unexpected alert text for ' + errFiles[fNum] + '\nExpected:\n' + alertTexts[fNum] + '\nFound:\n' + alertText + '\n')
//		return false
	}
	
	if (showAlertText) {
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('\nFull alert text for ' + errFiles[fNum] + ' is:\n' + alertHeader + '\n' + alertText + '\n')
	}
    
	///////// May want to consider using this instead of hard coded xPath//////
	////TestObject tObj = findTestObject('Card_Settings/checkbox_Book')
	////println "${tObj.findPropertyValue('xpath')}"
	///////////

    xPath = '/html/body/div[2]/div[3]/div/div[2]/p'

    WebDriver driver = DriverFactory.getWebDriver()

    // Get the entire validator message element
    WebElement Paragraph = driver.findElement(By.xpath(xPath))

    // Get the individual error message elements
    List rows = Paragraph.findElements(By.tagName('p'))

    // Get the individual link elements
    List links = Paragraph.findElements(By.tagName('a'))

    sRows = rows

    row = 0

    //	 links.each{ link ->
    for (def link : links) {
        println('The row is now ' + row)

        println('Row size is ' + rows.size())

        //		 println('The error on row ' + row + ' is ' + rows[row].getText())
        line = link.getText()

        println(('The link text is [' + line) + ']')

        lStart = line.indexOf(' ')

        lineNum = line.substring(lStart + 1, line.length())

        println(('Line number is [' + lineNum) + ']')

        link.click()

        WebUI.switchToWindowIndex(currentWindow + 1)

        WebUI.waitForElementPresent(findTestObject('Page_Git Repo/repo_validator_lineNumber_parmed', [('lineNum') : lineNum]), 
            10)

        lineClass = WebUI.getAttribute(findTestObject('Page_Git Repo/repo_validator_lineNumber_parmed', [('lineNum') : lineNum]), 
            'class', FailureHandling.OPTIONAL)

        if (!(lineClass.contains('active'))) {
            println('ERROR: Linked to row in repo is not highlighted')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the linked to row in repo is not highlighted.')
        }
        
        WebUI.closeWindowIndex(currentWindow + 1)

        WebUI.delay(1)

        WebUI.switchToWindowIndex(currentWindow)

//        WebUI.delay(3)

        row++
    }
	
	if(fNum < end) {
		WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_validator_Message_Close'))
	}
}

nextFile = 'en_tn_56-2TI.tsv'

retCode = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseFile'(nextFile)

if (retCode) {
    println('ERROR: The user is not forced to close the error message modal')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the user is not forced to close the error message modal.')
	
} else {
	
    WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_validator_Message_Close'))

    WebUI.click(findTestObject('Page_tC Create/resource_Parmed', [('resource') : 'unfoldingWord/en_tn']))

    WebUI.click(findTestObject('Page_tC Create/file_Parmed', [('fileName') : 'en_tn_56-2TI.tsv']))
}

if (WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'), 5, FailureHandling.OPTIONAL)) {

	alertText = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'))

	if (alertText.contains(errorHeader)) {
	    println('ERROR: On-open validator error message from the previous file is displayed on new file open')
	
	    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the on-open validator error message from the previous file is displayed on new file open.')
	}
}

WebUI.closeBrowser()

// Restore the saved version of the files before exit
CustomKeywords.'unfoldingWord_Keywords.WorkWithRepo.replaceRepoContent'(tnURL, tnSaveFile, GlobalVariable.validateUser, GlobalVariable.validatePassword)

CustomKeywords.'unfoldingWord_Keywords.WorkWithRepo.replaceRepoContent'(twlURL, twlSaveFile, GlobalVariable.validateUser, GlobalVariable.validatePassword)

if (testOtherLangs) {
	// Test other languages
	langFiles.each({ def language, def files ->
        files.each({ def file ->
//	language = 'ru'
//	file = 'en_tn_08-RUT.tsv'
	
	                WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : GlobalVariable.validateUser
	                        , ('$password') : GlobalVariable.validatePassword, ('file') : file, ('language') : language], FailureHandling.STOP_ON_FAILURE)

					errState = WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/alert_validator_Msg_Header'), 5, 
	                    FailureHandling.OPTIONAL)
					
					if (file == 'en_tn_08-RUT.tsv') {
						
						if (!errState) {
							println('ERROR: On-open validator failed to report a duplicate ID in ' + language + ' file ' + file)
							
							CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed failed to report a duplicate ID in ' + language + ' file ' + file + '.')
							
						} else {
							
							alertText = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'))
							
							if (!alertText.contains('duplicate of ID')) {
								println('ERROR: On-open validator failed to report a duplicate ID in ' + language + ' file ' + file)
								
								CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed failed to report a duplicate ID in ' + language + ' file ' + file + '.')
								
							} else {
								CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'('Duplicate IDs found as expected in source of Russian ' + file)
							}
							
						}
						
					} else if (errState) {
						
	                    println((('ERROR: On-open validator reports problems with ' + language) + ' file ') + file)
						
	                    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((('Test failed because the on-open validator reports a problems with ' + 
	                        language) + ' file ') + file) + '.')
						alertText = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'))
						CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Message text is: \n' + alertText + '\n')
	
	                }
	            })
	
	        WebUI.closeBrowser()
	    })
}

GlobalVariable.scriptRunning = false
WebUI.closeBrowser()

