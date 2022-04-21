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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.interactions.Action as Action
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tN-Add new resource')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()
CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")


WebUI.click(findTestObject('Page_tC Create/Add new resource objects/span_en_tn_57-TIT.tsv'))

if(WebUI.verifyElementPresent(findTestObject('Object Repository/tN objects/Page_tC Create/English Source side-Master'), 2))
{
	 KeywordUtil.logInfo("Master file is on the Source side")
}
else {
	KeywordUtil.logInfo("ERROR:Master file not shown on the source side")
}
WebUI.click(findTestObject('Object Repository/tN objects/Page_tC Create/button_userMenu'))
WebUI.click(findTestObject('Object Repository/tN objects/Page_tC Create/button_Logout'))
//WebUI.closeBrowser()
WebUI.delay(5)
WebUI.click(findTestObject('Object Repository/tN objects/Page_tC Create/input_Password_password'))
WebUI.setText(findTestObject('Object Repository/tN objects/Page_tC Create/input_Password_password'),GlobalVariable.user1Name1Name1Name1Password)
WebUI.click(findTestObject('Object Repository/tN objects/Page_tC Create/button_Login'))

//Select GL org
CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('translate_test')
CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("ru")
WebUI.click(findTestObject('Page_tC Create/Add new resource objects/span_en_tn_57-TIT.tsv'))

