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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.interactions.Action as Action
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tN-Add new resource')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()
CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")

WebUI.scrollToElement(findTestObject('Page_tC Create/Add new resource objects/span_en_tn_57-TIT.tsv'), 1)
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
	 KeywordUtil.logInfo("Parallel scripture viewer is ON")
 }

WebUI.scrollToElement(findTestObject('Page_tC Create/Add new resource objects/h6_TIT 1intro'), 2, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/h6_Titus 11'))

WebUI.verifyElementVisible(findTestObject('Page_tC Create/Add new resource objects/h6_Titus 11'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Page_tC Create/Add new resource objects/button_Manage Versions'))

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/input_Resource Path_resourceUrl'))

WebUI.setText(findTestObject('Page_tC Create/Add new resource objects/input_Resource Path_resourceUrl'), 'Door43-Catalog/hi/ulb/master/' // enter the URL
    )

// text Door43-Catalog/hi/ulb/master/
WebUI.click(findTestObject('Object Repository/Page_tC Create/Add new resource objects/button_Add_Resource Path'))

// check if the Resource is checked
if (WebUI.verifyElementChecked(findTestObject('Object Repository/Page_tC Create/Add new resource objects/input_unfoldingWord Simplified Textv14_jss5689'), 
    1)) {
    //click out of the box to see the PSV
    WebDriver driver = DriverFactory.getWebDriver()

    Actions action = new Actions(driver)

    action.sendKeys(Keys.ESCAPE).build().perform()

    WebUI.delay(2)

 // check the new Resource Header
    if (WebUI.verifyElementVisible(findTestObject('Object Repository/Page_tC Create/Add new resource objects/Header_Unlocked Literal Bible - Hindi v5'), 
        FailureHandling.CONTINUE_ON_FAILURE)) {
        System.out.println('New Resource Header Present in the Scripture Viewer')

        if (WebUI.verifyElementVisible(findTestObject('Object Repository/Page_tC Create/Add new resource objects/Hindi ULB Text'), 
            FailureHandling.CONTINUE_ON_FAILURE)) //verify the text is loaded
        {
            KeywordUtil.logInfo(' New Resource Text added by a Relative path is Present in the Scripture Viewer')
        } else {
            KeywordUtil.logInfo('Error:New Resource Text added by a Relative path is not present in the Scripture Viewer')
        }
    } else {
       KeywordUtil.logInfo('Error: New Resource added by a Relative path is not Present in the Scripture Viewer')
    }
} else {
    KeywordUtil.logInfo('Resource unchecked')
}
 //-------------checking for a Url-----
WebUI.click(findTestObject('Object Repository/Page_tC Create/Add new resource objects/button_Manage Versions'))

WebUI.click(findTestObject('Page_tC Create/Add new resource objects/input_Resource Path_resourceUrl'))

WebUI.setText(findTestObject('Page_tC Create/Add new resource objects/input_Resource Path_resourceUrl'), 'https://git.door43.org/ru_gl/ru_rlob' // enter the URL
	)

//https://git.door43.org/ru_gl/ru_rlob
WebUI.click(findTestObject('Object Repository/Page_tC Create/Add new resource objects/button_Add_Resource Path'))

// check if the Resource is checked
if (WebUI.verifyElementChecked(findTestObject('Object Repository/Page_tC Create/Add new resource objects/input_unfoldingWord Simplified Text-Russian check box'),
	1)) {
	//click out of the box to see the PSV
	WebDriver driver = DriverFactory.getWebDriver()

	Actions action = new Actions(driver)

	action.sendKeys(Keys.ESCAPE).build().perform()

	WebUI.delay(2)

 // check the new Resource Header
	if (WebUI.verifyElementVisible(findTestObject('Object Repository/Page_tC Create/Add new resource objects/span_RLOB v1'),
		FailureHandling.CONTINUE_ON_FAILURE)) {
		System.out.println('New Resource Header Present in the Scripture Viewer')

		if (WebUI.verifyElementVisible(findTestObject('Object Repository/Page_tC Create/Add new resource objects/div_11-russian text'),
			FailureHandling.CONTINUE_ON_FAILURE)) //verify the text is loaded
		{
			KeywordUtil.logInfo(' New Resource Text added by a URL is Present in the Scripture Viewer')
		} else {
			KeywordUtil.logInfo('Error:New Resource Text added by a URL is not present in the Scripture Viewer')
		}
	} else {
		KeywordUtil.logInfo('Error: New Resource added by a URL is not Present in the Scripture Viewer')
	}
} else {
	KeywordUtil.logInfo('Resource unchecked')
}
// ---------check other books--------
WebUI.delay(2)
//WebUI.scrollToElement(findTestObject('Object Repository/Page_tC Create/Hamburger Menu Button'), 2)
//WebUI.click(findTestObject('Object Repository/Page_tC Create/Hamburger Menu Button'))
try {
	WebUI.click(findTestObject('Object Repository/Page_tC Create/Hamburger Menu Button'), FailureHandling.STOP_ON_FAILURE)
} catch (Exception e) {
	WebUI.scrollToElement(findTestObject('Object Repository/Page_tC Create/chip_Repo'), 2)
	WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
}
WebUI.click(findTestObject('Object Repository/Page_tC Create/Add new resource objects/span_en_tn_01-GEN.tsv'))
WebUI.click(findTestObject('Object Repository/Page_tC Create/button_DrawerClose'))
WebUI.delay(1)
//validate
if(WebUI.verifyElementVisible(findTestObject('Object Repository/Page_tC Create/Add new resource objects/h6_GEN frontintro'), FailureHandling.CONTINUE_ON_FAILURE))
{
	KeywordUtil.logInfo('Other Books which do not have the added resource did not crash ')
}
else{
	KeywordUtil.logInfo('Other Books which do not have the added resource crashed ')
}
WebUI.closeBrowser()

