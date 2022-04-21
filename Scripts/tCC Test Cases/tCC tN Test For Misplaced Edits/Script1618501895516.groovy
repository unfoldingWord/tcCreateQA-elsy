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
import com.kms.katalon.core.testobject.ConditionType as ConditionType

baseRepo = 'https://qa.door43.org/translate_test/en_tn/src/branch/tc01-tc-create-1/'

//testFiles = ['en_tn_23-ISA.tsv', 'en_tn_42-MRK.tsv',  'en_tn_44-JHN.tsv', 'en_tn_45-ACT.tsv', 'en_tn_46-ROM.tsv', 'en_tn_47-1CO.tsv', 'en_tn_48-2CO.tsv']
//testFiles = ['en_tn_43-LUK.tsv']
//testFiles = ['en_tn_65-3JN.tsv']
testFiles = ['en_tn_57-TIT.tsv']

testFiles.each { file ->
//for (file in testFiles) {
	WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('file') : file], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.delay(15)
	
	rows = WebUI.getText(findTestObject('Page_tCC translationNotes/list_RowsPerPage'))
	
	if (rows != '25') {
	    println(('ERROR: The initial rows per page is set to ' + rows) + ' when it should be 25.')
	
	    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because the initial rows per page is set to ' + 
	        rows) + ' when it should be 25.')
		return false
	}
		
	page = 'Page_tCC translationNotes/'
	
	columns = []
	
	columns = ['Page_tCC translationNotes/columns_Book', 'Page_tCC translationNotes/columns_Chapter', 'Page_tCC translationNotes/columns_Verse'
	    , 'Page_tCC translationNotes/columns_ID', 'Page_tCC translationNotes/columns_OrigQuote', 'Page_tCC translationNotes/columns_Occurrence'
	    , 'Page_tCC translationNotes/columns_GLQuote']
	
	WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))
	
	KeywordUtil.logInfo('Setting all columns ON')
	
	//One by one set each column on 
	for (def c : (0..columns.size() - 1)) {
	    col = (columns[c])
	
	    WebUI.click(findTestObject(col))
	
	    WebUI.verifyElementChecked(findTestObject(col), 1, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))
	
	atEnd = false
	
	done = false
	
	pages = 0
	
	row = 1
	
	myRow = row
	
	repoLines = []
	
	repoTexts = []
	
	while (!(done) && (pages < 300)) {
	    myRow += 1

		if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_tCC translationNotes/last_ID', [('line') : row]), 1, FailureHandling.OPTIONAL)) {
	
		    myID = WebUI.getText(findTestObject('Object Repository/Page_tCC translationNotes/last_ID', [('line') : row]))
		
		    myText = ((('Testing-row-' + myRow) + '-ID=') + myID)
		
		    WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/last_SupportReference', [('line') : row]), 
		        myText)
			
			println('Updated ID ' + myID + ' on row ' + (myRow-1))
		
		    repoLines.add(myRow)
		
		    repoTexts.add(myText)
		
		    row += 24
		
		    myRow += 24
		
			if (WebUI.verifyElementPresent(findTestObject('Object Repository/Page_tCC translationNotes/last_ID', [('line') : row]), 1, FailureHandling.OPTIONAL)) {
	
			    myID = WebUI.getText(findTestObject('Object Repository/Page_tCC translationNotes/last_ID', [('line') : row]))
				
				while (myID.length() == 0) {
					row -= 1
					
					myRow -= 1
					
					myID = WebUI.getText(findTestObject('Object Repository/Page_tCC translationNotes/last_ID', [('line') : row]))
					
				}
			
			    myText = ((('Testing-row-' + myRow) + '-ID=') + myID)
			
			    WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/last_SupportReference', [('line') : row]), 
			        myText)
			
				println('Updated ID ' + myID + ' on row ' + (myRow-1))
			
			    repoLines.add(myRow)
			
			    repoTexts.add(myText)
			}
		}
		
		if (!atEnd) {
	
		    row = 1
		
		    WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'))
		
		    count = 0
		
		    while ((count < 5) && !(WebUI.verifyElementClickable(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'), 
		        FailureHandling.OPTIONAL))) {
		        WebUI.delay(1)
		
		        count++
		    }
		    
		    if (count >= 5) {
		        println('Next Page was not clickable after 5 seconds, assuming last page.')
		
		        atEnd = true
		    }
			
		} else {
			done = true
		}
	    
	    pages++
	}
	
	WebUI.click(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'))
	
	WebUI.delay(10)
	
	WebUI.closeBrowser()

	
	// Navigate to dcs repo
	dcsRepo = baseRepo + file 
	
	WebUI.openBrowser(dcsRepo)
	
	if (WebUI.waitForElementPresent(findTestObject('Page_Git Repo/headerRow_Book'), 15)) {
	
		WebUI.delay(2)
			
		table = WebUI.getText(findTestObject('Object Repository/Page_Git Repo/table_GitRepo'))
		
		errorCount = 0
		
		row = 0
		table.splitEachLine(' ', { def fields ->
			book = fields[0]
			chapter = fields[1]
			verse = fields[2]
			id = fields[3]
			sRef = fields[4]
			if (sRef.indexOf('Testing-row-') >= 0 ) {
				if (fields[4].indexOf(fields[3]) < 0) {
					println('ERROR: on row ' + (row+1) + ', ' + fields[3] + ' not found in ' + fields[4])
					println('The ID is [' + fields[3] + '] and the SupportReference is [' + fields[4] + '].')
					errorCount ++
				} else {
					println('Found match on row ' + (row+1))
				}
			}
			row ++
			
		})
	}
	
	println(errorCount + ' errors were found in ' + file + '.')
	
	WebUI.closeBrowser()
}

GlobalVariable.scriptRunning = false

