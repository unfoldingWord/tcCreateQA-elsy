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
import java.time.LocalDateTime as LocalDateTime // Import the LocalDateTime class
import java.time.format.DateTimeFormatter as DateTimeFormatter // Import the DateTimeFormatter class
import java.io.File as File

// Loads all tN projects (all books, NT only, or custom - set myBooks) and pages to the end to test for app crash
// THIS TEST RUNS FOR ABOUT 1 HOUR WHEN TESTING ONLY THE NEW TESTAMENT BOOKS

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], 
    FailureHandling.STOP_ON_FAILURE)

testFiles = []

//Users/cckozie/Katalon Studio/Files/Reference

//allBooks = '/Users/' + GlobalVariable.pcUser + '/Documents/Sikuli/Files/Bible_Books.csv'
allBooks = '/Users/' + GlobalVariable.pcUser + '/Katalon Studio/Files/Reference/Bible_Books.csv'

//ntBooks = '/Users/' + GlobalVariable.pcUser + '/Documents/Sikuli/Files/NT_Books.csv'
ntBooks = '/Users/' + GlobalVariable.pcUser + '/Katalon Studio/Files/Reference/NT_Books.csv'

//someBooks = '/Users/' + GlobalVariable.pcUser + '/Documents/Sikuli/Files/Some_Books.csv'
someBooks = '/Users/' + GlobalVariable.pcUser + '/Katalon Studio/Files/Reference/Some_Books.csv'

myBooks = ntBooks

new File(myBooks).splitEachLine(',', { def fields ->
        bookNum = (fields[0])

        if (bookNum.length() < 2) {
            bookNum = ('0' + bookNum)
        }
        
        bookAbrv = (fields[1])

        bookName = (((('en_tn_' + bookNum) + '-') + bookAbrv) + '.tsv')

        testFiles.add(bookName)
    })

//for (def testFile in testFiles) {
testFiles.each({ def testFile ->
        println('Testing ' + testFile)

        if (CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseFile'(testFile)) {
			
			//println("inside the test function")

			WebUI.click(findTestObject('Page_tC Create/button_DrawerClose'), FailureHandling.OPTIONAL)
			WebUI.delay(1)
		
			WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/list_RowsPerPage'))
			
			WebUI.delay(1)
			
			WebUI.click(findTestObject('Page_tCC translationNotes/option_RowsPerPage_parmned', [('rows') : 100]))
		
	        atEnd = false
	
	        while (!(atEnd)) {
	            WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'))
	
	            if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'), 
	                2)) {
				    count = 0
	                while (count < 5 && !(WebUI.verifyElementClickable(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'), 
	                    FailureHandling.OPTIONAL))) {
	                    WebUI.delay(1)
						count ++
	                }
					if (count >= 5) {
						println('Next Page was not clickable after 5 seconds, assuming last page.')
						atEnd = true
					}
	            } else {
					println('ERROR: Not able to find Next Page button. Assume app crash in ' + testFile + '.')
					CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the test was not able to find the Next Page button. Assume app crashed ' + testFile + '.')
	            }
	        }
		} else {
			println('ERROR: Failed to load ' + testFile)
			CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because ' + testFile + ' failed to laod. Assume app crashed.')
			WebUI.closeBrowser()
			WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], 
			    FailureHandling.STOP_ON_FAILURE)
		}
    })

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

