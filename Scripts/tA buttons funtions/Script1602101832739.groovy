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

WebUI.click(findTestObject('Object Repository/Page_tC Create/div_unfoldingWord'))

WebUI.click(findTestObject('Page_tC Create/div_unfoldingWord Translation Academyunfold_cb119f'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/div_Select Language'))

WebUI.setText(findTestObject('Page_tC Create/input_Step4Select Your Language_react-selec_aae5da'), 'en')

WebUI.click(findTestObject('Page_tC Create/div_en - English - English (Europe Gateway)'))

WebUI.click(findTestObject('Page_tC Create/span_translate'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/span_bita-animals'))

WebUI.click(findTestObject('Page_tC Create/button_English - unfoldingWorden_taElsyLamb_7e25b4'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/p_God is my rock. I take refuge in him.  He_f0de96'))

WebUI.setText(findTestObject('Object Repository/Page_tC Create/div_God is my rock. I take refuge in him.  _41a8ce'), '<blockquote style="">\n  <p style="">God is my rock. I take refuge in him.<br>\n  He is my shield, the <strong>horn</strong> of my salvation, my stronghold, and my refuge,<br>\n  the one who saves me from violence. (2 Samuel 22:3 ULT)</p>\n</blockquote>')

WebUI.click(findTestObject('Page_tC Create/button_English - unfoldingWorden_taElsyLambert-tc-create-1_MuiButtonBase-root MuiIconButton-root jss56'))

WebUI.click(findTestObject('Page_tC Create/svg_English - unfoldingWorden_taElsyLambert-tc-create-1_MuiSvgIcon-root'))

WebUI.closeBrowser()

