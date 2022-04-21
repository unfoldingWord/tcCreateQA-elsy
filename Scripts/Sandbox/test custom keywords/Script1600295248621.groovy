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

//import org.openqa.selenium.WebDriver as WebDriver
//import org.openqa.selenium.WebElement as WebElement
//import org.openqa.selenium.interactions.Actions as Actions
//import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
//import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
WebUI.openBrowser('google.com')

//WebDriver driver = DriverFactory.getWebDriver()
//println(driver.capabilities['browserName'])
//println(driver.capabilities['version'])
//WebUI.closeBrowser()
//browserName
println('this is a test')

system = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getOperatingSystem'()

println('The OS is ' + system)

browser = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getBrowserAndVersion'()

println('The browser is ' + browser)

(width, height) = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getScreenResolution'()

println((('The screen resolution is ' + width) + 'x') + height)

WebUI.closeBrowser()

