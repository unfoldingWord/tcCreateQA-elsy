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

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

(width, height) = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getScreenResolution'()

//Define the Spanish text to use for the outline'
textArray = []

textArray = ['Pablo instruye a Tito para que designe líderes piadosos (1:1-16)', 'Pablo instruye a Tito para que entrene a las personas a vivir vidas piadosas (2: 1-3: 11)'
    , 'Pablo termina compartiendo algunos de sus planes y enviando saludos a varios creyentes (3: 12-15)']

//Define the page object names for the GL outline'
glArray = []

glArray = ['Page_tCC translationNotes/tNText_GLOutlinePoint1', 'Page_tCC translationNotes/tNText_GLOutlinePoint2' //
    , 'Page_tCC translationNotes/tNText_GLOutlinePoint3']

//Define the page object names for the en outline'
enArray = []

enArray = ['Page_tCC translationNotes/tNText_enOutlinePoint1', 'Page_tCC translationNotes/tNText_enOutlinePoint2' //
    , 'Page_tCC translationNotes/tNText_enOutlinePoint3']


//Set the 3 outine points to the Spanish text'
for (def i : (0..2)) {
    if (i >= 1) {
        WebUI.setText(findTestObject(glArray[i]), '')

        WebUI.delay(1)
    }
    
    WebUI.setText(findTestObject(glArray[i]), textArray[i])

    WebUI.delay(1)
}

WebUI.clickOffset(findTestObject('Page_tCC translationNotes/tNText_GLOutlinePoint1'), -500, 0)

WebUI.click(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'))

WebUI.waitForElementNotPresent(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'), 10)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

//Verify that the Spanish text was correctly set and saved'
for (def i : (0..2)) {
    WebUI.verifyElementText(findTestObject(glArray[i]), textArray[i])
}

//Copy the en outline text to the GL outline text'
for (def i : (0..2)) {
    if (i >= 1) {
        WebUI.setText(findTestObject(glArray[i]), '')

        WebUI.delay(1)
    }
    
    WebUI.setText(findTestObject(glArray[i]), WebUI.getText(findTestObject(enArray[i])))

    WebUI.delay(1)

    WebUI.getText(findTestObject('Page_tCC translationNotes/tNText_enOutlinePoint1'))
}

WebUI.clickOffset(findTestObject('Page_tCC translationNotes/tNText_GLOutlinePoint1'), -500, 0)

WebUI.click(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'))

WebUI.waitForElementNotPresent(findTestObject('Page_tCC translationNotes/button_SaveEnabled - xPath'), 10)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)

//Verify that the en text overwrote the Spanish text and was saved'
for (def i : (0..2)) {
    WebUI.verifyElementText(findTestObject(glArray[i]), WebUI.getText(findTestObject(enArray[i])))
}

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

