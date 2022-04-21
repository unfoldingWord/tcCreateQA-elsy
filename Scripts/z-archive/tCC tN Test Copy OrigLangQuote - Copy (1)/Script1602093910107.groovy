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

String highlighted = 'rgba(255, 255, 0, 1)'

WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('$origQuote') : ''
        , ('$newOrigQuote') : ''], FailureHandling.STOP_ON_FAILURE)

system = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getOperatingSystem'()

myBrowser = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getBrowserAndVersion'()

(width, height) = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getScreenResolution'()

objectPage = 'Page_tCC translationNotes/'

//Define and load the original language text array for ID xyz8'
origQuote = []

origQuote = ['text_OrigQuote-en-xyz8-Pavlos-1', 'text_OrigQuote-en-xyz8-dolos-1', 'text_OrigQuote-en-xyz8-Theo-1', 'text_OrigQuote-en-xyz8-apstolos-1'
    , 'text_OrigQuote-en-xyz8-d-1', 'text_OrigQuote-en-xyz8-Iiso-1', 'text_OrigQuote-en-xyz8-Christo-1', 'text_OrigQuote-en-xyz8-kat-1'
    , 'text_OrigQuote-en-xyz8-pstin-1', 'text_OrigQuote-en-xyz8-eklektn-1', 'text_OrigQuote-en-xyz8-Theo-2', 'text_OrigQuote-en-xyz8-ka-1'
    , 'text_OrigQuote-en-xyz8-epgnosin-1', 'text_OrigQuote-en-xyz8-alitheas-1', 'text_OrigQuote-en-xyz8-ts-1', 'text_OrigQuote-en-xyz8-ka-2'
    , 'text_OrigQuote-en-xyz8-efsveian-1']

for (def i : (0..origQuote.size() - 1)) {
    (origQuote[i]) = (objectPage + (origQuote[i]))
}

// Show OrigQuotes
WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_OrigQuote'))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

// Verify that the GL OrigQuote is as expected (εὐσέβεια)
String glText = WebUI.getText(findTestObject('Page_tCC translationNotes/text_ID_m2jl'))

String enText = WebUI.getText(findTestObject(origQuote[16]))

if (glText != enText) {
    println(('Unexpected value [' + glText) + '] in GL OrigQuote field')
} else {
    printHighlightStatus(origQuote[16], enText)
}

//Move the GL Orig Quote into view 
WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/Titus 11_note'), 2)

// Clear the gl origQuote value and test highlight status
WebUI.setText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), '')

printHighlightStatus(origQuote[16], enText)

enText = WebUI.getText(findTestObject(origQuote[2]))

setGLOrigQuote('', enText)

printHighlightStatus(origQuote[2], enText)

if (!(testHighlightStatus(origQuote[2]))) {
    println(enText + ' is not highlighted as expected')
} else {
    println(enText + ' is highlighted as expected')
}

// Test single word copy and paste
WebUI.doubleClick(findTestObject(origQuote[3]))

copyText()

setGLOrigQuote('paste', '')

enText = WebUI.getText(findTestObject(origQuote[3]))

if (!(testHighlightStatus(origQuote[3]))) {
    println(enText + ' is not highlighted as expected')
} else {
    println(enText + ' is highlighted as expected')
}

// Test multiword copy and paste 
if ((system.contains('Windows') || myBrowser.contains('firefox')) || CustomKeywords.'unfoldingWord_Keywords.TestVersion.isVersionGreater'(
    '1.0.3')) {
    // This test will fail on Mac/Chrome prior to v1.0.5 because Paste and match style is not available in Katalon
    dragIt(7, 9)
	
    copyText()

    setGLOrigQuote('paste', '')
	
	if (!WebUI.verifyElementText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), 'κατὰ πίστιν ἐκλεκτῶν', FailureHandling.CONTINUE_ON_FAILURE)) {
		println('Failed to perform unformatted paste')
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because multiword paste contained formatting.')		
	} else {
		println('The pasted text was unformatted as desired.')
	}
	
    testHighlight(7, 9)
	
// Test pasting bolded text
	WebUI.doubleClick(findTestObject('Page_tCC translationNotes/text_OccurNote-Knowledge'))
	
	copyText()
	
	setGLOrigQuote('paste', '')
	
	WebUI.click(findTestObject('null'))

	WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/Titus 11_note'), 2)
	
	if (WebUI.getText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8-Preview')).contains('**')) {
		println('text is formatted')
	
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because the pasted bold text was bolded.')
	} else {
		println('text is not formatted')
	}
	
} else {
    println('Bypassing multiword paste and default format check')
}

WebUI.closeBrowser()

def copyText() {
    if (system.contains('Windows')) {
        WebUI.sendKeys(null, Keys.chord(Keys.CONTROL, 'c'))
    } else if (myBrowser.contains('firefox')) {
        WebUI.sendKeys(null, Keys.chord(Keys.COMMAND, 'c'))
    } else {
        WebUI.sendKeys(null, Keys.chord(Keys.CONTROL, Keys.INSERT))
    }
}

def setGLOrigQuote(def fnc, def quote) {
    if (fnc == 'paste') {
        WebUI.setText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), '')

        WebUI.click(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'))

        if (system.contains('Windows')) {
            WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), Keys.chord(Keys.CONTROL, 
                    Keys.SHIFT, 'v'))
        } else if (myBrowser.contains('firefox')) {
            WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), Keys.chord(Keys.COMMAND, 
                    Keys.SHIFT, 'v'))
        } else {
            WebUI.sendKeys(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), Keys.chord(Keys.SHIFT, Keys.INSERT))
        }
    } else {
        WebUI.setText(findTestObject('Page_tCC translationNotes/text_OrigQuote-GL-xyz8'), quote)
    }
    
    WebUI.clickOffset(findTestObject('Page_tCC translationNotes/label_OrigQuote'), -10, -10)
}

def printHighlightStatus(def element, def elementText) {
    hText = 'highlighted'

    highlighted = 'rgba(255, 255, 0, 1)'

    if (WebUI.getCSSValue(findTestObject(element), 'background-color') != highlighted) {
        hText = ('not ' + hText)
    }
    
    println((elementText + ' is ') + hText)
}

def testHighlightStatus(def element) {
    def flag = false

    highlighted = 'rgba(255, 255, 0, 1)'

    if (WebUI.getCSSValue(findTestObject(element), 'background-color') == highlighted) {
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
        if ((i >= from) && (i <= to)) {
            if (!(testHighlightStatus(origQuote[i]))) {
                println(WebUI.getText(findTestObject(origQuote[i])) + ' should be highlighted but is not.<<<<<<<<<<<<<<<<<<<<<<<<<<<<<')
            } else {
                println(WebUI.getText(findTestObject(origQuote[i])) + ' is highlighted as expected.')
            }
        } else {
            if (testHighlightStatus(origQuote[i])) {
                println(WebUI.getText(findTestObject(origQuote[i])) + ' is highlighted but should not be.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>')
            } else {
                println(WebUI.getText(findTestObject(origQuote[i])) + ' is not highlighted as expected.')
            }
        }
    }
}

