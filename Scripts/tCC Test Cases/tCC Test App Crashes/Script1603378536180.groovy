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

// 03/01/21	Modified to use tCC md Open For Edit instead of tCC tA Open For Edit

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], FailureHandling.STOP_ON_FAILURE)

println('>>>>>>>>> Testing for app crash when changing sections visible and invisible. ISSUE 365')

WebUI.click(findTestObject('Page_tCC translationNotes/button_filterOpen'))

WebUI.click(findTestObject('Page_tCC translationNotes/filter_ChapterAll'))

WebUI.click(findTestObject('Page_tCC translationNotes/filterOption_Chapter3'))

if (!(WebUI.waitForElementPresent(findTestObject('Page_tC Create/chip_Repo'), 5, FailureHandling.OPTIONAL))) {
    println('ERROR: Repo chip not found after filtering on chapter 3')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the repo chip was not found after filtering on chapter 3. Assuming app crashed.')
} else {
    println('App did not crash after filtering on chapter 3.')
	WebUI.click(findTestObject('Page_tCC translationNotes/button_filterClose'))

}

WebUI.delay(2)

WebUI.closeBrowser()

resource = ['en_ta', 'translate/', 'bita-humanbehavior/', '01.md']
println('>>>>> resource is ' + resource)

WebUI.callTestCase(findTestCase('tCC Components/tCC md Open For Edit'), [('$username') : '', ('$password') : '', ('resource') : resource], FailureHandling.STOP_ON_FAILURE)

println('>>>>>>>>> Testing for app crash when changing sections visible and invisible. ISSUE 428')

WebUI.click(findTestObject('Page_tCC translationAcademy/button_Sections'))

if (!(WebUI.waitForElementVisible(findTestObject('Page_tCC translationAcademy/text_BentOver'), 3, FailureHandling.OPTIONAL))) {
    println('ERROR: tA target text not found after sections were opened')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tA target text was not found after sections were opened. Assuming app crashed.')
} else {
    println('App did not crash after Sections was clicked.')
}

WebUI.delay(2)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], FailureHandling.STOP_ON_FAILURE)

println('>>>>>>>>> Testing for app crash after choosing a file from the drawer. ISSUE 428')

CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseFile'('en_tn_55-1TI.tsv')

if (!(WebUI.waitForElementVisible(findTestObject('Page_tCC translationNotes/header_frontintro_Parmed', [('text') : '1TI front:intro']), 
    5, FailureHandling.OPTIONAL))) {
    println('ERROR: tN source text not found after choosing a file from the drawer')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tN source text was not found after choosing a file from the drawer. Assuming app crashed.')
} else {
    println('App did not crash after choosing a file from the drawer.')
}

WebUI.delay(2)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], FailureHandling.STOP_ON_FAILURE)

println('>>>>>>>>> Testing for app crash when searching in tN. ISSUE 440')

WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), 'leaders')

if (!(WebUI.waitForElementVisible(findTestObject('Page_tCC translationNotes/header_frontintro_Parmed', [('text') : 'TIT front:intro']), 
    5, FailureHandling.OPTIONAL))) {
    println('ERROR: tN source text not found after entering search value')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tN source text was not found after entering search value. Assuming app crashed.')
} else {
    println('App did not crash after search in tN.')
}

WebUI.delay(2)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : 'en_tn_08-RUT.tsv'], FailureHandling.STOP_ON_FAILURE)

if (!WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/button_validator_Message_Close'), 1, FailureHandling.OPTIONAL)) {

	println('>>>>>>>>> Testing for app crash after paging forward twice. ISSUE 463')
	
	WebUI.click(findTestObject('Page_tCC translationNotes/button_NextPage'))
	
	WebUI.delay(2)
	
	WebUI.click(findTestObject('Page_tCC translationNotes/button_NextPage'))
	
	if (!(WebUI.waitForElementPresent(findTestObject('Page_tC Create/chip_Repo'), 5, FailureHandling.OPTIONAL))) {
	    println('ERROR: Repo chip is not present after paging forward twice')
	
	    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the repo chip is not present after paging forward twice. Assuming app crashed.')
	} else {
	    println('App did not crash after paging forward twice.')
	}
	
	WebUI.delay(2)
} else {
	msg = '>>>>>>>>> Testing for app crash after paging forward twice, ISSUE 463, was bypassed because of on-open validation errors'
	println(msg)
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)
}

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : 'en_tn_55-1TI.tsv'], 
    FailureHandling.STOP_ON_FAILURE)

println('>>>>>>>>> Testing for app crash after when adding data to deleted row. ISSUE 639')

IDs = ['e3ce', 'sshf', 'x93f', 'o6j4', 'z4ec']

columns = ['Book', 'ID']

CustomKeywords.'unfoldingWord_Keywords.ManageTNColumns.toggleColumn'(columns)

WebUI.click(findTestObject('Page_tCC translationNotes/button_Search'))

set = false

for (def id : IDs) {
    WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), id)

    book = WebUI.getText(findTestObject('Page_tCC translationNotes/text_Book_SearchId'))

    println(book)

    println(book.length())

    if (book.length() < 2) {
        WebUI.setText(findTestObject('Page_tCC translationNotes/text_Book_SearchId'), '1TI')

        WebUI.clickOffset(findTestObject('Page_tCC translationNotes/text_Book_SearchId'), 0, 100)

        set = true
    }
    
    if (set) {
		
        break
		
    } else {
		
        WebUI.setText(findTestObject('Page_tCC translationNotes/input_Search'), '')

        WebUI.sendKeys(findTestObject('Page_tCC translationNotes/input_Search'), Keys.chord(Keys.BACK_SPACE, Keys.BACK_SPACE, 
                Keys.BACK_SPACE, Keys.BACK_SPACE))
    }
}

if (!set) {
	println('ERROR: Failed to find any deleted row')
	
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Could not execute test because no deleted row could be found.')
}

WebUI.delay(2)

if (!(WebUI.waitForElementPresent(findTestObject('Page_tC Create/chip_Repo'), 5, FailureHandling.OPTIONAL))) {
    println('ERROR: Repo chip is not present after setting book on a deleted row')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the repo chip is not present after setting book on a deleted row.')
} else {
    println('App did not crash after setting the book on a deleted row.')
}

WebUI.delay(2)

WebUI.closeBrowser()

println('>>>>>>>>> Testing for app crash after when loading ru_tq. ISSUE 914')

resource = ['en_tq', 'tit/', '01/', '01.md']
println('>>>>> resource is ' + resource)

WebUI.callTestCase(findTestCase('tCC Components/tCC md Open For Edit'), [('$username') : '', ('$password') : '', ('organization') : 'test_org', ('language') : 'ru', ('resource') : resource], FailureHandling.STOP_ON_FAILURE)

if (!(WebUI.waitForElementPresent(findTestObject('Page_tC Create/chip_Repo'), 5, FailureHandling.OPTIONAL))) {
	println('ERROR: Repo chip is not present after selecting a Russian tQ file.')

	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the repo chip is not present after selecting a Russian tQ file. (Issue 914)')
} else {
	println('App did not crash after selecting a Russian tQ file.')
}

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

