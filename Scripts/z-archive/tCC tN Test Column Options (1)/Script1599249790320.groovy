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

// 10/19/20 Modified to include test for expand all scripture test
// 10/21/20 Modified to include test for blue bar visible on file open
WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

try {
	WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.OPTIONAL)
	WebUI.delay(2)
	WebUI.click(findTestObject('Page_tC Create/button_DrawerClose'))
} catch (Exception e) {
	println('ERROR: Blue bar and repo chips are not visible on file open')
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because blue bar and repo chips are not visible on file open.')
}

page = 'Page_tCC translationNotes/'

//def columns = new String[10]
columns = []

columns = ['Page_tCC translationNotes/columns_RowHeader', 'Page_tCC translationNotes/columns_Book', 
	'Page_tCC translationNotes/columns_Chapter', 'Page_tCC translationNotes/columns_Verse', 'Page_tCC translationNotes/columns_ID', 
	'Page_tCC translationNotes/columns_SupportReference', 'Page_tCC translationNotes/columns_OrigQuote', 
	'Page_tCC translationNotes/columns_Occurrence', 'Page_tCC translationNotes/columns_GLQuote', 
	'Page_tCC translationNotes/columns_OccurrenceNote']

//def elements = new String[10][3]
elements = ([][[]])

elements = [['Page_tCC translationNotes/text_RowHeader_TIT frontintro', 'Page_tCC translationNotes/text_RowHeader_TIT 1intro', 
	'Page_tCC translationNotes/text_RowHeader_Titus 11'], // 
	['Page_tCC translationNotes/label_Book', 'Page_tCC translationNotes/text_Book_TIT'], //
	['Page_tCC translationNotes/label_Chapter', 'Page_tCC translationNotes/text_Chapter_front', 'Page_tCC translationNotes/text_Chapter_1'], //
	['Page_tCC translationNotes/label_Verse', 'Page_tCC translationNotes/text_Verse_intro', 'Page_tCC translationNotes/text_Verse_1'], //
	['Page_tCC translationNotes/label_ID', 'Page_tCC translationNotes/text_ID_m2jl', 'Page_tCC translationNotes/text_ID_c7me'], //
	['Page_tCC translationNotes/label_SupportReference', 'Page_tCC translationNotes/text_SupRef_figs-abstractnouns'], // 
	['Page_tCC translationNotes/label_OrigQuote', 'Page_tCC translationNotes/text_OrigQuote1', 'Page_tCC translationNotes/text_OrigQuote2'], //
	['Page_tCC translationNotes/label_Occurrence', 'Page_tCC translationNotes/text_Occurrence_0', 'Page_tCC translationNotes/text_Occurrence_1'], //
	['Page_tCC translationNotes/label_GLQuote', 'Page_tCC translationNotes/text_GLQuote_for the faith', 'Page_tCC translationNotes/text_GLQuote_knowledge'], //
	['Page_tCC translationNotes/label_OccurrenceNote', 'Page_tCC translationNotes/text_OccurrenceNote_Who wrote the Book of Titus']]

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

println('Setting all columns OFF')

KeywordUtil.logInfo('Setting all columns OFF')

for (def c : (0..columns.size() - 1)) {
//    col = (page + (columns[c]))
    col = columns[c]
    WebUI.uncheck(findTestObject(col), FailureHandling.OPTIONAL)
}

//One by one set each column on and test for expected fields
for (def c : (0..columns.size() - 1)) {
//    col = (page + (columns[c]))
	col = (columns[c])
	
    WebUI.click(findTestObject(col))

    WebUI.verifyElementChecked(findTestObject(col), 1, FailureHandling.CONTINUE_ON_FAILURE)

    println(('Testing for ' + col) + ' elements visible.')

    KeywordUtil.logInfo(('Testing for ' + col) + ' elements visible.')

    for (def e : (0..(elements[c]).size() - 1)) {
//        ele = (page + ((elements[c])[e]))
		ele = ((elements[c])[e])
		
        println('looking for ' + ele)

        KeywordUtil.logInfo('looking for ' + ele)

        if (!(WebUI.waitForElementVisible(findTestObject(ele), 1, FailureHandling.OPTIONAL))) {
            println(ele + ' was not found')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because ' + ele) + ' was not found.')
        }
    }
    
    println(('Clicking ' + col) + ' OFF, then Testing for elements not visible.')

    KeywordUtil.logInfo(('Clicking ' + col) + ' OFF, then Testing for elements not visible.')

    WebUI.click(findTestObject(col))

    WebUI.verifyElementNotChecked(findTestObject(col), 1, FailureHandling.CONTINUE_ON_FAILURE)

    for (def e : (0..(elements[c]).size() - 1)) {
        ele = (page + ((elements[c])[e]))
		ele = ((elements[c])[e])
		
        println('looking for ' + ele)

        KeywordUtil.logInfo('looking for ' + ele)

        if (!(WebUI.verifyElementNotPresent(findTestObject(ele), 1, FailureHandling.CONTINUE_ON_FAILURE))) {
            WebUI.delay(5)

            if (WebUI.verifyElementNotVisible(findTestObject(ele), FailureHandling.CONTINUE_ON_FAILURE)) {
                println(ele + ' is present but not visible.')
            } else {
                CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because ' + ele) + ' is both present and visible but should not be.')

                println()
            }
        }
    }
}

KeywordUtil.logInfo('Testing Expand all Scripture option.')

//col = (page + (columns[0]))
col = columns[0]

WebUI.click(findTestObject(col))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

state = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('on')

ele = 'Page_tCC translationNotes/text_unfoldingWordGreekNewTestament'

if (WebUI.verifyElementPresent(findTestObject(ele), 1, FailureHandling.CONTINUE_ON_FAILURE)) {
    println('scripture is present as expected')
} else {
    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because scripture pane is not present.')

    println('ERROR: scripture pane is not present.')
}

state = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('off')

if (WebUI.verifyElementNotPresent(findTestObject(ele), 1, FailureHandling.CONTINUE_ON_FAILURE)) {
    println('Scripture is not present as expected')
} else {
    println('ERROR: scripture pane is present when it should not be.')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because scripture pane is present when it should not be.')
}

state = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('on')

ele = 'Page_tCC translationNotes/text_unfoldingWordGreekNewTestament'

if (WebUI.verifyElementPresent(findTestObject(ele), 1, FailureHandling.CONTINUE_ON_FAILURE)) {
    println('scripture is present as expected')
} else {
    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because scripture pane is not present.')

    println('ERROR: scripture pane is not present.')
}

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

