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

if (GlobalVariable.version.contains('rc.6') && 1 == 2) {
    WebUI.scrollToElement(findTestObject('Page_tCC translationAcademy/chip_Repo'), 2)

    WebUI.delay(5)
}

scripture = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('test')

println('The scripture pane is ' + scripture)

scripture = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('off')

println('The scripture pane is ' + scripture)

scripture = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('on')

println('The scripture pane is ' + scripture)

return false

WebUI.click(findTestObject('Page_tC Create/text_tCCVersion'))

WebUI.verifyElementVisible(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'))

//WebUI.verifyElementVisible(findTestObject('Page_tC Create/button_DrawerClose'), FailureHandling.STOP_ON_FAILURE)
open = WebUI.waitForElementPresent(findTestObject('Page_tC Create/button_DrawerClose'), 2)

if (open) {
    println('Drawer close is visible')
} else {
    println('Drawer close is not visible')
}

if (1 == 2) {
    WebUI.verifyElementPresent(findTestObject('Page_tC Create/input_Expand all Scripture'), 2)

    WebUI.verifyElementChecked(findTestObject('Page_tC Create/input_Expand all Scripture'), 2)

    WebUI.delay(5)

    WebUI.verifyElementPresent(findTestObject('Page_tC Create/input_Expand all Scripture'), 2)

    state = WebUI.verifyElementChecked(findTestObject('Page_tC Create/input_Expand all Scripture'), 2, FailureHandling.OPTIONAL)

    println(state)
}

//WebUI.verifyElementVisible(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
if (1 == 1) {
    WebUI.delay(5)

    onState = WebUI.verifyImagePresent(findTestObject('Page_tC Create/image_ExpandAllScriptureOn'), FailureHandling.OPTIONAL)

    println('On state is ' + onState)

    offState = WebUI.verifyImagePresent(findTestObject('Page_tC Create/image_ExpandAllScriptureOff'), FailureHandling.OPTIONAL)

    println('Off state is ' + offState)

    CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('Off')

    scripture = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('test')

    println('The scripture pane is ' + scripture)

    WebUI.delay(5)

    CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('ON')

    scripture = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('TEST')

    println('The scripture pane is ' + scripture)

    WebUI.delay(2)

    scripture = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('off')

    scripture = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('TEST')

    println('The scripture pane is ' + scripture)

    WebUI.delay(2)

    WebUI.closeBrowser()
}

