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

// NEED TO ADD TESTS FOR THE INITIAL PROBLEMS WHEN ATTEMPTING TO RESOLVE ERRORS IN TN
// NEED TO ADD TESTS FOR THE INTRODUCTION OF ERRORS AFTER FIXING THE EXISTING ONES

testFile = 'en_tn_50-EPH.tsv'

dirName = '/Users/cckozie/Downloads'

baseDir = '/Users/cckozie/Katalon Studio/Files/Reference/'

baseFile = baseDir + 'Validation-' + testFile + '_base.csv'

bFile = new File(baseFile)
baseContent = bFile.text

println ('baseContent:' + baseContent)

// Get the count of how many validation files already exist for the file being tested
vFiles = getValidationFiles(testFile)

initSize = vFiles.size()

// Load the project in tN
WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('file') : testFile], 
    FailureHandling.STOP_ON_FAILURE)

// Run the validation
(vSize,newContent) = runValidation(initSize)

println('newContent:' + newContent)

// Test to see if the validation results are what was expected based on the originally saved validation file
if (newContent != baseContent) {
	println('ERROR: Initial validation content does not match the base content')
    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the initial validation content does not match the base content.')
	WebUI.closeBrowser()
	return false
} else {
	println('Initial content matches the base content')
}

WebUI.delay(1)

// Show the additional required columns
WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'ID']))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_Parmed', [('column') : 'OrigQuote']))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

// Fix the validation errors
u2bwBaseQuote = 'ἑνὶ…ἐκάστῳ ἡμῶν ἐδόθη ἡ χάρις'

u2bwNewQuote = 'ἑνὶ…ἑκάστῳ ἡμῶν ἐδόθη ἡ χάρις'

WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'u2bw')

WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), u2bwNewQuote)

abbwBaseQuote = 'ἑνὶ…ἐκάστῳ ἡμῶν ἐδόθη ἡ χάρις'

abbwNewQuote = 'ἑνὶ…ἑκάστῳ ἡμῶν ἐδόθη ἡ χάρις'

WebUI.scrollToPosition(0, 0)

WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_SearchCloseX'))

WebUI.delay(1)

WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'abbw')

WebUI.setText(findTestObject('Object Repository/Page_tCC translationNotes/text_OrigQuote_SearchId'), abbwNewQuote)

// Rerun the validation
initSize = vSize

(vSize,newContent) = runValidation(initSize)

println('newContent:' + newContent)

// Read the file into field lists
prioritys = []

chapters = []

verses = []

lines = []

ids = []

detailss = []

poss = []

excerpts = []

messages = []

locations = []
	
new File(myFile).splitEachLine(',', { def fields ->
            prioritys.add(fields[0])

            chapters.add(fields[1])

            verses.add(fields[2])

            lines.add(fields[3])

            ids.add(fields[4])

            detailss.add(fields[5])

            poss.add(fields[6])

            excerpts.add(fields[7])

            messages.add(fields[8])

            locations.add(fields[9])
        })

// Test to see if there are more rows than just the header/labels row
if (prioritys.size() > 1) {
	println('ERROR: Validation errors remain in csv file')
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because validation errors remain in csv file.')
	println(newContent)
} else {
	println('There are no validation errors in the csv file')
}

GlobalVariable.scriptRunning = false
		
WebUI.closeBrowser()

def getValidationFiles(def testFile) {
	// Get list of files in the Download folder
    List files = new File(dirName).list()

    vFiles = []

	// Add the validation files to a list
    files.each({ def file ->
            if (file.contains('Validation-' + testFile)) {
                vFiles.add(file)
            }
        })

    return vFiles
}

def runValidation(initSize) {
	WebUI.click(findTestObject('Page_tCC translationNotes/button_validate'))
	
	// Wait for the validation file to be downloaded
	vSize = initSize
	
	while(vSize <= initSize) {
		
		WebUI.delay(5)
		
		vFiles = getValidationFiles(testFile)
		
		vSize = vFiles.size()
		
	}
	
	// Sort the list
	vFiles = vFiles.toSorted()
	
	// Get the name of the last (newest) file in the list
	myFile = (vFiles[(vFiles.size() - 1)])
	
	println('myFile:' + myFile)
	
	myFile = dirName + '/' + myFile
	
	nFile = new File(myFile)
	
	newContent = nFile.text
	
	return [vSize,newContent]
}


