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
import com.kms.katalon.core.configuration.RunConfiguration as RC

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
//myBooks = oneBook

dupsOnly = true 

testFiles = []

new File(myBooks).splitEachLine(',', { def fields ->
		bookAbrv = (fields[1])

		testFiles.add(bookAbrv)
	})

//testFiles = ['RUT']

server = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getServer'()

userTCC = 'tcc001'

userTC = 'tc01'
userEL = 'ElsyLambert'

user = userEL

catalogBase = 'https://' + server + '.door43.org/Door43-Catalog/en_tn/src/branch/master/'

myRepoBase = 'https://' + server + '.door43.org/translate_test/en_tn/src/branch/' + user + '-tc-create-1/'

uWBase = 'https://' + server + '.door43.org/unfoldingWord/en_tn/src/branch/master/'

repoBase = myRepoBase

//xPath = '/html/body/div[1]/div[2]/div[2]/div[5]/div/div/table/tbody'

WebUI.openBrowser('')

WebUI.maximizeWindow()

CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'('Testing files in repo ' + repoBase)


testFiles.each { book ->
	
	file = 'tn_' + book + '.tsv'
	
	println('>>>>>>>>>> Processing ' + file + ' <<<<<<<<<<<')
	
	repoFile = repoBase + file
	
	println('Testing ' + repoFile)
	
	WebUI.navigateToUrl(repoFile)
	
	if (WebUI.verifyElementPresent(findTestObject('Page_Git Repo/text_404_Error'), 1, FailureHandling.OPTIONAL)) {
		if (!dupsOnly) {
			msg = '404 error on ' + repoFile
			println(msg)
			CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)
		}
	} else {
		
		if (WebUI.waitForElementPresent(findTestObject('Page_Git Repo/button_Raw'), 5))
			
			 { //WebUI.delay(2)
				 WebUI.click(findTestObject('Page_Git Repo/button_Raw'))
		
			WebUI.delay(2)
				
	//		xPath = '/html/body/div[1]/div[2]/div[2]/div[5]/div/div/table/tbody'
					
			getRowIDs(file)
			
		} else {
			msg = 'Bypassing ' + file + ' because it is not properly formatted.'
			println(msg)
			CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)
			
		}
	}
}

GlobalVariable.scriptRunning = false

//WebUI.closeBrowser()


def getRowIDs(file) {
	int r = 0
	dupCount = 0
	def ids = [:]
	def refs = []
	def table = WebUI.getText(findTestObject('Object Repository/Page_Git Repo/text_raw'))
	table.splitEachLine(' ', { def fields ->
		refss= fields[0]
		id = fields[1]
		refs.add(refss)
		//verss.add(verse)
	//})
		if (ids.containsKey(id)) {
			row1 = ids.get(id)
			println("row1 is:" + row1)
			rowMsg = ('ID ' + id + ' is duplicated in rows ' + row1 + ', Ref ' + refss[row1] + ', and ' + r + ', Ref ' + refs  + ' in ' + file)
			println('##### ERROR: ' + rowMsg)
			CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because ' + rowMsg)
			dupCount ++
		} else {
			ids.put(id,r)
			//println("ids :"+ ids)
		}
		r ++
	})
	if (dupCount < 1 && !dupsOnly) { 
		msg = 'No duplicate IDs found in ' + file
		println(msg)
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)

	}
		
}	
//WebUI.closeBrowser()	
