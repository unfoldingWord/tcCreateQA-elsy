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

WebUI.openBrowser('')

WebUI.maximizeWindow()

println('Opening ' + GlobalVariable.url)

WebUI.navigateToUrl(GlobalVariable.url)

if (user == '') {
    println('Logging in as ' + GlobalVariable.user1Name)

    WebUI.setText(findTestObject('Object Repository/Page_tC Create/input__username'), GlobalVariable.user1Name)

    WebUI.setText(findTestObject('Object Repository/Page_tC Create/input__password'), GlobalVariable.user1Password)

    WebUI.click(findTestObject('Object Repository/Page_tC Create/button_Login'))
} else if (user != '?') {
    println('Logging in as ' + user)

    WebUI.setText(findTestObject('Object Repository/Page_tC Create/input__username'), user)

    WebUI.setText(findTestObject('Object Repository/Page_tC Create/input__password'), password)

    WebUI.click(findTestObject('Object Repository/Page_tC Create/button_Login'))
}

