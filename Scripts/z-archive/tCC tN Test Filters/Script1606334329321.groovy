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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.Keys as Keys
import groovy.transform.Field as Field
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], 
    FailureHandling.STOP_ON_FAILURE)

chpts = ['3','All','1','2','front']

for (chpt in chpts) {
	filterByChapter(chpt)
}

WebUI.delay(2)

WebUI.closeBrowser()

def filterByChapter(chpt) {
	if (chpt != 'All') {
		intro = 'TIT ' + chpt + ':intro'
	} else {
		intro = 'TIT front:intro'
	}
	
	badge = 'Chapter - ' + chpt
	
	WebUI.click(findTestObject('Page_tCC translationNotes/button_filterOpen'))
	
	WebUI.delay(1)
		
	WebUI.click(findTestObject('Page_tCC translationNotes/list_chapter'))
	
	WebUI.click(findTestObject('Page_tCC translationNotes/filter_Chapter_Parmed', [('chapter') : chpt]))
	
	if (!WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/header_chapterIntro_Parmed',[('introText') : intro]), 1)) {
		println('ERROR: Intro header ' + intro + ' not found')
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the intro header ' + intro + ' was not found.')
	} else {
		println('Intro header ' + intro + ' was found')
	}
	
	WebUI.click(findTestObject('Page_tCC translationNotes/button_filterClose'))
	
	WebUI.scrollToPosition(0, 0)
	
	if (chpt != 'All') {
		WebUI.clickOffset(findTestObject('Page_tCC translationNotes/badge_Filter_Parmed', [('badgeText') : badge]), 0, 0)
	}

	intro = 'TIT front:intro'
	
	if (!WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/header_chapterIntro_Parmed',[('introText') : intro]), 1)) {
		println('ERROR: Intro header ' + intro + ' not found after clicking on badge')
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the intro header ' + intro + ' was not found after clicking on badge.')
	} else {
		println('Intro header ' + intro + ' was found')
	}
	
	WebUI.delay(1)
		
}
