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

CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tW-Save')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()
CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Words')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")


WebUI.click(findTestObject('tW Objects/span_bible'))

WebUI.click(findTestObject('tW Objects/span_kt'))

WebUI.click(findTestObject('tW Objects/span_almighty.md'))

WebUI.click(findTestObject('tW Objects/h1_Almighty'))

WebUI.sendKeys(findTestObject('tW Objects/div_Almighty test'), '  edits by a test script')

WebUI.click(findTestObject('Object Repository/Page_tC Create/button_Save'))
//validate
//save button is garyed out
WebUI.delay(2)
if (WebUI.verifyElementNotClickable(findTestObject('Page_tC Create/button_Save'), FailureHandling.CONTINUE_ON_FAILURE))
{
	KeywordUtil.logInfo(" Save button is greyed out as expected")
}
else{
	KeywordUtil.logInfo(" Error: Save button is still enabled")

}
//Validate changes on DCS Repo
WebUI.click(findTestObject('Object Repository/tW Objects/chip_English - unfoldingWorden_twElsyLambert-tc-create-1'))
WebUI.delay(2)
WebUI.switchToWindowIndex(1)
if (WebUI.getText(findTestObject('Object Repository/tW Objects/h1_Almighty article on DCS')).contains('edits by a test script'))
{
	KeywordUtil.logInfo("The edits are saved on the DCS Repo File")
}
else{
	KeywordUtil.logInfo("The edits are not found on the DCS Repo File")
}
WebUI.switchToWindowIndex(0)
WebUI.closeBrowser()

WebUI.closeBrowser()

