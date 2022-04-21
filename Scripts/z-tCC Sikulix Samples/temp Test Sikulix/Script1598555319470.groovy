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

WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl(GlobalVariable.url)

WebUI.waitForElementVisible(findTestObject('Page_tC Create/icon_Lock'), 30)

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Page_tC Create/input__username'))

WebUI.delay(2)

WebUI.mouseOver(findTestObject('Object Repository/Page_tC Create/input__username'))


//String icnLock = '/Users/cckozie/Downloads/lockA.png'

String icnLock = '/Users/cckozie/Katalon Studio/Sikuli Images/lockA.png'


Screen s = new Screen()

//s.type("+",Key.CMD)

//s.type(Key.TAB)

s.type('abc')

WebUI.delay(2)


//Pattern lockIcon = new Pattern(icnLock)

//m = s.find(lockIcon)

//m.highlight(2)
//println(m)

//println(m.getScore())

String tCCName = '/Users/cckozie/Katalon Studio/Sikuli Images/tCC_Name.png'

//r = Region.create(297, 165, 76, 24)
Pattern name = new Pattern(tCCName)

Region r = s.find(name)

Region r1 = r.offset(((r.getX() + r.getW()) - r.getX()) + 10, 0)

r1.highlight(1)
r1.click()

//version = r1.text()

//println(version)

//WebUI.delay(2)

WebUI.closeBrowser()

