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

baseDir = (GlobalVariable.projectPath + '/Data Files/')

errFiles = ['en_tn_57-TIT-header_missing.tsv', 'en_tn_57-TIT-header_error.tsv', 'en_tn_57-TIT-tab_error.tsv', 'en_tn_57-TIT-all_errors.tsv']

saveFile = (baseDir + 'en_tn_57-TIT-SAVE.tsv')

myURL = 'https://qa.door43.org/translate_test/en_tn/src/branch/tcc001-tc-create-1/en_tn_57-TIT.tsv'

errorHeader = 'This file cannot be opened by tC Create. Please contact your administrator to address the following error(s).'
//			   This file cannot be opened by tC Create. Please contact your administrator to address the following error(s).

start = 0

end = (errFiles.size() - 1)

//end = start

//----------------------------------------------------------------------------------------------------------------------------------
if (1 == 2) { // This resets the Titus text before processing the errors, but is not necessary for production
	WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : GlobalVariable.validateUser, ('$password') : GlobalVariable.validatePassword
	        , ('file') : ''], FailureHandling.STOP_ON_FAILURE)
	
	currentWindow = WebUI.getWindowIndex()

	// The on-open validator alert is always present (and Katalon thinks visible), so use its text to determine if it is actual visible
	alertText = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'))
	println(alertText)
	
	if (alertText.contains('line')) {
	    println('Restoring the Titus tN TSV file')
		
	    WebUI.closeBrowser()
	
	    CustomKeywords.'unfoldingWord_Keywords.WorkWithRepo.replaceRepoContent'(myURL, saveFile, GlobalVariable.validateUser, 
	        GlobalVariable.validatePassword)
	}
//	return false
}
//----------------------------------------------------------------------------------------------------------------------------------

for (def fNum : (start..end)) {
    errFile = (baseDir + (errFiles[fNum]))

	WebUI.closeBrowser()
	
	println('Loading error file ' + errFile)

    CustomKeywords.'unfoldingWord_Keywords.WorkWithRepo.replaceRepoContent'(myURL, errFile, GlobalVariable.validateUser, 
        GlobalVariable.validatePassword)

    WebUI.delay(1)
	
   	WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : GlobalVariable.validateUser, ('$password') : GlobalVariable.validatePassword
		, ('file') : 'GlobalVariable.tNFile]'], FailureHandling.STOP_ON_FAILURE)
	   
	currentWindow = WebUI.getWindowIndex()

    WebUI.waitForElementPresent(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'), 5)

	alertText = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'))
	
	alertHeader = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Msg_Header'))

	println('Alert header:' + alertHeader)
	
    println('Alert text:' + alertText)
	
	if (!alertHeader.contains(errorHeader)) {
		println('ERROR: On-open validator error does not contain expected header text')
        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the on-open validator error does not contain expected header text.')
 	}

    List lines = alertText.split('\n').findAll({ 
            it
        })

    println(lines)

    WebUI.delay(2)

    for (def line : lines) {
        if (line.contains('line ')) {
            lStart = line.indexOf('line ')

            str = line.substring(lStart + 5)

            lEnd = str.indexOf(' ')

            lineNum = str.substring(0, lEnd)

            WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/link_errorLine_Parmed', [('lineNum') : lineNum]))

            WebUI.switchToWindowIndex(currentWindow + 1)

            WebUI.waitForElementPresent(findTestObject('Page_Git Repo/repo_validator_lineNumber_parmed', [('lineNum') : lineNum]), 
                3)

            lineClass = WebUI.getAttribute(findTestObject('Page_Git Repo/repo_validator_lineNumber_parmed', [('lineNum') : lineNum]), 
                'class', FailureHandling.OPTIONAL)

            if (!(lineClass.contains('active'))) {
                println('ERROR: Linked to row in repo is not highlighted')
				CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the linked to row in repo is not highlighted.')
            }
			            
            WebUI.closeWindowIndex(currentWindow + 1)

            WebUI.delay(1)

            WebUI.switchToWindowIndex(currentWindow)

            WebUI.delay(1)
			
			if(fNum < end) {
			
				WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_validator_Message_Close'))
			}
        }
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

WebUI.waitForElementPresent(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'), 2)

alertText = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Error_Msg_2'))

if (alertText.contains(errorHeader)) {
	println('ERROR: On-open validator error message from the previous file is displayed on new file open')
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the on-open validator error message from the previous file is displayed on new file open.')
}

WebUI.closeBrowser()

// Restore the saved version of the file before exit
CustomKeywords.'unfoldingWord_Keywords.WorkWithRepo.replaceRepoContent'(myURL, saveFile, GlobalVariable.validateUser,
	GlobalVariable.validatePassword)

GlobalVariable.scriptRunning = false



