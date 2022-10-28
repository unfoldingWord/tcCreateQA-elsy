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

CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tA-Add or delete row')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/span_en_tn_57-TIT.tsv'))
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
WebUI.click(findTestObject('Object Repository/tN objects/button_view columns'))
WebUI.click(findTestObject('Object Repository/tN objects/input_ID'))
WebUI.click(findTestObject('tN objects/button__Close-Columns'))
//scroll to PSV
WebUI.scrollToPosition(100,1700)
//WebUI.scrollToElement(findTestObject('Object Repository/tN objects/div_Titus 11'), 2)
// Add a new Row
WebUI.click(findTestObject('Object Repository/tN objects/button_Titus 11_add a new row'))
WebUI.click(findTestObject('Object Repository/tN objects/button_Add'))
//validate
if (WebUI.verifyElementVisible(findTestObject('Object Repository/tN objects/code_empty')))
{
	KeywordUtil.logInfo(" New row is added successfully")
}
else
{
	KeywordUtil.logInfo(" New row is not added successfully")
}
//delete the row
WebUI.click(findTestObject('Object Repository/tN objects/button_Titus 11_Delete new row'))
WebUI.click(findTestObject('Object Repository/tN objects/span_Delete'))
//validate
if (WebUI.verifyElementVisible(findTestObject('Object Repository/tN objects/code_empty')))
{
	KeywordUtil.logInfo(" New row is deleted successfully")
}
else
{
	KeywordUtil.logInfo(" New row is not deleted successfully")
}
WebUI.closeBrowser()