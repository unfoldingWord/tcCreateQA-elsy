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
import org.sikuli.script.*
//import org.sikuli.api.Screen
//import org.sikuli.api.Pattern
//import org.sikuli.core.search.Match as Match

WebUI.openBrowser('google.com')

WebUI.maximizeWindow()

//WebUI.sendKeys(findTestObject('recordings/input__q'), '123')

//WebUI.delay(2)

Screen s = new Screen()

//s.type("+",Key.CMD)

//s.type(Key.TAB)

//s.type('abc')

WebUI.delay(2)

search = s.find('/Users/cckozie/git/Katalon_tC_Create/Images/googleSearch.png')

//s.click(search)

s.paste('abc')
