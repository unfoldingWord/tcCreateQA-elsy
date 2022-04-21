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

// 02/10/21	Modified to use the HotKeys custom keyword for all copy and paste functions
// 03/02/21 Modified to add new scrolling because of unexpected changes in v1.2.0-rc2

String highlighted = 'rgba(255, 255, 0, 1)'

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], FailureHandling.STOP_ON_FAILURE)

system = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getOperatingSystem'()

myBrowser = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getBrowserAndVersion'()

(width, height) = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getScreenResolution'()

objectPage = 'Page_tCC translationNotes/'

//Define and load the original language text array for ID xyz8'
origQuote = []

origQuote = ['Page_tCC translationNotes/text_OrigQuote-en-xyz8-Pavlos-1', 'Page_tCC translationNotes/text_OrigQuote-en-xyz8-dolos-1', 
	'Page_tCC translationNotes/text_OrigQuote-en-xyz8-Theo-1', 'Page_tCC translationNotes/text_OrigQuote-en-xyz8-apstolos-1',
    'Page_tCC translationNotes/text_OrigQuote-en-xyz8-d-1', 'Page_tCC translationNotes/text_OrigQuote-en-xyz8-Iiso-1', 
	'Page_tCC translationNotes/text_OrigQuote-en-xyz8-Christo-1', 'Page_tCC translationNotes/text_OrigQuote-en-xyz8-kat-1',
    'Page_tCC translationNotes/text_OrigQuote-en-xyz8-pstin-1', 'Page_tCC translationNotes/text_OrigQuote-en-xyz8-eklektn-1', 
	'Page_tCC translationNotes/text_OrigQuote-en-xyz8-Theo-2', 'Page_tCC translationNotes/text_OrigQuote-en-xyz8-ka-1',
    'Page_tCC translationNotes/text_OrigQuote-en-xyz8-epgnosin-1', 'Page_tCC translationNotes/text_OrigQuote-en-xyz8-alitheas-1', 
	'Page_tCC translationNotes/text_OrigQuote-en-xyz8-ts-1', 'Page_tCC translationNotes/text_OrigQuote-en-xyz8-ka-2',
    'Page_tCC translationNotes/text_OrigQuote-en-xyz8-efsveian-1']


// Show OrigQuotes
WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_OrigQuote'))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

// Verify that the GL OrigQuote is as expected (εὐσέβεια)
String glText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_GLQuote-xyz8'))

String enText = WebUI.getText(findTestObject(origQuote[16]))

if (glText != enText) {
    println(('ERROR: unexpected value [' + glText) + '] in GL OrigQuote field')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because there is an unexpected value [' + 
        glText) + '] in GL OrigQuote field.')
} else {
    printHighlightStatus(origQuote[16], enText)
}

// Verify that clicking on an adjacent word does not highlight it (Issue 469)
WebUI.scrollToElement(findTestObject(origQuote[15]), 0)

WebUI.click(findTestObject(origQuote[15]))

if (testHighlightStatus(origQuote[15])) {
	println('ERROR: Clicking on a word adjacent to a highlighted word highlights it also')
	
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because clicking on a word adjacent to a highlighted word highlights it also.')	
}

//Test to ensure that the GL OrigQuote is the highlighted OrigLangQuote
glQuote = WebUI.getText(findTestObject('Page_tCC translationNotes/text_GLQuote-xyz8'))

for (def i : (0..origQuote.size - 1)) {
    if (testHighlightStatus(origQuote[i])) {
        if (WebUI.getText(findTestObject(origQuote[i])) != glQuote) {
            println('The GL quote is not the same as the original language highlighted text')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the GL quote is not the same as the original language highlighted text.')
        }
        
        break
    }
}

//Move the GL Orig Quote into view 
WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/Titus 11_note'), 2)

// Clear the gl origQuote value and test highlight status
WebUI.setText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), '')

printHighlightStatus(origQuote[16], enText)

enText = WebUI.getText(findTestObject(origQuote[2]))

setGLOrigQuote('', enText, true)

printHighlightStatus(origQuote[2], enText)

if (!(testHighlightStatus(origQuote[2]))) {
    println('ERROR: ' + enText + ' is not highlighted as expected')
    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because ' + enText + ' is not highlighted as expected.')
} else {
    println(enText + ' is highlighted as expected')
}

// Scroll the xyz8 check into view (xyx8 is hidden under the scripture pane)
WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-rtc9'), 1)

// Test single word copy and paste
WebUI.doubleClick(findTestObject(origQuote[3]))

CustomKeywords.'unfoldingWord_Keywords.HotKeys.sendKeys'(null, 'copy')

setGLOrigQuote('paste', '', true)

enText = WebUI.getText(findTestObject(origQuote[3]))

WebUI.delay(1)

if (!(testHighlightStatus(origQuote[3]))) {
    println(enText + ' is not highlighted as expected')
    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'(('Test failed because ' + enText) + ' is not highlighted as expected.')
} else {
    println(enText + ' is highlighted as expected')
}

// Test multiword copy and paste 
if ((system.contains('Windows') || myBrowser.contains('firefox')) || CustomKeywords.'unfoldingWord_Keywords.TestVersion.isVersionGreater'('1.0.3')) {
    // This test will fail on Mac/Chrome prior to v1.0.5 because Paste and match style is not available in Katalon
    dragIt(7, 9)

    CustomKeywords.'unfoldingWord_Keywords.HotKeys.sendKeys'(null, 'copy')

    setGLOrigQuote('paste', '', true)
	
	WebUI.delay(1)

    if (!(WebUI.verifyElementText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), 'κατὰ πίστιν ἐκλεκτῶν', 
        FailureHandling.CONTINUE_ON_FAILURE))) {
        println('ERROR: Failed to perform unformatted paste')
        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because multiword paste contained formatting.')
    } else {
        println('The pasted text was unformatted as desired.')
    }
    
    testHighlight(7, 9)
	
	// Test that copied highlighted original language word is not pasted highlighted
	WebUI.doubleClick(findTestObject(origQuote[7]))
	
	CustomKeywords.'unfoldingWord_Keywords.HotKeys.sendKeys'(null, 'copy')
	
	setGLOrigQuote('paste', '', true)
	
	WebUI.doubleClick(findTestObject(origQuote[7]))
	
	CustomKeywords.'unfoldingWord_Keywords.HotKeys.sendKeys'(null, 'copy')
	
	setGLOrigQuote('paste', '', false)
	
	enText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'))
	
	WebUI.delay(1)
	
	if (WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/span_GL_OrigQuoteText - Save'), 2, FailureHandling.OPTIONAL)) {

		if (myBrowser.contains('firefox')) {
			result = testHighlightStatus('Page_tCC translationNotes/span_GL_OrigQuoteText - Save')
		} else {
			if (WebUI.getCSSValue(findTestObject('Page_tCC translationNotes/span_GL_OrigQuoteText - Save'), 'background-color') == highlighted) {
				result = true
			}
		}
		if (result) {
			println('ERROR: The GL Quote ' + enText + ' is highlighted after the paste')
			CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the GL Quoate ' + enText + ' is highlighted after the paste, but it should not be.')
		} else {
			println(enText + ' is not highlighted as desired')
		}
		
	    WebUI.clickOffset(findTestObject('Page_tCC translationNotes/label_OrigQuote'), -10, -10)
	
	} else {
	
		println('No span was found in the OrigQuote field indicating pasted text was not highlighted as desired')
	
	}
	
	// Test pasting bolded text
    WebUI.doubleClick(findTestObject('Page_tCC translationNotes/text_OccurNote-Knowledge'))

    CustomKeywords.'unfoldingWord_Keywords.HotKeys.sendKeys'(null, 'copy')

    setGLOrigQuote('paste', '', true)
	
	WebUI.scrollToPosition(0, 0)

    WebUI.click(findTestObject('Page_tC Create/button_Preview'))

    WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/Titus 11_note'), 2)

    if (!WebUI.getText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8-Preview')).contains('**')) {
        println('ERROR: pasted text is not formatted')
        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the pasted bold text was not bolded.')
    } else {
        println('text is properly formatted')
    }
} else {
    println('Bypassing multiword paste and default format check')
}

WebUI.closeBrowser()

// Test proper highlighting when Parashah Setumah marker appears in Hebrew
WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : 'en_tn_16-NEH.tsv'], FailureHandling.STOP_ON_FAILURE)

if (WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/button_validator_Message_Close'), 1, FailureHandling.OPTIONAL)) {
	msg = 'Bypassed testing proper highlighting when Parashah Setumah marker because en_tn_16-NEH.tsv has on-open validation errors.'
	println(msg)
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendInfoMessage'(msg)
	WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_validator_Message_Close'))
	
} else {
	
	WebUI.waitForElementClickable(findTestObject('Page_tCC translationNotes/button_filterOpen'), 10)
	
	WebUI.click(findTestObject('Page_tCC translationNotes/button_filterOpen'))
	
	WebUI.click(findTestObject('Page_tCC translationNotes/filter_ChapterAll'))
	
	WebUI.click(findTestObject('Page_tCC translationNotes/filterOption_Chapter3'))
	
	WebUI.delay(3)
	
	WebUI.click(findTestObject('Page_tCC translationNotes/button_filterClose'))
	
	if (WebUI.getText(findTestObject('Object Repository/Page_tCC translationNotes/list_RowsPerPage')) != 25) {
		WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/list_RowsPerPage'))
		WebUI.delay(1)
		WebUI.click(findTestObject('Page_tCC translationNotes/option_RowsPerPage_parmned', [('rows') : 25]))
		}
	
	if (!testHighlightStatus('Page_tCC translationNotes/span_Jericho')) {
		println('ERROR: Jericho is not highlighted')
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because Jericho is not highlighted in Neh 3:2.')
	} else {
		println('Jericho is highlighted as expected')
	}
}


GlobalVariable.scriptRunning = false

WebUI.closeBrowser()


def setGLOrigQuote(def fnc, def quote, clickOut) {
	
		myElement = 'Page_tCC translationNotes/text_OrigQuote-GL-xyz8'
		
    if (fnc == 'paste') {
        WebUI.setText(findTestObject(myElement), '')
		
        WebUI.click(findTestObject(myElement))
		
		CustomKeywords.'unfoldingWord_Keywords.HotKeys.sendKeys'(myElement, 'paste')

    } else {
        WebUI.setText(findTestObject(myElement), quote)
    }
	
	if (clickOut) {
		WebUI.clickOffset(findTestObject('Page_tCC translationNotes/label_OrigQuote'), -10, -10)
	}
		
}

def printHighlightStatus(def element, def elementText) {
    hText = 'highlighted'

//    highlighted = 'rgba(255, 255, 0, 1)'

//    if (WebUI.getCSSValue(findTestObject(element), 'background-color') != highlighted ||
	if (WebUI.getAttribute(findTestObject(element), 'data-testselected', FailureHandling.OPTIONAL) != 'true') {
        hText = ('not ' + hText)
    }
    
//    println((elementText + ' is ') + hText)
}

def testHighlightStatus(def element) {
    def flag = false

    highlighted = 'rgba(255, 255, 0, 1)'

//	println('++++++++ data-testselected for ' + element + ' is ' + WebUI.getAttribute(findTestObject(element), 'data-testselected', FailureHandling.STOP_ON_FAILURE))
 //   if (WebUI.getCSSValue(findTestObject(element), 'background-color') == highlighted ||
	if (WebUI.getAttribute(findTestObject(element), 'data-testselected', FailureHandling.OPTIONAL) == 'true') {
        flag = true
    }
    
    return flag
}

def dragIt(def from, def to) {
    def ofset = WebUI.getElementWidth(findTestObject(origQuote[from]))

    Integer x = WebUI.getElementWidth(findTestObject(origQuote[from])) / -(2)

    WebDriver driver = DriverFactory.getWebDriver()

    Actions builder = new Actions(driver)

    if (from != to) {
        ofset = ((WebUI.getElementLeftPosition(findTestObject(origQuote[to])) + WebUI.getElementWidth(findTestObject(origQuote[
                to]))) - WebUI.getElementLeftPosition(findTestObject(origQuote[from])))
    }
    
    WebUI.mouseOverOffset(findTestObject(origQuote[from]), x, 0)

    builder.clickAndHold()

    builder.moveByOffset(ofset, 0).release().build().perform()
}

def testHighlight(def from, def to) {
    for (def i : (0..origQuote.size - 1)) {
		word = WebUI.getText(findTestObject(origQuote[i]))
        if ((i >= from) && (i <= to)) {
            if (!(testHighlightStatus(origQuote[i]))) {
                println(word + ' should be highlighted but is not.<<<<<<<<<<<<<<<<<<<<<<<<<<<<<')
				CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because ' + word + ' should be highlighted but is not.')
            } else {
                println(WebUI.getText(findTestObject(origQuote[i])) + ' is highlighted as expected.')
            }
        } else {
            if (testHighlightStatus(origQuote[i])) {
                println(word + ' is highlighted but should not be.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>')
				CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because ' + word + ' is highlighted but should not be.')
            } else {
                println(WebUI.getText(findTestObject(origQuote[i])) + ' is not highlighted as expected.')
            }
        }
    }
}

