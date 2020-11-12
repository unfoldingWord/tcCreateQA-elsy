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
 
 WebUI.click(findTestObject('Page_tC Create/Organization unfoldingWord'))
 
 WebUI.click(findTestObject('Object Repository/Page_tC Create/div_unfoldingWord Translation Academyunfold_cb119f'))
 
 WebUI.click(findTestObject('Object Repository/Page_tC Create/p_Select Language'))
 
 WebUI.setText(findTestObject('Object Repository/Page_tC Create/input_Step4Select Your Language_react-selec_aae5da'), 'en')
 
 WebUI.click(findTestObject('Object Repository/Page_tC Create/div_en - English - English (Europe Gateway)'))
 
 WebUI.click(findTestObject('Page_tC Create/span_translate'))
 
 WebUI.click(findTestObject('Object Repository/Page_tC Create/span_bita-farming'))
 
 WebUI.click(findTestObject('Object Repository/Page_tC Create/div_01.md4.68 KB'))
 
 WebUI.click(findTestObject('Object Repository/Page_tC Create/div_A FARMER represents God, and the VINEYA_8b10d9'))
 WebUI.click(findTestObject('Object Repository/Page_tC Create/h4_A FARMER represents God, and the VINEYARD represents his chosen people'))
 
 WebUI.setText(findTestObject('Object Repository/Page_tC Create/h4_A FARMER represents God, and the VINEYARD represents his chosen people'), 'test edit the first time') // need to look why set text add the other text
 WebUI.delay(4)
 
 
 WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/h4_A FARMER represents God, and the VINEYARD represents his chosen people'), Keys.chord(Keys.COMMAND, 'z')) 
 System.out.println("Text is cleared successfully by undo action")
 WebUI.setText(findTestObject('Object Repository/Page_tC Create/Edit box'), "test edit after undo action")
	 
WebUI.click(findTestObject('Page_tC Create/button_Save'))
 
 WebUI.closeBrowser()