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
WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

page = 'Page_tCC translationNotes/'

//def columns = new String[10]
columns = []
columns = ['columns_RowHeader', 'columns_Book', 'columns_Chapter', 'columns_Verse', 'columns_ID', 'columns_SupportReference'
    , 'columns_OrigQuote', 'columns_Occurrence', 'columns_GLQuote', 'columns_OccurrenceNote']

//def elements = new String[10][3]
elements = [][]

elements = [['text_RowHeader_TIT frontintro', 'text_RowHeader_TIT 1intro', 'text_RowHeader_Titus 11' //
    ], ['label_Book', 'text_Book_TIT' //
    ], ['label_Chapter', 'text_Chapter_front', 'text_Chapter_1' //
    ], ['label_Verse', 'text_Verse_intro', 'text_Verse_1' //
    ], ['label_ID', 'text_ID_m2jl', 'text_ID_c7me' //
    ], ['label_SupportReference', 'text_SupRef_figs-abstractnouns' //
    ], ['label_OrigQuote', 'text_OrigQuote1', 'text_OrigQuote2' //
    ], ['label_Occurrence', 'text_Occurrence_0', 'text_Occurrence_1' //
    ], ['label_GLQuote', 'text_GLQuote_for the faith', 'text_GLQuote_knowledge' //
    ], ['label_OccurrenceNote', 'text_OccurrenceNote_Who wrote the Book of Titus']]

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

println('Setting all columns OFF')

KeywordUtil.logInfo('Setting all columns OFF')

for (def c : (0..columns.size() - 1)) {
    col = (page + (columns[c]))

    if (WebUI.verifyElementChecked(findTestObject(col), 1, FailureHandling.OPTIONAL) == true) {
        WebUI.click(findTestObject(col))

        WebUI.verifyElementNotChecked(findTestObject(col), 1, FailureHandling.CONTINUE_ON_FAILURE)
    }
}

//One by one set each column on and test for expected fields
for (def c : (0..columns.size() - 1)) {
    col = (page + (columns[c]))

    WebUI.click(findTestObject(col))

    WebUI.verifyElementChecked(findTestObject(col), 1, FailureHandling.CONTINUE_ON_FAILURE)

    println(('Testing for ' + col) + ' elements visible.')

    KeywordUtil.logInfo(('Testing for ' + col) + ' elements visible.')

    for (def e : (0..(elements[c]).size() - 1)) {
        ele = (page + ((elements[c])[e]))

        println('looking for ' + ele)

        KeywordUtil.logInfo('looking for ' + ele)

        WebUI.verifyElementVisible(findTestObject(ele), FailureHandling.CONTINUE_ON_FAILURE)
    }
    
    println(('Clicking ' + col) + ' OFF, then Testing for elements not visible.')

    KeywordUtil.logInfo(('Clicking ' + col) + ' OFF, then Testing for elements not visible.')

    WebUI.click(findTestObject(col))

    WebUI.verifyElementNotChecked(findTestObject(col), 1, FailureHandling.CONTINUE_ON_FAILURE)

    for (def e : (0..(elements[c]).size() - 1)) {
        ele = (page + ((elements[c])[e]))

        println('looking for ' + ele)

        KeywordUtil.logInfo('looking for ' + ele)

        if (!(WebUI.verifyElementNotPresent(findTestObject(ele), 1, FailureHandling.CONTINUE_ON_FAILURE))) {
            WebUI.delay(5)

            if (WebUI.verifyElementNotVisible(findTestObject(ele), FailureHandling.CONTINUE_ON_FAILURE)) {
                println(ele + ' is present but not visible.')
            } else {
				CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because ' + ele + ' is both present and visible but should not be.')
                println(ERROR: ele + ' is both present and visible but should not be.')
            }
        }
    }
}

KeywordUtil.logInfo('Testing Expand all Scripture option.')

col = (page + (columns[0]))

WebUI.click(findTestObject(col))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

state = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('on')

//WebUI.waitForElementNotVisible(findTestObject('Page_tC Create/button_DrawerClose'), 5)
WebUI.waitForElementVisible(findTestObject('Page_tC Create/button_DrawerOpen'), 5)

ele = 'Page_tCC translationNotes/text_unfoldingWordGreekNewTestament'

if (WebUI.verifyElementPresent(findTestObject(ele), 5, FailureHandling.CONTINUE_ON_FAILURE)) {
	println('scripture is present as expected')
} else {
    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because scripture pane is not present.')
	println('ERROR: scripture pane is not present.')
}

state = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('off')

if (WebUI.verifyElementNotPresent(findTestObject(ele), 5, FailureHandling.CONTINUE_ON_FAILURE)) {
	println('Scripture is not present as expected')
} else {
	println('ERROR: scripture pane is present when it should not be.')
    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because scripture pane is present when it should not be.')
	println('ERROR: scripture pane is present when it should not be.')
}

state = CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'('on')

WebUI.waitForElementNotVisible(findTestObject('Page_tC Create/button_DrawerClose'), 5)

ele = 'Page_tCC translationNotes/text_unfoldingWordGreekNewTestament'

if (WebUI.verifyElementPresent(findTestObject(ele), 5, FailureHandling.CONTINUE_ON_FAILURE)) {
	println('scripture is present as expected')
} else {
    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because scripture pane is not present.')
	println('ERROR: scripture pane is not present.')
}

WebUI.closeBrowser()

