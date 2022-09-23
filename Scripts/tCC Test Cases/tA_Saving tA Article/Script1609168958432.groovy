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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tA-Save')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Academy')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()

WebUI.click(findTestObject('Page_tC Create/span_translate'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/span_bita-farming'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/span_01.md'))
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()

WebUI.click(findTestObject('Object Repository/Page_tC Create/div_A FARMER represents God, and the VINEYA_8b10d9'))

WebUI.sendKeys(findTestObject('Object Repository/tA/div_A FARMER represents God,edit box'), ' test edit by the test script')

WebUI.click(findTestObject('Page_tC Create/button_Save'))

//validate
//save button is garyed out
WebUI.delay(2)

if (WebUI.verifyElementNotClickable(findTestObject('Page_tC Create/button_Save'), FailureHandling.CONTINUE_ON_FAILURE)) {
    //System.out.println(' Save button is greyed out as expected')
    KeywordUtil.logInfo(' \n Save button is greyed out as expected' // System.out.println(' Error: Save button is still enabled')
        )
} else {
    KeywordUtil.logInfo('\n  Error: Save button is still enabled')
}

//Validate changes on DCS Repo
WebUI.click(findTestObject('Object Repository/tA/Chip_English - unfoldingWorden_taElsyLambert-tc-create-1'))

WebUI.delay(2)

WebUI.switchToWindowIndex(1)

if (WebUI.getText(findTestObject('Object Repository/tA/h4_dcs article')).contains('test edit by the test script')) {
    KeywordUtil.logInfo('\n The edits are saved on the DCS Repo File')
} else {
    KeywordUtil.logInfo('\n The edits are not found on the DCS Repo File')
}

WebUI.switchToWindowIndex(0)

KeywordUtil.logInfo('\n TEST PASSED:Edits are saved successfully in tA articles')

//String file_name = "/Users/elsylitsonlambert/Documents/Test Results/output.txt"
WebUI.closeBrowser()

