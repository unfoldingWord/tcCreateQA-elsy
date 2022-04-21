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

// 03/31/21 Modified to add test for the icon bar height (issue 742)

// TEST MOVING A ROW UP AND THEN BACK DOWN
// REDO THE TEST BUT THIS TIME SAVING THE FILE AFTER THE FIRST MOVE, THEN SAVING
// REOPEN THE SAVED PROJECT, VERIFY THAT THE CHANGED ORDER WAS SAVED, THEN RESTORE TO ORIGINAL ORDER

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(1)

expectedHeight = 48

barHeight = WebUI.getElementHeight(findTestObject('Page_tCC translationNotes/div_Icon_Toolbar'))

if (barHeight != expectedHeight) {
	println('ERROR: The icon bar height is ' + barHeight + ' pixels and ' + expectedHeight + ' was expected.')
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the icon bar height is ' + barHeight + ' pixels and ' + expectedHeight + ' was expected.')
}

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'ID']))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

// When project is loaded, ID rtc9 is above xyz8
rtc9Text = WebUI.getText(findTestObject('Page_tCC translationNotes/p_IdRow2'))

xyz8Text = WebUI.getText(findTestObject('Page_tCC translationNotes/p_IdRow3'))

if ((rtc9Text != 'rtc9') || (xyz8Text != 'xyz8')) {
    println('ERROR: Rows are not in the expected order')

    println('rtc9:' + rtc9Text)

    println('xyz8:' + xyz8Text)

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the rows are not in the expected order.')

    WebUI.delay(10)
} else {
    println('Rows are in expected order when project is opened')
}

WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/button_MoveRow_xyz8Up'), 1)

WebUI.click(findTestObject('Page_tCC translationNotes/button_MoveRow_xyz8Up'))

rtc9Text = WebUI.getText(findTestObject('Page_tCC translationNotes/p_IdRow2'))

xyz8Text = WebUI.getText(findTestObject('Page_tCC translationNotes/p_IdRow3'))

if ((rtc9Text != 'xyz8') || (xyz8Text != 'rtc9')) {
    println('ERROR: Rows do not appear to have been moved')

    println('rtc9:' + rtc9Text)

    println('xyz8:' + xyz8Text)

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the rows do not appear to have been moved.')

    WebUI.delay(10)
} else {
    println('Rows appear to have been moved as expected')
}

WebUI.delay(1)

WebUI.scrollToPosition(0, 2800)

WebUI.delay(1)

WebUI.click(findTestObject('Page_tCC translationNotes/button_MoveRow_xyz8Down'))

rtc9Text = WebUI.getText(findTestObject('Page_tCC translationNotes/p_IdRow2'))

xyz8Text = WebUI.getText(findTestObject('Page_tCC translationNotes/p_IdRow3'))

if ((rtc9Text != 'rtc9') || (xyz8Text != 'xyz8')) {
    println('ERROR: Rows were not returned to the original order')

    println('rtc9:' + rtc9Text)

    println('xyz8:' + xyz8Text)

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the rows were not returned to the original order.')

    WebUI.delay(10)
} else {
    println('Rows were returned to their original positions')
}

WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/button_MoveRow_xyz8Up'), 1)

WebUI.click(findTestObject('Page_tCC translationNotes/button_MoveRow_xyz8Up'))

WebUI.click(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'))

WebUI.delay(1)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(1)

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'ID']))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

// Now ID rtc9 should be below xyz8
rtc9Text = WebUI.getText(findTestObject('Page_tCC translationNotes/p_IdRow2'))

xyz8Text = WebUI.getText(findTestObject('Page_tCC translationNotes/p_IdRow3'))

if ((rtc9Text != 'xyz8') || (xyz8Text != 'rtc9')) {
    println('ERROR: Rows do not appear to have been moved before the Save')

    println('rtc9:' + rtc9Text)

    println('xyz8:' + xyz8Text)

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the rows do not appear to have been moved before the Save.')

    WebUI.delay(10)
} else {
    println('Rows were moved and saved as expected')
}

WebUI.delay(1)

WebUI.scrollToPosition(0, 2800)

WebUI.click(findTestObject('Page_tCC translationNotes/button_MoveRow_xyz8Down'))

rtc9Text = WebUI.getText(findTestObject('Page_tCC translationNotes/p_IdRow2'))

xyz8Text = WebUI.getText(findTestObject('Page_tCC translationNotes/p_IdRow3'))

if ((rtc9Text != 'rtc9') || (xyz8Text != 'xyz8')) {
    println('ERROR: Rows were not returned to the original order')

    println('rtc9:' + rtc9Text)

    println('xyz8:' + xyz8Text)

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the rows were not returned to the original order.')

    WebUI.delay(10)
} else {
    println('Rows have been returned to their original positions after save')
}

WebUI.click(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'))

WebUI.delay(1)

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

