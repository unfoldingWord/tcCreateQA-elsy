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

dirName = (('/Users/' + GlobalVariable.pcUser) + '/Downloads')

filesPath = '/Users/' + GlobalVariable.pcUser + '/Katalon Studio/Files/Reference/'

allBooks = filesPath + 'Bible_Books.csv'
ntBooks = filesPath + 'NT_Books.csv'
otBooks = filesPath + 'OT_Books.csv'
someBooks = filesPath + 'Some_Books.csv'
oneBook = filesPath + 'One_Book.csv'
epistleBooks = filesPath + 'Epistle_Books.csv'

myBooks = ntBooks

testFiles = []

tab = '\t'

spaces = '    '

new File(myBooks).splitEachLine(',', { def fields ->
		bookNum = (fields[0])

		if (bookNum.length() < 2) {
			bookNum = ('0' + bookNum)
		}
		
		bookAbrv = (fields[1])

		testFiles.add(bookNum + '-' + bookAbrv)
	})


repoBase = 'https://git.door43.org/Door43-Catalog/en_tn/src/branch/master/'

xPath = '/html/body/div[1]/div[2]/div[2]/div[5]/div/div/table/tbody'

WebUI.openBrowser('')

WebUI.maximizeWindow()

testFiles.each { book ->
	
	file = 'en_tn_' + book + '.tsv'
	
	println('>>>>>>>>>> Processing ' + file + ' <<<<<<<<<<<')
	
	repoFile = repoBase + file
	
	WebUI.navigateToUrl(repoFile)
	
	if (WebUI.waitForElementPresent(findTestObject('Page_Git Repo/headerRow_Book'), 15)) {
	
		WebUI.delay(2)
			
		xPath = '/html/body/div[1]/div[2]/div[2]/div[5]/div/div/table/tbody'
		
		getRowIDs(xPath, file)
		
	} else {
		msg = 'Bypassing ' + file + ' because it is not properly formatted.'
		println(msg)
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)
		
	}
	
}

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()


def getRowIDs(xPath,file) {
	WebDriver driver = DriverFactory.getWebDriver()
	WebElement Table = driver.findElement(By.xpath(xPath))
	List<WebElement> rows = Table.findElements(By.tagName('tr'))
	rows_count = rows.size()
	dupCount = 0
	def ids = [:]
//	for (int row = 0; row < rows_count; row++) {
	r = 0
	rows.each { row -> 
		List<WebElement> columns = rows.get(r).findElements(By.tagName('td'))
		id = columns.get(3).getText()
		if (ids.containsKey(id)) {
			row1 = ids.get(id)
			rowMsg = ('ID ' + id + ' is duplicateed in rows ' + row1 + ' and ' + r + ' in ' + file)
			println('##### ERROR: ' + rowMsg)
//			CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(rowMsg)
			CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because ' + rowMsg)
			dupCount ++
		} else {
			ids.put(id,r)
		}
		r ++
	}
	if (dupCount < 1) { 
		msg = 'No duplicate IDs found in ' + file
		println(msg)
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)
	}
}		
