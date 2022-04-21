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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

// USER WITH WRITE AUTHORITY IN ORGANIZATION
WebUI.callTestCase(findTestCase('tCC Components/tCC Login'), [('user') : 'tc01', ('password') : 'tc01', ('newSession') : true], FailureHandling.STOP_ON_FAILURE)

if (!(WebUI.callTestCase(findTestCase('tCC Components/tCC Select Org-Lang-Resource'), [('organization') : '', ('language') : ''
        , ('resource') : 'en_ta'], FailureHandling.STOP_ON_FAILURE))) {
    KeywordUtil.markFailed('Exiting script because organization was not found.')
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the organization was not found.')
}

WebUI.closeBrowser()

// USER WITH NO ORGANIZATION
WebUI.callTestCase(findTestCase('tCC Components/tCC Login'), [('user') : 'tc02', ('password') : 'tc02', ('newSession') : true], FailureHandling.STOP_ON_FAILURE)

if (!WebUI.verifyElementPresent(findTestObject('Page_tC Create/msg_No organizations for this account'), 1, FailureHandling.OPTIONAL)) {
	println = 'ERROR: The no organization error message was not displayed.'
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the no organization error message was not displayed.')
}

WebUI.closeBrowser()

// USER WITH INVALID PASSWORD
WebUI.callTestCase(findTestCase('tCC Components/tCC Login'), [('user') : 'tc02', ('password') : 'tc111', ('newSession') : true], FailureHandling.STOP_ON_FAILURE)

if (!WebUI.verifyElementPresent(findTestObject('Page_tC Create/msg_Password is Invalid'), 1, FailureHandling.OPTIONAL)) {
	println('Invalid password message was not displayed')
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the invalid password message was not displayed.')
} 
		
GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

