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

// NEED TO UPDATE TO LOOK FOR CURRENT VALIDATOR MESSAGE HEADER TEXT
// NEED TO VERIFY THAT CURRENT FOUND ERRORS ARE LEGITIMATE
// NEED TO REVIEW TEARDOWN MESSAGES

// NEED UPDATES TO TEST:
// - GOOD AND BAD TWL FILES
// - GOOD AND BAD SOURCE FILES

showAlertText = true

testOtherLangs = true

baseDir = (GlobalVariable.projectPath + '/Data Files/')

errFiles = ['en_tn_57-TIT-header_missing.tsv']//, 'en_tn_57-TIT-header_error.tsv', 'en_tn_57-TIT-tab_error.tsv', 'en_tn_57-TIT-all_errors.tsv']

saveFile = (baseDir + 'en_tn_57-TIT-SAVE.tsv')

// Alternate languages/files to test
langFiles = [('ru') : ['en_tn_57-TIT.tsv', 'en_tn_65-3JN.tsv', 'en_tn_08-RUT.tsv'], ('hi') : ['en_tn_57-TIT.tsv', 'en_tn_67-REV']]
langFiles = [('ru') : ['en_tn_08-RUT.tsv']]

if (GlobalVariable.url == 'create.translationcore.com') {
    server = 'git'
} else {
    server = 'qa'
}

//myURL = 'https://qa.door43.org/translate_test/en_tn/src/branch/tcc001-tc-create-1/en_tn_57-TIT.tsv'
myURL = (('https://' + server) + '.door43.org/translate_test/en_tn/src/branch/tcc001-tc-create-1/en_tn_57-TIT.tsv')

errorHeader = 'This file cannot be opened by tC Create. Please contact your administrator to address the following error(s).'

start = 0

end = (errFiles.size() - 1)

//end = start
//----------------------------------------------------------------------------------------------------------------------------------
if (1 == 2) {
    // This resets the Titus text before processing the errors, but is not necessary for production
    WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : GlobalVariable.validateUser
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
    errFile = (baseDir + (errFiles[fNum]))

    WebUI.closeBrowser()

    println('Loading error file ' + errFile)

    CustomKeywords.'unfoldingWord_Keywords.WorkWithRepo.replaceRepoContent'(myURL, errFile, GlobalVariable.validateUser, 
        GlobalVariable.validatePassword)

    WebUI.delay(1)

    WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : GlobalVariable.validateUser
            , ('$password') : GlobalVariable.validatePassword, ('file') : 'GlobalVariable.tNFile]'], FailureHandling.STOP_ON_FAILURE)

    currentWindow = WebUI.getWindowIndex()

    WebUI.waitForElementPresent(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'), 5)

    alertText = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'))

    alertHeader = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Msg_Header'))

    println('Alert header:' + alertHeader)

    println('Alert text:' + alertText)

    if (!(alertHeader.contains(errorHeader))) {
        println('ERROR: On-open validator error does not contain expected header text')

        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the on-open validator error does not contain expected header text when testing ' + errFiles[fNum] + '.')
    }
	
	if (showAlertText) {
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'('\nFull alert text for ' + errFiles[fNum] + ' is:\n' + alertHeader + '\n' + alertText + '\n')
	}
    
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

// Restore the saved version of the file before exit
CustomKeywords.'unfoldingWord_Keywords.WorkWithRepo.replaceRepoContent'(myURL, saveFile, GlobalVariable.validateUser, GlobalVariable.validatePassword)

if (testOtherLangs) {
	// Test other languages
	langFiles.each({ def language, def files ->
	        files.each({ def file ->
	                WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : GlobalVariable.validateUser
	                        , ('$password') : GlobalVariable.validatePassword, ('file') : file, ('language') : language], FailureHandling.STOP_ON_FAILURE)
	
	                if (WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/alert_validator_Msg_Header'), 5, 
	                    FailureHandling.OPTIONAL)) {
	                    println((('ERROR: On-open validator reports problems with ' + language) + ' file ') + file)
						
	                    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((('Test failed because the on-open validator reports a problems with ' + 
	                        language) + ' file ') + file) + '.')
						alertText = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'))
						CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Message text is: \n' + alertText + '\n')
	
	                }
	            })
	
//	        WebUI.closeBrowser()
	    })
}

GlobalVariable.scriptRunning = false

