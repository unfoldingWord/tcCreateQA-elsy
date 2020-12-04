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

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/span_unfoldingWord'))

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/span_unfoldingWord Translation Notes'))

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/div_Select Language'))

WebUI.setText(findTestObject('Object Repository/Page_tC Create/Add new resource objects/input_Step4Select Your Language_react-selec_aae5da'),
	'en')

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/div_en - English - English (Europe Gateway)'))

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/span_en_tn_57-TIT.tsv'))
//Add steps to validate if PSV is on
def buttonstate = CustomKeywords.'com.tccreate.keywords.ExpandAllScriptureToggle.toggleAllScripture'('test')
System.out.println(" the state of the button is"+ buttonstate )
WebUI.delay(1)

 if (buttonstate == "off")
 {
 CustomKeywords.'com.tccreate.keywords.ExpandAllScriptureToggle.toggleAllScripture'('on')
 }
 else
 {
	 System.out.println("Parallel scripture viewer is ON")
 }

WebUI.scrollToElement(findTestObject('Page_tC Create/Add new resource objects/h6_TIT 1intro'), 2, FailureHandling.CONTINUE_ON_FAILURE)
// Expand the chapter
//validate