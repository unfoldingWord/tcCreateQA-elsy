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

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.io.File as File

WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], 
    FailureHandling.STOP_ON_FAILURE)

testFiles = []

allBooks = '/Users/cckozie/Documents/Sikuli/Files/Bible_Books.csv'
ntBooks = '/Users/cckozie/Documents/Sikuli/Files/NT_Books.csv'

myBooks = allBooks

new File(myBooks).splitEachLine(',', { def fields ->
        bookNum = (fields[0])

        if (bookNum.length() < 2) {
            bookNum = ('0' + bookNum)
        }
        
        bookAbrv = (fields[1])

        bookName = (((('en_tn_' + bookNum) + '-') + bookAbrv) + '.tsv')

        testFiles.add(bookName)
    })

dirName = '/Users/cckozie/Downloads'

myPrioritys = []

errorRows = []

now = new Date()

fName = 'Unique_Validation_Errors-' + now.format('MMddyyhhmmss') + '.csv'
oFile = new File('/Users/cckozie/Katalon Studio/Files/' + fName)

//for (def testFile in testFiles) {
testFiles.each({ def testFile ->
    println('Opening ' + testFile)
	
	vFiles = getValidationFiles(testFile)
	
	initSize = vFiles.size()
	
    CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseFile'(testFile)

    WebUI.delay(2)
	
    (vSize, newContent, myFile) = runValidation(initSize, testFile)

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
	
	newLines = []
	
	new File(myFile).eachLine({ def line ->
		newLines.add(line)
	})

	for (def row : (0..prioritys.size()-1) ) {
		if (!myPrioritys.contains(prioritys[row])) {
			myPrioritys.add(prioritys[row])
			errorRows.add(newLines[row])
			oFile.append(testFile + ',' + newLines[row] + '\n')
		}
	}
	
	println(myPrioritys)
	
	println(errorRows)	

})

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()


def getValidationFiles(def testFile) {
	List files = new File(dirName).list()

	vFiles = []

	files.each({ def file ->
			if (file.contains('Validation-' + testFile)) {
				vFiles.add(file)
			}
		})

	return vFiles
}


def runValidation(def initSize, def testFile) {
	//	WebUI.scrollToPosition(0, 0)
		
		WebUI.waitForElementClickable(findTestObject('Page_tCC translationNotes/button_validate'),30)
		
		WebUI.click(findTestObject('Page_tCC translationNotes/button_validate'))
	
		vSize = initSize
	
		while (vSize <= initSize) {
			WebUI.delay(5)
	
			vFiles = getValidationFiles(testFile)
	
			vSize = vFiles.size()
		}
		
		vFiles = vFiles.toSorted()
	
		myFile = (vFiles[(vFiles.size() - 1)])
	
		println('myFile:' + myFile)
	
		myFile = ((dirName + '/') + myFile)
	
		nFile = new File(myFile)
	
		newContent = nFile.text
	
		return [vSize, newContent, myFile]
	}
	