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
WebUI.click(findTestObject('Object Repository/Page_tCC translationAcademy/h4_A BRANCH header'))
WebUI.click(findTestObject('Object Repository/tA/button_Preview'))
WebUI.click(findTestObject('Object Repository/Page_tCC translationAcademy/code_ A shoot Preview mode'))
CustomKeywords.'com.tccreate.keywords.Hotkeys.sendKeys'('Object Repository/Page_tCC translationAcademy/code_ A shoot Preview mode', 'all')
WebUI.sendKeys(findTestObject('Object Repository/Page_tCC translationAcademy/code_ A shoot Preview mode'), ' **test edit by the test script- Bolded text**')
WebUI.sendKeys(findTestObject('Object Repository/Page_tCC translationAcademy/code_ A shoot Preview mode'), Keys.chord(Keys.RETURN))

WebUI.sendKeys(findTestObject('Object Repository/Page_tCC translationAcademy/code_ A shoot Preview mode'), ' *test edit by the test script- Italics*')
WebUI.sendKeys(findTestObject('Object Repository/Page_tCC translationAcademy/code_ A shoot Preview mode'), Keys.chord(Keys.RETURN))
WebUI.sendKeys(findTestObject('Object Repository/Page_tCC translationAcademy/code_ A shoot Preview mode'), ' > test edit by the test script- blockquote; ')
WebUI.sendKeys(findTestObject('Object Repository/Page_tCC translationAcademy/code_ A shoot Preview mode'), Keys.chord(Keys.RETURN))
WebUI.click(findTestObject('Object Repository/tA/button_Preview'))
//verify bold
if(WebUI.getText(findTestObject('Object Repository/Page_tCC translationAcademy/p_test edit by the test script- Bolded text')).contains("</strong>"))
{
	KeywordUtil.logInfo('Text is bolded as expected \n')
}
else {
	KeywordUtil.logInfo('Text is not bolded \n')
}
// Italics *text* <em>
if(WebUI.getText(findTestObject('Object Repository/Page_tCC translationAcademy/p_test edit by the test script- Bolded text')).contains("</em>"))
	{
		KeywordUtil.logInfo('Text is in Italics as expected \n')
	}
	else {
		KeywordUtil.logInfo('Text is not in Italics \n')
	}
//verify blockquote
if(WebUI.getText(findTestObject('Object Repository/Page_tCC translationAcademy/Element_blockquote')).contains("<blockquote>"))
	{
		KeywordUtil.logInfo('Text is in blockquote as expected \n')
	}
	else {
		KeywordUtil.logInfo('Text is not in blockquote \n')
	}

// WebUI.closeBrowser()


