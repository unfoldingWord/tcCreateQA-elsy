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

WebUI.verifyElementPresent(findTestObject('Object Repository/Organization Link/span_unfoldingWord'), 1)

//Check the tool tip
String expectedTooltip = 'Go to Organization'

// Find the icon  //get the value of the "title" attribute of the icon
String actualTooltip = WebUI.getAttribute(findTestObject('Object Repository/Organization Link/button_unfoldingWord_MuiButtonBase-root MuiIconButton-root'), 
    'title')

//Assert the tooltip's value is as expected
System.out.println('Actual Title of Tool Tip' + actualTooltip)

if (actualTooltip.equals(expectedTooltip)) {
    System.out.println('Test Case Passed')
} else {
    System.out.println('Test Case failed- Tool tips are different or not present')
}

// click on the Organization <> link
WebUI.click(findTestObject('Object Repository/Organization Link/button_unfoldingWord_MuiButtonBase-root MuiIconButton-root'))

WebUI.switchToWindowIndex(1)
if (WebUI.verifyElementVisible(findTestObject('Object Repository/Organization Link/a_Home') , FailureHandling.CONTINUE_ON_FAILURE) )// Add object from website page
{
	System.out.println("Organization page is opening")
}
else{
	System.out.println("Error: Cannot find the Organization page")
}
WebUI.switchToWindowIndex(0)
WebUI.closeBrowser()
