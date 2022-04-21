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

String highlighted = 'rgba(255, 255, 0, 1)'

WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_OrigQuote'))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/Titus 11_note'), 2)

WebUI.doubleClick(findTestObject('Page_tCC translationNotes/text_OccurNote-Knowledge'))

WebUI.sendKeys(null, Keys.chord(Keys.CONTROL, Keys.INSERT))

WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), Keys.chord(Keys.SHIFT, Keys.INSERT))

WebUI.click(findTestObject('null'))

WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/Titus 11_note'), 2)

if (WebUI.getText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8-Preview')).contains('**')) {
    println('text is formatted')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the pasted text was formatted.')
} else {
    println('text is not formatted')
}

WebUI.closeBrowser()

