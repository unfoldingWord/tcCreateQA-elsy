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

CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('OBS-tQ-Save')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Open Bible Stories Translation Questions')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")

CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
WebUI.click(findTestObject('obs-tq/tq_OBS.tsv'))
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
WebUI.click(findTestObject('Object Repository/obs-tq/div_question edit box'))

WebUI.sendKeys(findTestObject('Object Repository/obs-tq/div_question edit box'), '  edits by a test script')

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
WebUI.click(findTestObject('Object Repository/obs-tq/span_English - unfoldingWorden_obs-tqElsyLambert-tc-create-1'))
WebUI.delay(2)
WebUI.switchToWindowIndex(1)
if (WebUI.getText(findTestObject('Object Repository/obs-tq/h1_DCS Repo question')).contains('edits by a test script'))
{
	KeywordUtil.logInfo("The edits are saved on the DCS Repo File")
}
else{
	KeywordUtil.logInfo("The edits are not found on the DCS Repo File")
}
WebUI.switchToWindowIndex(0)
WebUI.closeBrowser()

WebUI.closeBrowser()

