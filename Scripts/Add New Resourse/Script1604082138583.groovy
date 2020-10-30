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
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Action
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.driver.DriverFactory

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/span_unfoldingWord'))

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/span_unfoldingWord Translation Notes'))

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/div_Select Language'))

WebUI.setText(findTestObject('Object Repository/Page_tC Create/Add new resource objects/input_Step4Select Your Language_react-selec_aae5da'), 
    'en')

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/div_en - English - English (Europe Gateway)'))

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/span_en_tn_57-TIT.tsv'))

//Add steps to validate if PSV is on 

WebUI.scrollToElement((findTestObject('Page_tC Create/Add new resource objects/h6_TIT 1intro')), 2, FailureHandling.CONTINUE_ON_FAILURE) 

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/h6_Titus 11'))

WebUI.verifyElementVisible(findTestObject('Page_tC Create/Add new resource objects/h6_Titus 11'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Page_tC Create/Add new resource objects/button_Manage Versions'))

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/input_Resource Path_resourceUrl'))

WebUI.setText(findTestObject('Page_tC Create/Add new resource objects/input_Resource Path_resourceUrl'), 'Door43-Catalog/hi/ulb/master/') // enter the URL
// text Door43-Catalog/hi/ulb/master/

WebUI.click(findTestObject('Object Repository/Page_tC Create/Add new resource objects/button_Add_Resource Path'))
// check if the Resource is checked

if (WebUI.verifyElementChecked(findTestObject('Object Repository/Page_tC Create/Add new resource objects/input_unfoldingWord Simplified Textv14_jss5689'), 1))
{
//click out of the box to see the PSV
	WebDriver driver = DriverFactory.getWebDriver()
	Actions action = new Actions(driver);
	action.sendKeys(Keys.ESCAPE).build().perform();
	
WebUI.delay(2)
//WebUI.click(findTestObject('Object Repository/Page_tC Create/Add new resource objects/Header_Unlocked Literal Bible - Hindi v5')) // check the new Resource Header
if (WebUI.verifyElementVisible(findTestObject('Object Repository/Page_tC Create/Add new resource objects/Header_Unlocked Literal Bible - Hindi v5'), FailureHandling.CONTINUE_ON_FAILURE))
{
	System.out.println("New Resource Header Present in the Scripture Viewer")
	if (WebUI.verifyElementVisible(findTestObject('Object Repository/Page_tC Create/Add new resource objects/Hindi ULB Text'), FailureHandling.CONTINUE_ON_FAILURE)) //verify the text is loaded
	{
		System.out.println(" New Resource Text is Present in the Scripture Viewer")
	} else
	{
		System.out.println("Error:New Resource Text is not present in the Scripture Viewer")
	}
}
else{
	System.out.println("Error: New Resource is not Present in the Scripture Viewer")
}

}

else
{
	System.out.println("Resource unchecked")
}
WebUI.closeBrowser()