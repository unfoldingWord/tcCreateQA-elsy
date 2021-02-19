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
CustomKeywords.'com.tccreate.keywords.WriteToFile.writeTofilename'('tN-validate-450')

CustomKeywords.'com.helper.login.TimeDate.getTimeDate'()

dirName = (('/Users/' + GlobalVariable.pcUser) + '/Downloads')

baseDir = (GlobalVariable.projectPath + '/Data Files/')

testFiles = [ 'en_tn_57-TIT.tsv']//'en_tn_50-EPH.tsv', 'en_tn_42-MRK.tsv', 'en_tn_43-LUK.tsv', 'en_tn_45-ACT.tsv', 'en_tn_46-ROM.tsv'
	//, 'en_tn_52-COL.tsv', 'en_tn_15-EZR.tsv', 'en_tn_56-2TI.tsv', 'en_tn_41-MAT.tsv']

// expectedFails holds the list of rows (base 0) in the baseline csv files that currently are expected to fail when tested
//expectedFails = [('en_tn_57-TIT.tsv') : [1, 2]]//, ('en_tn_43-LUK.tsv') : [0], ('en_tn_46-ROM.tsv') : [0], ('en_tn_15-EZR.tsv') : [0, 1], ('en_tn_56-2TI.tsv') : [0], ('en_tn_41-MAT.tsv') : [0, 1]]

start = 0

end = (testFiles.size() - 1)

end = start //comment out this line when checking more than one file

errorCount = 0 

passCount = 0

testCount = 0

expectedCount = 0
defaultLevel = 'High'
first = true

timings = []

for (def fileNum : (start..end)) {
	errorFlag = false

	testFile = (testFiles[fileNum])

	println('>>>>>>>> Running test on ' + testFile)

	baseFile = (((baseDir + 'Validation-') + testFile) + '_base.csv')
	baseLines = removeExcerpts(baseFile) // Remove the excertps column because it sometimes changes even though the error didn't change
	baseLines.each({ def line ->
		println(line)
	})
	/*bFile = new File(baseFile)

	baseContent = bFile.text

	println('baseContent:' + baseContent)*/

	// Get the count of how many validation files already exist for the file being tested
	println('testFile:' + testFile)

	vFiles = getValidationFiles(testFile)

	initSize = vFiles.size()

//Load Project in tN
CustomKeywords.'com.helper.login.loginhelper.logintoapp'()

CustomKeywords.'com.tccreate.keywords.selectOrg.organization'('')

CustomKeywords.'com.tccreate.keywords.selectOrg.resource'('unfoldingWord® Translation Notes')
CustomKeywords.'com.tccreate.keywords.selectOrg.language'("")
WebUI.click(findTestObject('Page_tC Create/Add new resource objects/span_en_tn_57-TIT.tsv'))

// Set validation level to low
retCode = CustomKeywords.'com.tccreate.keywords.ExpandAllScriptureToggle.chooseValidationLevel'('lo')
// Run the validation
(vSize, newContent) = runValidation(initSize, testFile)

if (newContent == '') {  // The validator did not find any errors. Continue with next file.
	msg = ('The validator found no errors in ' + testFile + '.' )
	println(msg)
	//CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)
	//continue
}


if (fileNum == 0) {
	if (newContent == ''){

System.out.println("Priority -450 and 658 are fixed and are nt showing in the file")
}}
WebUI.closeBrowser()
}
println('\n' + testCount + ' tests were run.')

println(passCount + ' tests passed.')

println(((errorCount + ' tests failed; ') + expectedCount) + ' expected.')
GlobalVariable.testCount = testCount

GlobalVariable.passCount = passCount

GlobalVariable.errorCount = errorCount

GlobalVariable.expectedErrors = expectedCount

GlobalVariable.scriptRunning = false
WebUI.closeBrowser( // Get list of files in the Download folder
// Add the validation files to a list
)
def removeExcerpts(vFile) {
	lines = []
	new File(vFile).splitEachLine('\\12', { def fields ->
		line = fields[0]
		int [] commas = line.findIndexValues {
			it == ','
		}
		line = line.substring(0,commas[6]) + line.substring(commas[7])
		lines.add(line)
	})
	return lines
}
def getValidationFiles(def testFile) {
	println('dirName1:' + dirName)

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
	WebUI.scrollToPosition(0, 0)

    WebUI.waitForElementClickable(findTestObject('tN objects/button_validate'), 30)

    WebUI.click(findTestObject('tN objects/button_validate'))
	
	WebUI.waitForElementPresent(findTestObject('Object Repository/tN objects/alert_ValidationRunning'), 5)
			
    vSize = initSize
	
	myFile = ''
	
	newContent = ''
	
	noFile = false
	
    while (vSize <= initSize && !noFile) { // && !WebUI.verifyAlertNotPresent(1)) {

        vFiles = getValidationFiles(testFile)

        vSize = vFiles.size()
		
		if (!WebUI.verifyAlertNotPresent(5, FailureHandling.OPTIONAL)) {
			
			noFile = true			
			
		}
    }
	
	if (noFile) {
		
		WebUI.acceptAlert(FailureHandling.OPTIONAL) // It seems the verifyAlertNotPresent auto-accepts it when found????
		
	} else {
    
	    vFiles = vFiles.toSorted()
	
	    myFile = (vFiles[(vFiles.size() - 1)])
	
	    println('myFile:' + myFile)
	
	    println('dirName2:' + dirName)
	
	    myFile = ((dirName + '/') + myFile)
		
		newContent = removeExcerpts(myFile)
		
	}
	
    return [vSize, newContent, myFile]
}

def testOutput(def baseLines, def myFile) {
	newLines = []

	new File(myFile).eachLine({ def line ->
			newLines.add(line)
		})

	row = 0

	baseLines.each({ def line ->
			println('testing line-' + line)

			testCount++

			if (line in newLines) {
				errorFlag = true

				errorCount++

				prefix = test4Expected(testFile, row)

				println(((((prefix + 'ERROR: Validation file line [') + line) + ']  in ') + testFile) + ' still exists after being fixed')

				CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(((((prefix + 'Test failed because validation file line [') +
					line) + ']  in ') + testFile) + ' still exists after being fixed.')
			} else {
				passCount++
			}
			
			row++
		})
}

