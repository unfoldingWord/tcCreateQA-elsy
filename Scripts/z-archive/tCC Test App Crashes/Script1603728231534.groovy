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

WebUI.callTestCase(findTestCase('z-archive/tCC tA Open For Edit'), [('$username') : '', ('$password') : '', ('$origQuote') : ''
        , ('$newOrigQuote') : ''], FailureHandling.STOP_ON_FAILURE)

println('Testing for app crassh when changing sections visible and invisible. ISSUE 428')

WebUI.click(findTestObject('Page_tCC translationAcademy/button_Sections'))

if (!(WebUI.waitForElementVisible(findTestObject('Page_tCC translationAcademy/text_BentOver'), 1, FailureHandling.OPTIONAL))) {
    println('ERROR: tA target text not found after sections were opened')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tA target text was not found after sections were opened. Assuming app crashed.')
} else {
    println('App did not crash after Sections was clicked.')
}

WebUI.delay(2)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('$origQuote') : ''
        , ('$newOrigQuote') : ''], FailureHandling.STOP_ON_FAILURE)

println('Testing for app crassh when searching in tN. ISSUE 440')

WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'leaders')

if (!(WebUI.waitForElementVisible(findTestObject('Page_tCC translationNotes/header_frontintro_Parmed', [('text') : 'TIT front:intro']), 
    5, FailureHandling.OPTIONAL))) {
    println('ERROR: tN source text not found after entering search value')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tN source text was not found after entering search value. Assuming app crashed.')
} else {
    println('App did not crash after search in tN.')
}

WebUI.delay(2)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('$origQuote') : ''
        , ('$newOrigQuote') : ''], FailureHandling.STOP_ON_FAILURE)

println('Testing for app crash after choosing a file from the drawer. ISSUE 428')

CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseFile'('en_tn_55-1TI.tsv')

if (!(WebUI.waitForElementVisible(findTestObject('Page_tCC translationNotes/header_frontintro_Parmed', [('text') : '1TI front:intro']), 
    5, FailureHandling.OPTIONAL))) {
    println('ERROR: tN source text not found after choosing a file from the drawer')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tN source text was not found after choosing a file from the drawer. Assuming app crashed.')
} else {
    println('App did not crash after choosing a file from the drawer.')
}

WebUI.delay(2)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('$origQuote') : ''
        , ('$newOrigQuote') : ''], FailureHandling.STOP_ON_FAILURE)

println('Testing for app crash after paging forward twice. ISSUE 463')

WebUI.click(findTestObject('Page_tCC translationNotes/button_NextPage'))

WebUI.delay(2)

WebUI.click(findTestObject('Page_tCC translationNotes/button_NextPage'))

if (!(WebUI.waitForElementPresent(findTestObject('Page_tC Create/chip_Repo'), 5, FailureHandling.OPTIONAL))) {
    println('ERROR: Repo chip is not present after paging forward twice')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the Repo chip is not present after paging forward twice. Assuming app crashed.')
} else {
    println('App did not crash after paging forward twice.')
}

WebUI.delay(2)

WebUI.closeBrowser()

