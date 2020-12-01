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
import org.openqa.selenium.Keys as Keys

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

WebUI.click(findTestObject('tW Objects/div_unfoldingWord'))

WebUI.click(findTestObject('tW Objects/div_unfoldingWord Translation Wordsunfoldin_70512f'))

WebUI.click(findTestObject('tW Objects/p_Select Language'))

WebUI.setText(findTestObject('tW Objects/input_Step4Select Your Language_react-selec_aae5da'), 'en')

WebUI.click(findTestObject('tW Objects/div_en - English - English (Europe Gateway)'))

WebUI.click(findTestObject('tW Objects/span_bible'))

WebUI.click(findTestObject('tW Objects/span_kt'))

WebUI.click(findTestObject('tW Objects/span_almighty.md'))

WebUI.click(findTestObject('tW Objects/h1_Almighty'))

WebUI.setText(findTestObject('tW Objects/div_Almighty test'), '<h1 id="almighty" style="">Almighty test</h1>')

WebUI.click(findTestObject('Page_tC Create/button_English - unfoldingWorden_twElsyLambert-tc-create-1_MuiButtonBase-root MuiIconButton-root jss56'))

WebUI.closeBrowser()

