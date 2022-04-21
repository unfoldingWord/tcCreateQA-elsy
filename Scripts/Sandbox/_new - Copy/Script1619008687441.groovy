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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import java.awt.datatransfer.Clipboard as Clipboard
import java.awt.datatransfer.Transferable as Transferable
import java.awt.datatransfer.DataFlavor as DataFlavor
import java.awt.Toolkit as Toolkit

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory

testRows = ['00', '01', '25', '26', '50', '51', '75', '76']

// Navigate to dcs repo
dcsRepo = 'https://qa.door43.org/translate_test/en_tn/src/branch/tc01-tc-create-1/en_tn_43-LUK.tsv'

WebUI.openBrowser(dcsRepo)

if (WebUI.waitForElementPresent(findTestObject('Page_Git Repo/headerRow_Book'), 15)) {

	WebUI.delay(2)
	
	errorCount = 0 
	
	table = WebUI.getText(findTestObject('Object Repository/Page_Git Repo/table_GitRepo'))
	
	row = 0
	
	table.splitEachLine(' ', { def fields ->
		book = fields[0]
		chapter = fields[1]
		verse = fields[2]
		id = fields[3]
		sRef = fields[4]
		String sRow = row
		if (sRow.length() < 2) {
			sRow = '0' + sRow
		}
		l2 = sRow.substring(sRow.length()-2, sRow.length())
		if (testRows.contains(l2) ) {
			if (fields[4].indexOf(fields[3]) < 0) {
				println('ERROR: on row ' + sRow + ', ' + fields[3] + ' not found in ' + fields[4])
				println('The ID is [' + fields[3] + '] and the SupportReference is [' + fields[4] + '].\n')
				errorCount ++
			} else {
				println('Found match on row ' + sRow)
			}
		}
		row ++
		
	})
	
	println(errorCount + ' errors were detected.')
}
