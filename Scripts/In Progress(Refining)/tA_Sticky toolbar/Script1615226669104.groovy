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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil



CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('MD formatting')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Academy')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")


WebUI.click(findTestObject('Page_tC Create/span_translate'))
WebUI.click(findTestObject('Object Repository/Page_tCC translationAcademy/span_bita-plants'))
WebUI.click(findTestObject('Object Repository/Page_tCC translationAcademy/span_01.md'))
WebUI.click(findTestObject('Object Repository/tA/button_Sections'))
WebUI.delay(1)
for (int i: (1..2))
	{
		
		WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.PAGE_DOWN))
	}
WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.PAGE_UP))

if(WebUI.verifyElementPresent(findTestObject('Object Repository/tA/button_Sections'),2 ))
{
	KeywordUtil.logInfo('Toolbar is visible as expected \n')
    WebUI.click(findTestObject('Object Repository/tA/button_Sections'))
    KeywordUtil.logInfo('Button in the toolbar is clickable')
}
else {
	KeywordUtil.logInfo('Toolbar is not visible')
}
WebUI.closeBrowser()