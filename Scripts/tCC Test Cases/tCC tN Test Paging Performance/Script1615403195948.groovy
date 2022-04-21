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
import groovy.io.FileType as FileType
import org.apache.commons.io.FileUtils as FileUtils
import java.awt.datatransfer.Clipboard as Clipboard
import java.awt.datatransfer.Transferable as Transferable
import java.awt.datatransfer.DataFlavor as DataFlavor
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import org.openqa.selenium.Keys as Keys
import groovy.time.*
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// Load a Matthew tN project and test how long it takes to page with the various rows/page options

rowsList = [10, 25, 50, 100]

timings = []

avgTimes = []

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : GlobalVariable.validateUser, ('$password') : GlobalVariable.validatePassword
	, ('file') : 'en_tn_42-MRK.tsv', ('language') : ''], FailureHandling.STOP_ON_FAILURE)

for (rows in rowsList) {
	
	msg = 'Timings test on ' + rows + ' rows.'
	
	println(msg)
	
	timings.add(msg)

	displayRows = WebUI.getText(findTestObject('Page_tCC translationNotes/text_RowsOnPage'))
	
	WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/list_RowsPerPage'))
	
	WebUI.delay(1)
	
	WebUI.click(findTestObject('Page_tCC translationNotes/option_RowsPerPage_parmned', [('rows') : rows]))
	
	loops = 0

	while (WebUI.getText(findTestObject('Object Repository/Page_tCC translationNotes/text_RowsOnPage')) == displayRows && loops < 30) {
		WebUI.delay(1)
	}
	
	displayRows = WebUI.getText(findTestObject('Page_tCC translationNotes/text_RowsOnPage'))
	
	if (1 == 2) {
	
		println(displayRows)
		
		ofLoc = displayRows.indexOf('of')
		
		println(ofLoc)
		
		rowsLoc = displayRows.indexOf(' ', ofLoc)
		
		lRow = displayRows.substring(rowsLoc+1, displayRows.length())
		
		tRows = displayRows(displayRows.indexOf(' ',ofLoc)+2)
		
		println(tRows)
	}
	
	totalTime = 0
	
	times = []
	
	count = 1
	
	for (def i : (1..count)) {
		
		timeStart = new Date()
		
		WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'))
		
		WebUI.waitForElementClickable(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'), 20)
			
		timeEnd = new Date()
		
		seconds = (timeEnd.getTime() - timeStart.getTime())/1000
		
		println(seconds + ' seconds')
		
		timings.add('Paging ' + rows + ' rows took ' + seconds + ' seconds.')
		
		totalTime += seconds
		
		times.add(seconds)
		
	}
	timings.add(' ')
	println('Max is ' + times.max())
	println('Min is ' + times.min())
	println('Average is ' + totalTime/count)
	avgTimes.add(totalTime/count)
	
}

i = 0

for (rows in rowsList) {
	
	avgTime = ((int) ((avgTimes[i] + 0.005f) * 100)) / 100f
	
	avgRowTime = ((int) ((avgTime/rows + 0.005f) * 100)) / 100f
	
	timing = 'Average time to page ' + rows + ' rows is ' + avgTime + ' seconds, and ' + avgRowTime + ' seconds/row.'
	
	println(timing)
	
	timings.add(timing)
	
	i ++
}

GlobalVariable.timings = timings

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()
