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
import org.openqa.selenium.Keys as Keys
import groovy.transform.Field as Field
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'church as if it were')

myText = 'managing that household'

if (!(WebUI.verifyTextPresent(myText, false))) {
    println(('ERROR: Expected text "' + myText) + '" was not found on the screen')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because the expected text "' + myText) + 
        '" was not found on the screen.')
}
WebUI.delay(1)

WebUI.click(findTestObject('Page_tCC translationNotes/button_SearchCloseX'))

WebUI.delay(1)

WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'h15y')

myText = 'Sorry, no matching records found'

if (!(WebUI.verifyTextPresent(myText, false))) {
    println(('ERROR: Expected text "' + myText) + '" was not found on the screen')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because the expected text "' + myText) + 
        '" was not found on the screen.')
}

WebUI.click(findTestObject('Page_tCC translationNotes/button_SearchCloseX'))

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'ID']))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'h15y')

myText = 'Do not allow anyone to ignore you'

if (!(WebUI.verifyTextPresent(myText, false))) {
    println(('ERROR: Expected text "' + myText) + '" was not found on the screen')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because the expected text "' + myText) + 
        '" was not found on the screen.')
}

WebUI.delay(1)

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

