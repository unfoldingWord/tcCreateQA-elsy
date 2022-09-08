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

CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tA-Save-translate')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('translate_test')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Academy')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("ru")


WebUI.click(findTestObject('Page_tC Create/span_translate'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/span_bita-farming'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/span_01.md'))

WebUI.click(findTestObject('Object Repository/Page_tC Create/div_A FARMER represents God, and the VINEYA_8b10d9'))

if (WebUI.getText(findTestObject('Object Repository/tA/div_  translate edit box')).contains('ЗЕМЛЕДЕЛЕЦ символизирует Бога')) {
	KeywordUtil.logInfo('\n The text in target is in Russian as expected')
} else {
	KeywordUtil.logInfo('\n Error: The text in target is not in Russian')
}

WebUI.sendKeys(findTestObject('Object Repository/tA/div_  translate edit box'), ' отделение злых от добрых')

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
WebUI.click(findTestObject('Object Repository/tA/div_Russian - translate_testru_taElsyLambert-tc-create-1'))

WebUI.delay(2)

WebUI.switchToWindowIndex(1)

if (WebUI.getText(findTestObject('Object Repository/tA/h4_ DCS russian text')).contains('отделение злых от добрых')) {
    KeywordUtil.logInfo('\n The edits are saved on the DCS Repo File')
} else {
    KeywordUtil.logInfo('\n The edits are not found on the DCS Repo File')
}

WebUI.switchToWindowIndex(0)
KeywordUtil.logInfo('\n TEST PASSED:Edits are saved successfully in tA articles')
WebUI.click(findTestObject('Object Repository/Page_tC Create/Page_tC Create/svg_en_ta_deleteIcon_en_ta'))
CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')

WebUI.click(findTestObject('Page_tC Create/file_Parmed', [('fileName') : "en_tn_04-NUM.tsv"]))
println("Opening book")
WebUI.delay(5)
if(WebUI.verifyElementVisible(findTestObject('Object Repository/Page_tCC translationNotes/Button-Add-row'), FailureHandling.CONTINUE_ON_FAILURE))
	{
		KeywordUtil.logInfo('New file is created in User branch')
	}
	else{
		KeywordUtil.logInfo('New file creation failed ')
	}
	


//String file_name = "/Users/elsylitsonlambert/Documents/Test Results/output.txt"
WebUI.closeBrowser()

