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
CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Academy')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")


WebUI.click(findTestObject('Page_tC Create/span_translate'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/span_bita-farming'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/span_01.md'))

testToggleTip('Object Repository/Tool tips/button_Sections', "Sections",'Object Repository/Tool tips/button_No Sections', "No Sections" )
testToggleTip('Object Repository/Tool tips/button_Blocks', "Blocks", 'Object Repository/Tool tips/button_No Blocks', "NoBlocks")
testToolTip('Object Repository/Tool tips/button_Preview', "Preview")
//testToolTipInactive('Object Repository/Tool tips/button_Save', "Save")



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