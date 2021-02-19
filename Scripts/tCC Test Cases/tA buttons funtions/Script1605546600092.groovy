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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tA-buttons')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Academy')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")


WebUI.click(findTestObject('Page_tC Create/span_translate'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/span_bita-farming'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/span_01.md'))
//section button
WebUI.click(findTestObject('Object Repository/tA/button_Sections'))
//verify if collapse button is visible
if(WebUI.verifyElementNotPresent(findTestObject('Object Repository/tA/div_A FARMER represents God, collapse tab'), 2, FailureHandling.CONTINUE_ON_FAILURE))
{
	KeywordUtil.logInfo('Collapsable Sections are not visible and is expanded as expected \n')
}
else
{
	KeywordUtil.logInfo('Error: Section is nt expanded \n')
}
if(WebUI.getText(findTestObject('Object Repository/tA/div_My well beloved had a vineyard')).contains("My well beloved had a vineyard"))
{
	KeywordUtil.logInfo('Section is expanded as expected and text is visible \n')
}
else{
	KeywordUtil.logInfo('Error:Section is not Expanded \n')
}
//blocks button
WebUI.click(findTestObject('Object Repository/tA/button_Blocks'))
//verify
//
//Preview button
WebUI.click(findTestObject('Object Repository/tA/button_Preview'))
//verify if MD char is visible in the block
if(WebUI.getText(findTestObject('Object Repository/tA/block in preview mode')).contains(">"))
{
	KeywordUtil.logInfo('Preview mode is ON as expected \n')
}
else{
	KeywordUtil.logInfo('Error:Preview mode is not seen \n')
}

WebUI.closeBrowser()

