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

resource = ['unfoldingWord/en_ta', 'translate/', 'bita-humanbehavior/', '01.md']

WebUI.callTestCase(findTestCase('tCC Components/tCC md Open For Edit'), [('$username') : '', ('$password') : '', ('resource') : resource],
	FailureHandling.STOP_ON_FAILURE)

WebUI.delay(3)

Screen s = new Screen()

myImage = '/Users/cckozie/git/Katalon_tC_Create/Images/iconBar.png'

Pattern icons = new Pattern(myImage).similar(0.50)

found = s.exists(icons)

println(found)

WebUI.delay(1)

WebUI.scrollToPosition(0, 1000)

WebUI.delay(1)

found = s.exists(icons)

println(found)

WebUI.delay(1)

WebUI.scrollToPosition(0, 980)

WebUI.delay(1)

found = s.exists(icons)

println(found)

WebUI.delay(1)

WebUI.closeBrowser()
