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

CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tN-Parallel Scripture Viewer')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()

WebUI.delay(2)
WebUI.scrollToElement(findTestObject('Object Repository/Page_tC Create/span_en_tn_57-TIT.tsv'), 1)
WebUI.click(findTestObject('Object Repository/Page_tC Create/span_en_tn_57-TIT.tsv'))
CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()

//Scrolls to top for the hamburger to be visible
//check whether the toggle is "on"
CustomKeywords.'com.tccreate.keywords.ExpandAllScriptureToggle.toggleAllScripture'('"test"')

//turn the toggle On if it is not
CustomKeywords.'com.tccreate.keywords.ExpandAllScriptureToggle.toggleAllScripture'('"on"')

//WebUI.scrollToElement(findTestObject('Page_tC Create/span_unfoldingWord Literal Text v14'), 2)
//Checks ULT in the scripture pane
//WebUI.scrollToElement(findTestObject('Object Repository/Page_tC Create/Add new resource objects/Page_1648836637269/h6_Titus 11'), 2, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.scrollToPosition(100,1200, FailureHandling.CONTINUE_ON_FAILURE)

//def row= ['Page_tC Create/span_unfoldingWord Literal Text v14','Page_tCC translationNotes/Page_tC Create/span_unfoldingWord Literal Text row 2' ]
//for(def i:0..row.size -1) {
if (WebUI.verifyElementPresent(findTestObject('Page_tC Create/span_unfoldingWord Literal Text v14'), 2)) {
    KeywordUtil.logInfo('ULT is visible 1 ')
} 
else {
   KeywordUtil.logInfo('Cannot find ULT in PSV in')
}

//Checks UST in the scripture pane
if (WebUI.verifyElementPresent(findTestObject('Page_tC Create/span_unfoldingWord Simplified Text v14'), 5)) {
 KeywordUtil.logInfo('UST is visible in row 1')
} else {
    KeywordUtil.logInfo('Cannot find UST in PSV in row1')
}

//KeywordUtil.logInfo('ParallelScripture is present')

//turn toggle Off
CustomKeywords.'com.tccreate.keywords.ExpandAllScriptureToggle.toggleAllScripture'('"off"')

KeywordUtil.logInfo('button is toggled')
WebUI.scrollToPosition(100,1200, FailureHandling.CONTINUE_ON_FAILURE)
if (WebUI.verifyElementNotPresent(findTestObject('Page_tC Create/span_unfoldingWord Literal Text v14'), 3)) {
    KeywordUtil.logInfo('ULT is not visible in row 1 as expected')
} else {
   KeywordUtil.logInfo('PSV is visible in row 1 when it should not be')
}

WebUI.closeBrowser()

