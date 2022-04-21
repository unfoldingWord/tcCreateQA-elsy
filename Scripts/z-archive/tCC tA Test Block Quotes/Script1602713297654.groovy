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

WebUI.click(findTestObject('Page_tCC translationAcademy/section_BentOver'))

WebUI.click(findTestObject('Page_tCC translationAcademy/text_BentOver'))

WebUI.click(findTestObject('Page_tCC translationAcademy/header_BentOver'))

WebUI.delay(2)

if (!(WebUI.getText(findTestObject('Page_tCC translationAcademy/text_BentOver')).contains('Yahweh'))) {
    println('Block quote text was deleted')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because block quote text was deleted.')
} else {
    println('Block quote text is correct')
}

if (CustomKeywords.'unfoldingWord_Keywords.TestVersion.isVersionGreater'('1.0.4')) {
    WebUI.click(findTestObject('Page_tCC translationAcademy/button_preview'))

    WebUI.click(findTestObject('Page_tCC translationAcademy/section_Having BIRTH PAINS'))

    WebUI.sendKeys(findTestObject('Page_tCC translationAcademy/text_birthPains'), Keys.chord(Keys.END))

    WebUI.sendKeys(findTestObject('Page_tCC translationAcademy/text_birthPains'), Keys.chord(Keys.ENTER))

    WebUI.sendKeys(findTestObject('Page_tCC translationAcademy/text_birthPains'), '> This is line 1')

    WebUI.sendKeys(findTestObject('Page_tCC translationAcademy/text_birthPains'), Keys.chord(Keys.ENTER))

    WebUI.sendKeys(findTestObject('Page_tCC translationAcademy/text_birthPains'), '> This is line 2')

    WebUI.click(findTestObject('Page_tCC translationAcademy/button_preview'))

    WebUI.scrollToElement(findTestObject('Page_tCC translationAcademy/paragraph_My little children'), 2)

    if (WebUI.getText(findTestObject('Page_tCC translationAcademy/paragraph_My little children')).contains('ULT) This is line 1 This is line 2')) {
        println('line feeds were deleted in block quotes')

        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because block quote line feeds were deleted.')
    } else {
        println('Line feeds were correctly included in block quotes')
    }
} else {
    println('Test for block quote line feeds was bypassed')
}

WebUI.closeBrowser()

