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

//LOGIN
WebUI.callTestCase(findTestCase('tCC Components/tCC Login'), [('user') : $username, ('password') : $password, ('newSession') : true], 
    FailureHandling.STOP_ON_FAILURE)

organization = GlobalVariable.organization

if (WebUI.waitForElementPresent(organization, 5, FailureHandling.OPTIONAL) == false) {
    println('Failed to find organization option. Suspect infinite spinner')

    WebUI.takeScreenshot()

    return false
}

testToolTip('Page_tC Create/button_LinkToOrgDCS', 'Go to Organization')

WebUI.delay(1)

WebUI.click(organization)

testToolTip('Page_tC Create/button_ResourceGoToRepo', 'Go to Repo')

WebUI.click(findTestObject('Page_tC Create/resource_Parmed', [('resource') : 'unfoldingWord/en_tn']))

WebUI.click(findTestObject('Page_tC Create/combo_Select Language'))

WebUI.click(findTestObject('Page_tC Create/listOption_Language_Parmed', [('lang_code') : GlobalVariable.langCode]))

WebUI.click(findTestObject('Page_tC Create/file_Parmed', [('fileName') : GlobalVariable.tNFile]))

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_ID'))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

WebUI.scrollToPosition(0, 2800)

WebUI.delay(1)

testToolTip('Page_tCC translationNotes/button_AddARow', 'Add a Row')

testToolTip('Page_tCC translationNotes/button_DeleteARow', 'Delete a Row')

testToolTip('Page_tCC translationNotes/button_MoveRowUp', 'Move Row Up')

testToolTip('Page_tCC translationNotes/button_MoveRowDown', 'Move Row Down')

testToolTip('Page_tCC translationNotes/button_CloseScripturePane', 'Close Scripture Pane')

testToolTip('Page_tCC translationNotes/button_ManageVersions', 'Manage Versions')

testToolTip('Page_tCC translationNotes/button_ExpandChapter', 'Expand Chapter')

WebUI.click(findTestObject('Page_tCC translationNotes/button_CloseScripturePane'))

testToolTip('Page_tCC translationNotes/button_ExpandScripturePane', 'Expand Scripture Pane')

WebUI.click(findTestObject('Page_tCC translationNotes/button_ExpandChapter'))

WebUI.scrollToPosition(0, 2800)

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/p_figs-abstractnouns'))

testToolTip('Page_tCC translationNotes/button_CollapseChapter', 'Collapse Chapter')

testToolTip('Page_tCC translationNotes/button_NextPage', 'Next Page')

WebUI.click(findTestObject('Page_tCC translationNotes/button_NextPage'))

testToolTip('Page_tCC translationNotes/button_PreviousPage', 'Previous Page') //WebUI.waitForElementVisible(findTestObject('Page_tCC translationNotes/button_AddARow_Intro'), 1)

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

def testToolTip(def object, def toolTip) {
    println('Testing tooltip for object ' + object)

    WebUI.waitForElementPresent(findTestObject(object), 2)

    text = WebUI.getAttribute(findTestObject(object), 'Title')

    println('The Title text is ' + text)

    if (WebUI.getAttribute(findTestObject(object), 'Title') != toolTip) {
        println(('ERROR: ' + toolTip) + ' tool tip not found.')

        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because the ' + toolTip) + ' tool tip was not found.')
    } else {
        println(toolTip + ' tool tip found')
    }
    
    WebUI.mouseOver(findTestObject(object))

    WebUI.delay(1)

    if (WebUI.verifyTextPresent(toolTip, false, FailureHandling.OPTIONAL)) {
        println(toolTip + ' tool tip text displayed')
    } else {
        println(('ERROR: The ' + toolTip) + ' tool tip text was not displayed.')

        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because the ' + toolTip) + ' tool tip text was not displayed.')
    }
}

