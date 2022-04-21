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

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : $username, ('$password') : $password, ('file') : ''], 
    FailureHandling.STOP_ON_FAILURE)

baseDir = (GlobalVariable.projectPath + '/Data Files/')

books = []

//new File('/Users/' + GlobalVariable.pcUser + '/Katalon Studio/Files/Reference/Bible_Books.csv').splitEachLine(',', { def fields ->
//new File('/Users/' + GlobalVariable.pcUser + '/Katalon Studio/Files/Reference/NT_Books.csv').splitEachLine(',', { def fields ->
//new File('/Users/' + GlobalVariable.pcUser + '/Katalon Studio/Files/Reference/One_Book.csv').splitEachLine(',', { def fields ->
new File(GlobalVariable.projectPath + '/Data Files/Reference/Bible_Books.csv').splitEachLine(',', { def fields ->
        bookNum = (fields[0])

        if (bookNum.length() < 2) {
            bookNum = ('0' + bookNum)
        }
        
        bookAbrv = (fields[1])

        bookName = (((('en_tn_' + bookNum) + '-') + bookAbrv) + '.tsv')

        books.add(bookName)
    })

println(books)

for (def book : books) {
    println('Opening ' + book)

    retCode = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseFile'(book)

    WebUI.delay(2)
	// If there was an on-open validation error, then load the default tN file before proceding with the next book from the drawer
	if (!retCode && WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/button_validator_Message_Close'), 1, FailureHandling.OPTIONAL)) {
		alertHeader = WebUI.getText(findTestObject('Page_tCC translationNotes/alert_validator_Msg_Header'))
		if (alertHeader.contains('target')) {
			errorFile = 'TARGET'
		} else {
			errorFile = 'SOURCE'
		}
		msg = book + ' has on-open validation errors in the ' + errorFile + ' file.'
		println(msg)
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)
		WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_validator_Message_Close'))
		WebUI.click(findTestObject('Page_tC Create/button_DrawerClose'), FailureHandling.OPTIONAL)
		WebUI.waitForElementVisible(findTestObject('Page_tC Create/button_DrawerOpen'), 5)
		WebUI.click(findTestObject('Page_tC Create/resource_Parmed', [('resource') : 'unfoldingWord/en_tn']))
		WebUI.click(findTestObject('Page_tC Create/file_Parmed', [('fileName') : GlobalVariable.tNFile]))
		
	}
	
}

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

