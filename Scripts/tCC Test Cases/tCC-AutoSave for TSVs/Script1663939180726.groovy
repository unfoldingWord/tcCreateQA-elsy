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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.ui.SystemOutputInterceptor
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

import org.openqa.selenium.interactions.Action as Action
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tN-AutoSave')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()


CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()



WebUI.click(findTestObject('Object Repository/Page_tC Create/tn_TIT.tsv'))

CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()

WebUI.click(findTestObject('Object Repository/Page_tC Create/div_Introduction to TitusPart 1'))

WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/div_Introduction to TitusPart 1'), "TESTestAutoSAVE 1.7")
if(WebUI.verifyElementClickable(findTestObject('Page_tC Create/button_Save') , FailureHandling.CONTINUE_ON_FAILURE))
{
	KeywordUtil.logInfo(' \n Save button is enabled as expected' )// System.out.println(' Error: Save button is still enabled')
}	
else {
KeywordUtil.logInfo('\n  Error: Save button is not enabled')
}

//WebUI.closeBrowser()

WebUI.executeJavaScript('window.open();', [])
currentWindow = WebUI.getWindowIndex()
//Switches tab #1
WebUI.switchToWindowIndex(currentWindow + 1)

//Switches tab #0
//WebUI.switchToWindowIndex(currentWindow)

WebUI.navigateToUrl(GlobalVariable.url)

WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/input__username'), GlobalVariable.user1Name)
WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/input__password'), GlobalVariable.user1Password)
WebUI.click(findTestObject('Object Repository/Page_tC Create/button_Login'))


CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")

CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
WebUI.click(findTestObject('Object Repository/Page_tC Create/tn_TIT.tsv'))
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
WebUI.delay(2)
WebUI.click(findTestObject('Object Repository/Page_tC Create/div_Introduction to TitusPart 1'))
if (!(WebUI.getText(findTestObject('Object Repository/Page_tC Create/div_Introduction to TitusPart 1')).contains('TESTTestAutoSAVE 1.7'))) {
    println('Edits were Autosaved when Browser was closed')


} else {
    println('Error: Edits lost when Browser was closed')
}


//WebUI.closeBrowser()

// Open a firefox browser so that the server file can be chnaged
if (GlobalVariable.systemOS.contains('Windows')) {
	System.setProperty("webdriver.gecko.driver","C:\\Users\\cckoz\\Katalon\\Katalon_Studio_Windows_64-7.9.0\\configuration\\resources\\drivers\\firefox_win64\\geckodriver.exe")
} else {
	System.setProperty("webdriver.gecko.driver","/Applications/Katalon Studio.app/Contents/Eclipse/configuration/resources/drivers/firefox_mac/geckodriver");
}
WebDriver driver = new FirefoxDriver()
DriverFactory.changeWebDriver(driver)

WebUI.navigateToUrl(GlobalVariable.url)

WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/input__username'), GlobalVariable.user1Name)
WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/input__password'), GlobalVariable.user1Password)
WebUI.click(findTestObject('Object Repository/Page_tC Create/button_Login'))


CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()

WebUI.click(findTestObject('Object Repository/Page_tC Create/tn_TIT.tsv'))
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
WebUI.click(findTestObject('Object Repository/Page_tC Create/div_Introduction to TitusPart 1'))
WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/div_Introduction to TitusPart 1'), "TestChanges1.5 to the server file")
WebUI.click(findTestObject('Object Repository/Page_tC Create/button_Save'))
System.println("Changes are saved to DCS file-latest")
WebUI.closeBrowser()
//switch back to chrome session
WebUI.switchToWindowIndex(currentWindow + 1)

WebUI.navigateToUrl(GlobalVariable.url)

WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/input__username'), GlobalVariable.user1Name)
WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/input__password'), GlobalVariable.user1Password)
WebUI.click(findTestObject('Object Repository/Page_tC Create/button_Login'))

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")

CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
WebUI.click(findTestObject('Object Repository/Page_tC Create/tn_TIT.tsv'))
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
if(WebUI.verifyElementPresent(findTestObject('Object Repository/Page_tC Create/Autosave alert'),2)) 
	{
	AutosaveText= WebUI.getText(findTestObject('Object Repository/Page_tC Create/Autosave alert'))
	System.println(AutosaveText)
}
else {
	
	System.println("Error:Should get the Autosave pop-up")
}
WebUI.click(findTestObject('Object Repository/Page_tC Create/button_Discard My AutoSaved File'))
if ((WebUI.getText(findTestObject('Object Repository/Page_tC Create/div_Introduction to TitusPart 1')).contains('TestChanges1.5 to the server file'))) 
{
	println('AutoSave is discarded and changes from server are overwritten to the user branch')


} else {
	println('Error: Autosave file is not discarded')
}

WebUI.delay(3)
WebUI.closeBrowser()

