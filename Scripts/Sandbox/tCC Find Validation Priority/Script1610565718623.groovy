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

import org.junit.After

import java.io.File as File

// FIND INSTANCES OF SPECIFIC VALIDATION ERRORS
// Will search for one or many errors, listed in myPriorities
// Will stop after finding the first instance of each of the errors specified, 
//		or will find all instances of the errors in all books specified (determined by state of findAll)
// Will search OT, NT, ALL, or single book (determined by myBooks for more than one, 
//		or set fileExists to true to test only an existing validation file in the Downloads folder

fileExists = false //SET TO TRUE TO TEST EXISTING VALIDATION FILE AND SET FILE NAME BELOW AS myFile

findAll = false //FIND EVERY INSANCE OF THE ERROR. IF FALSE, STOP SEARCHING AFTER THE FIRST INSTANCE OF EACH CODE IN myPrioritys

if (!fileExists) {
	WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], 
	    FailureHandling.STOP_ON_FAILURE)
	
	CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseValidationLevel'('Low')
	
} else {
	myFile = '/Users/cckozie/Downloads/Validation-en_tn_67-REV.tsv-2021-01-13T23_21_09.121Z.csv' //TEST EXISTING VALIDATION FILE
}

myPrioritys = ['581']

testFiles = []

allBooks = '/Users/cckozie/Documents/Sikuli/Files/Bible_Books.csv'
ntBooks = '/Users/cckozie/Documents/Sikuli/Files/NT_Books.csv'
otBooks = '/Users/cckozie/Documents/Sikuli/Files/OT_Books.csv'
epistleBooks = '/Users/cckozie/Documents/Sikuli/Files/Epistle_Books.csv'

myBooks = epistleBooks

if (!fileExists) {

	new File(myBooks).splitEachLine(',', { def fields ->
	        bookNum = (fields[0])
	
	        if (bookNum.length() < 2) {
	            bookNum = ('0' + bookNum)
	        }
	        
	        bookAbrv = (fields[1])
	
	        bookName = (((('en_tn_' + bookNum) + '-') + bookAbrv) + '.tsv')
	
	        testFiles.add(bookName)
	    })
} else {
	testFiles.add(myFile)
}

dirName = '/Users/cckozie/Downloads'

pFound = []

output = []

//for (def testFile in testFiles) {
testFiles.each({ def testFile ->
	if (!fileExists) {
	    println('Opening ' + testFile)
		
		vFiles = getValidationFiles(testFile)
		
		initSize = vFiles.size()
		
	    CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseFile'(testFile)
	
	    WebUI.delay(2)
		
	    (vSize, newContent, myFile) = runValidation(initSize, testFile)
	}

	// Read the file into field lists
	prioritys = []

	IDs = []
	
	new File(myFile).splitEachLine(',', { def fields ->
			prioritys.add(fields[0])
			IDs.add(fields[4])
		})
	println(IDs)
	
	newLines = []
	
	new File(myFile).eachLine({ def line ->
		newLines.add(line)
	})

	for (def row : (0..prioritys.size()-1) ) {
		for (def p : (0..myPrioritys.size()-1)) {
//			println('testing line ' + row + ' > ' + myPrioritys[p] + ':' + prioritys[row])			
			if (myPrioritys[p] == prioritys[row]) {
				if (!pFound.contains(p) || findAll) {
					output.add('Priority ' + myPrioritys[p] + ' was found in line ' + (row+1) + ' ' + IDs[row] + ' of ' + testFile)
					pFound.add(p)
	//				println('p is ' + p)
	//				println('>>>>>>>>>>>>>>> found it!')
	//				println('pFound:' + pFound)
	//				println('output:' + output)
				}
			}
		}
	}
	for (def f : (0..myPrioritys.size()-1)) {
		if (pFound.contains(f)) {
			println('Found:' + myPrioritys[f])
//			println('removing ' + myPrioritys[f])
			if (!findAll) {
				myPrioritys.remove(myPrioritys[f])
			}
		}
	}
	
	if (myPrioritys.size() < 1) {
		println('found all priorities')
		printAndClose()
		return false
	}
	
})

printAndClose()

def printAndClose () {
//	println('Priorities found:')
	first = true
	l = 0
	output.each({ def line ->
		if (first) {
			println('\n======================================================')
			first = false
		}
		println(line)
		l ++
		if (l >= output.size()) {
			println('======================================================')
		}
	})
	GlobalVariable.scriptRunning = false
	WebUI.closeBrowser()
}

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
	