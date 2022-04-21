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

edit = true
test = true

goToRow = ''
if (goToRow != '') {
	firstRow = goToRow as Integer
}

//testFiles = ['en_tn_23-ISA.tsv', 'en_tn_42-MRK.tsv',  'en_tn_44-JHN.tsv', 'en_tn_45-ACT.tsv', 'en_tn_46-ROM.tsv', 'en_tn_47-1CO.tsv', 'en_tn_48-2CO.tsv']
//testFiles = ['en_tn_42-MRK.tsv'] //,  'en_tn_44-JHN.tsv', 'en_tn_45-ACT.tsv', 'en_tn_46-ROM.tsv', 'en_tn_47-1CO.tsv', 'en_tn_48-2CO.tsv']
//testFiles = ['en_tn_16-NEH.tsv']
//testFiles = ['en_tn_50-EPH.tsv']
testFiles = ['en_tn_43-LUK.tsv']
//testFiles = ['en_tn_55-1TI.tsv']

repo = 'git'

user = 'tcc001'

//for (file in testFiles) {
	testFiles.each { file ->
	
	if (edit) {
	
		WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('file') : file], FailureHandling.STOP_ON_FAILURE)
		
		WebUI.delay(15)
		
		rows = WebUI.getText(findTestObject('Page_tCC translationNotes/list_RowsPerPage'))
		
		if (rows != '25') {
		    println(('ERROR: The initial rows per page is set to ' + rows) + ' when it should be 25.')
		
		    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because the initial rows per page is set to ' + 
		        rows) + ' when it should be 25.')
		}
				
		// Count the number of SupportReference fields on the screen (this counts both from the source and target)
		List elements = WebUI.findWebElements(new TestObject().addProperty('xpath', ConditionType.EQUALS, '//*[@class = \'MuiTypography-root-114 jss255 MuiTypography-subtitle2-126 MuiTypography-colorTextSecondary-140 MuiTypography-alignLeft-129\' and (text() = \'SupportReference\' or . = \'SupportReference\')]'), 
		    10)
		
		totalRows = (elements.size() / 2)
		
		page = 'Page_tCC translationNotes/'
			
		//def columns = new String[10]
		columns = []
		
		columns = ['Page_tCC translationNotes/columns_Book', 'Page_tCC translationNotes/columns_Chapter', 'Page_tCC translationNotes/columns_Verse'
		    , 'Page_tCC translationNotes/columns_ID', 'Page_tCC translationNotes/columns_OrigQuote', 'Page_tCC translationNotes/columns_Occurrence'
		    , 'Page_tCC translationNotes/columns_GLQuote']
		
		WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))
		
		KeywordUtil.logInfo('Setting all columns ON')
		
		for (def c : (0..columns.size() - 1)) {
		    //    col = (page + (columns[c]))
		    col = (columns[c])
		
		    WebUI.uncheck(findTestObject(col), FailureHandling.OPTIONAL)
		}
		
		//One by one set each column on 
		for (def c : (0..columns.size() - 1)) {
		    col = (columns[c])
		
		    WebUI.click(findTestObject(col))
		
		    WebUI.verifyElementChecked(findTestObject(col), 1, FailureHandling.CONTINUE_ON_FAILURE)
		}
		
		WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))
		
		row = 1 
		
		if (goToRow != '') {
			nextPage(goToRow)
			row = firstRow
		}
		
		lastPage = false
	
		atEnd = false
		
		pages = 0
		
		myRow = row + 1
		
		while (!(atEnd) && (pages < 250)) {
			
			myID = 'unknown'
		    myID = WebUI.getText(findTestObject('Object Repository/Page_tCC translationNotes/last_ID', [('line') : row]), FailureHandling.OPTIONAL)
			
			if (myID == '' || myID.length() < 4) {
				println('myID is blank on row ' + myRow)
				row ++				
				myRow ++			
			} else {
		
				myText = ((('Testing-row-' + myRow) + '-ID=') + myID)
		
			    WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/last_SupportReference', [('line') : row]), 
			        myText, FailureHandling.OPTIONAL)
				
				row ++
				
				myRow ++
			
				println('\n>>>>> Row is ' + row + ', myRow is ' + myRow)
				if (row >= 26) {
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
						
						if (lastPage) {
							atEnd = true
						} else {
							lastPage = true
						}
						
				    }
				    
				    pages++
					
					WebUI.click(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'))
					
					while (WebUI.verifyTextPresent('Save', false, FailureHandling.OPTIONAL)) {
						println('Save present')
						WebUI.mouseOverOffset(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'), -100, 0)
					}
					
				
				}
			}
		}
		
		WebUI.click(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'), FailureHandling.OPTIONAL)
		
		while (WebUI.verifyTextPresent('Save', false, FailureHandling.OPTIONAL)) {
		    println('Save present')
			WebUI.mouseOverOffset(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'), -100, 0)
		}
		
		WebUI.delay(2)
		
		WebUI.closeBrowser()
	}

	if (test) {
		
		repoLines = []
		
		repoTexts = []
		
	//	dcsRepo = 'https://git.door43.org/translate_test/en_tn/raw/branch/tcc001-tc-create-1/en_tn_43-LUK.tsv'
//		dcsRepo = 'https://' + repo + '.door43.org/translate_test/en_tn/raw/branch/' + user + '-tc-create-1/' + file
//		baseRepo = 'https://qa.door43.org/translate_test/en_tn/src/branch/tc01-tc-create-1/'
		dcsRepo = 'https://' + repo + '.door43.org/translate_test/en_tn/src/branch/' + user + '-tc-create-1/' + file
		
		
		WebUI.openBrowser(null)
		
		WebUI.navigateToUrl(dcsRepo)
		
		if (WebUI.waitForElementPresent(findTestObject('Page_Git Repo/headerRow_Book'), 15)) {
		
			WebUI.delay(5)
				
			table = WebUI.getText(findTestObject('Object Repository/Page_Git Repo/table_GitRepo'))
			
			errorCount = 0
			
			row = 0
			table.splitEachLine(' ', { def fields ->
				book = fields[0]
				chapter = fields[1]
				verse = fields[2]
				id = fields[3]
				sRef = fields[4]+fields[5]+fields[6]+fields[7]+fields[8]+fields[9]
				if (sRef.indexOf('Testingrow') >= 0 ) {
					if (sRef.indexOf(id) < 0) {
						msg = 'ERROR: on row ' + (row+1) + ', ' + id + ' not found in ' + sRef + '. The ID is [' + id + '] and the SupportReference is [' + sRef + '].'
						CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(msg)
						return false
						errorCount ++
					}
				}
				row ++
				
			})
		}
		
		println(errorCount + ' errors were found in ' + file + '.')
	
		WebUI.closeBrowser()
	}

}

GlobalVariable.scriptRunning = false

def nextPage(row) {
	
	while (WebUI.getText(findTestObject('Object Repository/Page_tCC translationNotes/text_RowsOnPage')).indexOf(row) < 0) {

		WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'))
		
		count = 0
	
		while ((count < 5) && !(WebUI.verifyElementClickable(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'),
			FailureHandling.OPTIONAL))) {
			WebUI.delay(1)
	
			count++
		}
	}
}