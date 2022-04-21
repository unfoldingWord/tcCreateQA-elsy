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
import groovy.time.*
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.firefox.FirefoxDriver as FirefoxDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.Keys as Keys
import groovy.io.FileType as FileType
import org.apache.commons.io.FileUtils as FileUtils
import java.awt.datatransfer.Clipboard as Clipboard
import java.awt.datatransfer.Transferable as Transferable
import java.awt.datatransfer.DataFlavor as DataFlavor
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection

CustomKeywords.'unfoldingWord_Keywords.OpenBrowser.openFirefox'()

currentFile = '/Users/cckozie/git/Katalon/tC Create Project/Data Files/en_tn_57-TIT-SAVE.tsv'

newFile = '/Users/cckozie/git/Katalon/tC Create Project/Data Files/en_tn_57-TIT-no_header.tsv'

myURL = 'https://qa.door43.org/translate_test/en_tn/src/branch/tcc001-tc-create-1/en_tn_57-TIT.tsv'

name = GlobalVariable.validateUser

password = GlobalVariable.validatePassword

WebUI.navigateToUrl(myURL)

if (WebUI.verifyElementPresent(findTestObject('Page_Git Repo/icon_UserSignIn'), 1)) {
    WebUI.click(findTestObject('Page_Git Repo/icon_UserSignIn'))

    WebUI.setText(findTestObject('Page_Git Repo/input_Username'), name)

    WebUI.setText(findTestObject('Page_Git Repo/input_Password'), password)

    WebUI.click(findTestObject('Page_Git Repo/button_SignIn'))
}

WebUI.click(findTestObject('Page_Git Repo/icon_Edit'))

WebUI.delay(1)

WebUI.click(findTestObject('Page_Git Repo/span_ProjectTextHeader'))

WebUI.delay(1)

WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.COMMAND, 'a'))

WebUI.delay(1)

WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.DELETE))

iFile = new File(currentFile)

String fileText = FileUtils.readFileToString(iFile)

println(fileText)

//String myString = "This text will be copied into clipboard"
StringSelection stringSelection = new StringSelection(fileText)

Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()

clipboard.setContents(stringSelection, null)

WebUI.sendKeys(findTestObject(null), Keys.chord(Keys.COMMAND, 'v'))

WebUI.click(findTestObject('Page_Git Repo/button_CommitChanges'))

WebUI.closeBrowser()

