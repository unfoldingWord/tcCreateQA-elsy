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
import javax.swing.JOptionPane;

columnsBase = 'Page_tCC translationNotes/columns_'

columns = ['RowHeader', 'Book',
	'Chapter', 'Verse', 'ID',
	'SupportReference', 'OrigQuote',
	'Occurrence', 'GLQuote',
	'OccurrenceNote']

WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

if (1 == 2) {
	displayStates(columnsBase, columns)
	
	for (def c : (0..columns.size() - 1)) {
		col = (columnsBase + columns[c])
		WebUI.check(findTestObject(col), FailureHandling.OPTIONAL)
	}
	
	displayStates(columnsBase, columns)
}

done = false

while (!done) {
	
	displayStates(columnsBase, columns)
	
}

def displayStates(columnsBase, columns) {
	msg = ''
	for (def c : (0..columns.size() - 1)) {
		col = (columnsBase + columns[c])
		state = WebUI.verifyElementChecked(findTestObject(col), 1, FailureHandling.OPTIONAL)
		if (state) {
			msg += columns[c] + ' is checked\n'
		} else {
			msg += columns[c] + ' is NOT checked\n'
		}
	}
	
	JOptionPane.showMessageDialog(null,
		msg,
		"Update checkboxes and click OK",
		JOptionPane.PLAIN_MESSAGE);
}