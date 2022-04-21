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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

// Modified 11/06/20	Added Scroll to top and wait in order to allow Save button to be clicked on Firefox
//						Added CustomKeywords send fail messages om failures
//						Verified that timeout has not occurred on waiting for Save button to not be clickable

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_OrigQuote'))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

origQuote = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-rtc9'))

println('Original language quote is ' + origQuote)

String newOrigQuote = origQuote + ' abc'

println('Setting original language quote to ' + newOrigQuote)

WebUI.setText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-rtc9'), newOrigQuote)

WebUI.scrollToPosition(0, 0)

WebUI.delay(1)

//In v1.0.4 the save button is enabled without the blur when running Katalon scripts
//WebUI.clickOffset(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-rtc9'), 0, -20)

WebUI.click(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'))

if (!WebUI.waitForElementNotClickable(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'), 10)) {
	println('ERROR: Save button was not disabled after 10 seconds')
    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the Save button was not disabled after 10 seconds.')
}

WebUI.verifyElementText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-rtc9'), newOrigQuote)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_OrigQuote'))

WebUI.clickOffset(findTestObject('Page_tCC translationNotes/columns_OrigQuote'), -30, 0)

origQuote2 = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-rtc9'))

println((((('At ' + GlobalVariable.url) + ' before was ') + origQuote) + ' and after is ') + origQuote2)

if (!WebUI.verifyElementText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-rtc9'), newOrigQuote)) {
	println('ERROR: GL quote text is not as expected')
    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the GL quote text is not as expected.')
}

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

