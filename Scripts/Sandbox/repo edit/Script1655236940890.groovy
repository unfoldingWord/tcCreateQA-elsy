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
import org.openqa.selenium.interactions.Action as Action
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
WebUI.openBrowser('https://qa.door43.org/unfoldingWord/en_tn/src/branch/ElsyLambert-tc-create-1/en_tn_57-TIT.tsv')


if (WebUI.verifyElementPresent(findTestObject('Page_Git Repo/icon_UserSignIn'), 1)) {
	WebUI.click(findTestObject('Page_Git Repo/icon_UserSignIn'))

	WebUI.setText(findTestObject('Page_Git Repo/input_Username'), GlobalVariable.user1Name)

	WebUI.setText(findTestObject('Page_Git Repo/input_Password'), GlobalVariable.user1Password)

	WebUI.click(findTestObject('Page_Git Repo/button_SignIn'))
}

WebUI.click(findTestObject('Page_Git Repo/icon_Edit'))

WebUI.waitForElementClickable(findTestObject('Page_Git Repo/span_ProjectTextHeader'), 15)
WebUI.click(findTestObject('Page_Git Repo/span_ProjectTextHeader'))
WebUI.sendKeys(findTestObject('Page_Git Repo/span_ProjectTextHeader'),)

//def test=WebUI.getText(findTestObject('Page_Git Repo/span_ProjectTextHeader'))
//println(test)
WebUI.sendKeys(findTestObject('Page_Git Repo/span_ProjectTextHeader'), 'Change TEXT on DCS', FailureHandling.CONTINUE_ON_FAILURE)


def enabled = WebUI.verifyElementClickable(findTestObject('Object Repository/Page_Git Repo/button_CommitChanges'), FailureHandling.OPTIONAL)

		println('enabled is ' + enabled)

		if (enabled) {
			WebUI.click(findTestObject('Page_Git Repo/button_CommitChanges'))
		} else {
			WebUI.click(findTestObject('Page_Git Repo/button_Cancel'))
		}