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


testToolTip('Object Repository/Tool tips/button_Search', "Search")
testToolTip('Object Repository/Tool tips/button_Filter table', "Filter Table")
testToolTip('Object Repository/Tool tips/button_view columns', "View Columns")
testToolTip('Object Repository/Tool tips/button_Validate', "Validate")
testToolTip('Object Repository/Tool tips/button_Preview', "Preview")
//testToolTipInactive('Object Repository/Tool tips/button_Save', "Save")
testToolTip('Object Repository/Tool tips/button_add a new row', "Add a Row")
testToolTip('Object Repository/Tool tips/button_Delete new row', "Delete a Row")
testToolTip('Object Repository/Tool tips/button_Move Row Down', "Move Row Down")
testToolTip('Object Repository/Tool tips/button_Move row up', "Move Row Up")
testToolTip('Object Repository/Tool tips/button_Manage Versions', "Manage Versions")
WebUI.scrollToElement(findTestObject('Object Repository/Tool tips/button_Expand chapter'), 2)
testToggleTip("Object Repository/Tool tips/button_Expand chapter", "Expand Chapter",'Object Repository/Tool tips/button_collapse-chapter', "Collapse Chapter" )


public void testToolTip(String button, String toolTip){
	
// Find the Search icon at the top right of the header //get the value of the "title" attribute of the icon
String actualTooltip = WebUI.getAttribute(findTestObject(button), "title")

//Assert the tooltip's value is as expected
System.out.println("Actual Title of Tool Tip for button " +toolTip +" is " +actualTooltip);
String expectedTooltip = toolTip;
if(actualTooltip.equals(expectedTooltip)) {
	System.out.println("Test Case Passed for " +  toolTip);
}
else{
	System.out.println("Test Case failed- Tool tips are different");

}
}
 public void testToolTipInactive(String button, String toolTip){
	 
	 WebUI.mouseOver(findTestObject(button))
	 
		 WebUI.delay(1)
	 
		 if (WebUI.verifyTextPresent(toolTip, false, FailureHandling.OPTIONAL)) {
			 println(toolTip + ' tool tip text is displayed')
		 } else {
			 println(('ERROR: The ' + toolTip) + ' tool tip text was not displayed.')
	 
			 
		 }
 }
 
 public void testToggleTip(String button1, String toolTip, String toggleButton, String toggleTip){
	 
 // Find the Search icon at the top right of the header //get the value of the "title" attribute of the icon
 String actualTooltip = WebUI.getAttribute(findTestObject(button1), "title")
 
 //Assert the tooltip's value is as expected
 System.out.println("Actual Title of Tool Tip for button " +toolTip +" is " +actualTooltip);
 String expectedTooltip = toolTip;
 if(actualTooltip.equals(expectedTooltip)) {
	 System.out.println("Test Case Passed for " +toolTip);
 }
 else{
	 System.out.println("Test Case failed- Tool tips are different");
 
 }
 WebUI.delay(2)
 //scroll
 
  WebUI.click(findTestObject(button1))
  
  String button= toggleButton
 String toggleTipTest= toggleTip
 System.out.println("Togglebutton" +button)
 System.out.println("ToggleTip" +toggleTipTest);
WebUI.delay(2)
 
 testToolTip(button, toggleTipTest)
 
 }
WebUI.closeBrowser()