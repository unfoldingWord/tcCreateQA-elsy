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

String expectedTooltip = "Search";

// Find the Search icon at the top right of the header //get the value of the "title" attribute of the icon
String actualTooltip = WebUI.getAttribute(findTestObject('Object Repository/Tool tips/button_English - unfoldingWorden_tnElsyLambert-tc-create-1_MuiButtonBase-root-204 MuiIconButton-root-196 jss145'), "title")

//Assert the tooltip's value is as expected
System.out.println("Actual Title of Tool Tip"+actualTooltip);
if(actualTooltip.equals(expectedTooltip)) {
	System.out.println("Test Case Passed");
}
else{
	System.out.println("Test Case failed- Tool tips are different");
}
WebUI.closeBrowser()