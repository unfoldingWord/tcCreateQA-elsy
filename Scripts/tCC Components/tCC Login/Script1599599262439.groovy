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

// LOGIN A USER AND SET THE GLOBAL VARIABLE version 
//
// INPUT PARAMETERS
// - user (If user is empty, the global variables user1Name and user1Password from the profile will be used)
//		  (If user = ? the test will wait for a manual login)
// - password 

if (newSession) {
	
	if (GlobalVariable.openBrowser) {
		
		WebUI.openBrowser('')
		
		WebUI.maximizeWindow()
		
	}
	
	println('Opening ' + GlobalVariable.url)
	
	WebUI.navigateToUrl(GlobalVariable.url)
	
	WebUI.waitForElementVisible(findTestObject('Page_tC Create/text_tCCVersion'), 30)
	
	version = WebUI.getText(findTestObject('Page_tC Create/text_tCCVersion'))
	
	GlobalVariable.version = version
	
	println('Version is ' + GlobalVariable.version)
}
if (user == "") {

	println('Logging in as ' + GlobalVariable.user1Name)
	
	GlobalVariable.activeUser = GlobalVariable.user1Name
	WebUI.setText(findTestObject('Page_tC Create/input__username'), GlobalVariable.user1Name)

	WebUI.setText(findTestObject('Page_tC Create/input__password'), GlobalVariable.user1Password)
	
	if (WebUI.verifyElementPresent(findTestObject('Page_tC Create/button_Login'),1,FailureHandling.OPTIONAL)) {
		
		WebUI.click(findTestObject('Page_tC Create/button_Login'))
		
	} else if (WebUI.verifyElementPresent(findTestObject('Page_tC Create/button_Login to try again'),1,FailureHandling.OPTIONAL)) {
	
		WebUI.click(findTestObject('Page_tC Create/button_Login to try again'))
	}
} 

else if (user != "?") {
	
	println('Logging in as ' + user)
	
	GlobalVariable.activeUser = user

	WebUI.setText(findTestObject('Page_tC Create/input__username'), user)
	
	WebUI.setText(findTestObject('Page_tC Create/input__password'), password)
	
	if (WebUI.verifyElementPresent(findTestObject('Page_tC Create/button_Login'),1,FailureHandling.OPTIONAL)) {
		
		WebUI.click(findTestObject('Page_tC Create/button_Login'))
		
	} else if (WebUI.verifyElementPresent(findTestObject('Page_tC Create/button_Login to try again'),1,FailureHandling.OPTIONAL)) {
	
		WebUI.click(findTestObject('Page_tC Create/button_Login to try again'))
	}
	
} 