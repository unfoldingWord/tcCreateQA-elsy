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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

//LOGIN
WebUI.callTestCase(findTestCase('tCC Components/tCC Login'), [('user') : $username, ('password') : $password, ('newSession') : true], 
    FailureHandling.STOP_ON_FAILURE)

organization = GlobalVariable.organization

if (WebUI.waitForElementPresent(organization, 5, FailureHandling.OPTIONAL) == false) {
    println('Failed to find organization option. Suspect infinite spinner')

    WebUI.takeScreenshot()

    return false
	
} else {

	WebUI.mouseOver(findTestObject('Page_tC Create/button_LinkToOrgDCS'))

    WebUI.delay(2)

    if (WebUI.getAttribute(findTestObject('Page_tC Create/button_LinkToOrgDCS'), 'Title') == null) {
		
		println('ERROR: No tool tip found for Organization')

		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the Organization tool tip was not foound.')

	}


    WebUI.delay(3)

    WebUI.click(organization)

	if (WebUI.getAttribute(findTestObject('Page_tC Create/button_ResourceGoToRepo'), 'Title') != 'Go to Repo') {
	
		println('ERROR: No tool tip found for Resource link to repo')

		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the Resource link to repo tool tip was not foound.')

	} else {
	
		println('Go to Repo tool tip found')
		
	}
	
	WebUI.mouseOver(findTestObject('Page_tC Create/button_ResourceGoToRepo'))
	
	WebUI.delay(2)
	
	if (!WebUI.verifyTextPresent('Go to Repo', false, FailureHandling.OPTIONAL)) {
	
		println('ERROR: The Go to Repo tool tip text was not displayed.')

		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the Go to Repo tool tip text was not displayed..')

	} else {
	
		println('Go to Repo tool tip text is displayed')
		
	}
	
}
