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

//CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tN-Highlighting by occurrence number')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
WebUI.click(findTestObject('Object Repository/Page_tC Create/tn_TIT.tsv'))
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
WebUI.click(findTestObject('Object Repository/tN objects/button_view columns'))
WebUI.click(findTestObject('tN objects/input_OrigQuote_checkbox'))
WebUI.click(findTestObject('Object Repository/tN objects/input_Occurrence'))
WebUI.click(findTestObject('tN objects/button__Close-Columns'))

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
 //scroll to PSV
 WebUI.scrollToPosition(100,2100)
	 //WebUI.scrollToElement(findTestObject('Object Repository/tN objects/div_Titus 11'), 2)
	 
 // Add a new Row
	 WebUI.click(findTestObject('Object Repository/tN objects/button_Titus 11_add a new row'))
	 WebUI.click(findTestObject('Object Repository/tN objects/button_Add'))

	
	// copy and paste a OL word to ORiginal Quote text box
	 def word1 = WebUI.getText(findTestObject('tN objects/word1'))
	 KeywordUtil.logInfo("the word is" + word1)
	 
	 def color_word1 = WebUI.getCSSValue(findTestObject('tN objects/word1'), 'color');
	 
	 def backcolor_word1 = WebUI.getCSSValue(findTestObject('tN objects/word1'), "background-color");
	 
	 KeywordUtil.logInfo(color_word1);
	 
	 KeywordUtil.logInfo(backcolor_word1);
	 
	 
	
	 WebUI.click(findTestObject('Object Repository/tN objects/p_OL_box'))
	 WebUI.setText(findTestObject('Object Repository/tN objects/p_OL_box'), 'Θεοῦ')
	
	

	 WebDriver driver = DriverFactory.getWebDriver()
	 
		 Actions action = new Actions(driver)
	 
		 action.sendKeys(Keys.TAB).build().perform()
	 
	 if(!color_word1.equals(backcolor_word1)){
	 
	KeywordUtil.logInfo("First Occurrence is highlighted!")
	 
	 }
	 
	 else{
	 
	 KeywordUtil.logInfo("First Occurrence is not highlighted! or No hightlight at all!")
	 }
	 
	 //set the occurrence number to 2
	 def color_word2 = WebUI.getCSSValue(findTestObject('tN objects/word2'), 'color');
	 
	 def backcolor_word2 = WebUI.getCSSValue(findTestObject('tN objects/word2'), "background-color");
	 WebUI.click(findTestObject('Object Repository/tN objects/p_occurrence box'))
	 WebUI.clearText(findTestObject('Object Repository/tN objects/p_occurrence box'), FailureHandling.CONTINUE_ON_FAILURE)
	 WebUI.sendKeys(findTestObject('Object Repository/tN objects/p_occurrence box'), "2")

	 //tab out of the box
	 
	 action.sendKeys(Keys.TAB).build().perform()
	 
	 
	 //check if the words are highlighted
	

	 if(!color_word2.equals(backcolor_word2)){
	 
	KeywordUtil.logInfo("Second occcurrence is highlighted!")
	 
	 }
	 
	 else{
	 
	 KeywordUtil.logInfo("Text is not highlighted!")
	 }
	
	 //set the occurrence number to -1
	 //WebUI.click(findTestObject('Object Repository/tN objects/p_occurrence box'))
	 WebUI.clearText(findTestObject('Object Repository/tN objects/p_occurrence box'), FailureHandling.CONTINUE_ON_FAILURE)
	 WebUI.sendKeys(findTestObject('Object Repository/tN objects/p_occurrence box'), "-1")

	 //tab out of the box
	 
	 action.sendKeys(Keys.TAB).build().perform()
	 
	 
	 //check if the words are highlighted
	

	 if(!color_word1.equals(backcolor_word1) && !color_word2.equals(backcolor_word2) ){
	 
	 KeywordUtil.logInfo("Both occurrences are highlighted!")
	 
	 }
	 
	 else{
	 
	KeywordUtil.logInfo("Text is not highlighted!")
	 }
	 WebUI.delay(2)
 WebUI.closeBrowser()

