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

import org.apache.commons.lang3.StringUtils

tnHeader = 'Book	Chapter	Verse	ID	SupportReference	OrigQuote	Occurrence	GLQuote	OccurrenceNote'

twlHeader = 'Reference	ID	Tags	OrigWords	Occurrence	TWLink'

baseDir = '/Users/cckozie/git/Katalon_tC_Create/Data Files'

List files = new File(baseDir).list()

testFiles = []

files.each({ def file ->
		if (!file.contains('SAVE') && !file.contains('copy') && (file.substring(file.length() - 3) == 'tsv')) {
			testFiles.add(file)
		}
	})

testFiles.each { file ->
//for (file in test)
	
	myFile = baseDir + '/' + file
	
	tabs = 8
	
	if (file.contains('twl')) {
		tabs = 5
	}
	
	println('\nTesting ' + file + ', should have ' + tabs + ' tabs.')
	
	rows = 0
	
	errCount = 0
	
	row = 1
	
	new File(myFile).eachLine { line ->
			
		count = StringUtils.countMatches(line, '\t')
		
		if (row == 1) {
			line = line.trim()
			if (tabs == 8 && line != tnHeader || tabs == 5 && line != twlHeader) {
				println('Bad or missing header')
				errCount ++
			}
		}
		
		if (count != tabs) {
			count = 'ERROR ' + count
			errCount ++
			line = line.replace('\t', '[TAB]')
			println('Row ' + row + ' has ' + count + ' tabs:' + line)
		}
			
		rows ++
		
		row ++
	}
	println(rows + ' rows processed')
	println(errCount + ' errors were found\n')
}
