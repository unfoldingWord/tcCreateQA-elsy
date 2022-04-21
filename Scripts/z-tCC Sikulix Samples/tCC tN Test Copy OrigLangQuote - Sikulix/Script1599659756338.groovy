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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.sikuli.script.*

WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_OrigQuote'))

WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/btnX_CloseColumns'))

Screen s = new Screen()

String tCCName = '/Users/cckozie/Katalon Studio/Sikuli Images/tCC_Name.png'

String wasQuote = '/Users/cckozie/Katalon Studio/Sikuli Images/was.png'

String nowQuote = '/Users/cckozie/Katalon Studio/Sikuli Images/now.png'

String nowQuoteHl = '/Users/cckozie/Katalon Studio/Sikuli Images/nowHl.png'

Pattern nowHl = new Pattern(nowQuoteHl).similar(0.5)

Pattern was = new Pattern(wasQuote).similar(0.5)

Pattern name = new Pattern(tCCName)

Region r = s.find(name)

Integer w = s.getW() / 2

enReg = Region.create(0, r.getY() + 30, w, s.getH() - r.getY())

enReg.highlight(1)

WebUI.setText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), '')

backColor = WebUI.getCSSValue(findTestObject('null'), 'background-color')

println('The background color is ' + backColor)

String origQuoute = WebUI.getText(findTestObject('null'))

WebUI.setText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), origQuoute)

WebUI.clickOffset(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), 0, -20)

enReg.highlight(1)

match = enReg.find(nowHl)

//match = enReg.find(was)
score = match.getScore()

println('Match score is ' + score)

if (score >= 0.85) {
    println('New orignal quote was highlighted')
} else {
    println('New orignal quote was not highlighted')

    KeywordUtil.markFailed('Failed message')
}

//String backcolor = driver.findElement(By.xpath('//a[@id=\'btn-make-appointment\']')).getCssValue('background-color')

//findTestObject('null')

backColor = WebUI.getCSSValue(findTestObject('null'), 'background-color')

println('The background color is ' + backColor)

not_run: origQuote = WebUI.getText(findTestObject('null'))

not_run: println('Original language quote is ' + origQuote)

not_run: String newOrigQuote = origQuote + ' abc'

not_run: println('Setting original language quote to ' + newOrigQuote)

not_run: WebUI.setText(findTestObject('null'), newOrigQuote)

not_run: WebUI.clickOffset(findTestObject('null'), 0, -20)

not_run: WebUI.click(findTestObject('Page_tCC translationNotes/button_SaveEnabled'))

not_run: WebUI.waitForElementNotPresent(findTestObject('Page_tCC translationNotes/button_SaveEnabled'), 10)

not_run: WebUI.verifyElementText(findTestObject('null'), newOrigQuote)

not_run: WebUI.closeBrowser()

'If user is empty the global variables from the profile will be used for username and password'
not_run: WebUI.callTestCase(findTestCase('tCC Components/tCC Login'), [('user') : '', ('password') : ''], FailureHandling.STOP_ON_FAILURE)

not_run: WebUI.callTestCase(findTestCase('null'), [('resource') : 'unfoldingWord/en_tn'], FailureHandling.STOP_ON_FAILURE)

not_run: WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

not_run: WebUI.click(findTestObject('Page_tCC translationNotes/columns_OrigQuote'))

not_run: WebUI.click(findTestObject('null'))

not_run: WebUI.closeBrowser()

