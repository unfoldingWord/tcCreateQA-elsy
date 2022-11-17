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
import org.openqa.selenium.Point as Point

//import com.kms.katalon.core.webui.common.WebUiCommonHelper
WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_ID'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_Quote'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_SupportReference'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_Note'))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

if (1 == 2) {
    WebUI.setViewPortSize(400, 80)

    WebUI.delay(1)

    WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/p_m2jl', [('myid') : id]), 1)

    println(WebUI.verifyTextPresent('rtc9', false))

    while (!(WebUI.verifyTextPresent('rtc9', false))) {
        WebUI.scrollToPosition(0, yPos)

        WebUI.delay(1)
    }
}

id = 'rtc9'

//WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/p_m2jl', [('myid') : id]), 1)
//println(WebUI.getText(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_2', [('id') : 'xyz8', ('wordNum') : '10'])))
WebUI.delay(1)

divId = 'id("header-1-1-rtc9")'

WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_1', [('myDiv') : divId]), 1)

if (1 == 2) {
    i = 0

    //List<WebElement> links = WebUiCommonHelper.findWebElements(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_1', [('myDiv') : divId]),1)
    //List<WebElement> links = WebUiCommonHelper.findWebElements(findTestObject('Page_tCC translationNotes/div_11B'),1)
    List<WebElement> links = WebUI.findWebElements(findTestObject('Page_tCC translationNotes/div_11C'), 1)

    //List<WebElement> links = WebUI.findWebElements(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_1', [('myDiv') : divId]),1)
    //List<WebElement> listElement = WebUI.findWebElements(findTestObject('Page_tCC translationNotes/span_OrigLangWord', [('word') : 'εὐσέβειαν']), 
    //    2).findPropertyValue('xpath')
    println(links.size() + ' links found')

    println(links)

    for (WebElement link : links) {
        //	println(WebUI.getAttribute(findTestObject(link), 'class',FailureHandling.OPTIONAL))
        //	println(WebUI.getAttribute(findTestObject(link), 'xpath',FailureHandling.OPTIONAL))
        //	println(WebUI.getAttribute(findTestObject(link), 'Xpath',FailureHandling.OPTIONAL))
        println(i)

        println(link)

        try {
            link.click()

            //		println(WebUI.getAttribute(findTestObject(link), 'class',FailureHandling.OPTIONAL))
            //		println(WebUI.getAttribute(findTestObject(link), 'xpath',FailureHandling.OPTIONAL))
            println(WebUI.getAttribute(findTestObject(link), 'Xpath', FailureHandling.OPTIONAL))
        }
        catch (Exception e) {
            println('oops')
        } 
        
        i++
    }
}

highlighted = 'rgba(255, 255, 0, 1)'

myWord = 'Θεοῦ'

for (def myNum : (1..17)) {
    //myNum = 8
    //WebUI.click(findTestObject('Page_tCC translationNotes/span_OLW_Parmed',[('word') : myWord, ('wordNum') : myNum]))
    word = WebUI.getText(findTestObject('Page_tCC translationNotes/span_OLW_Parmed_1', [('wordNum') : myNum]), FailureHandling.OPTIONAL)
	//println(word)
    if (word == myWord) {
        if (WebUI.getCSSValue(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('word') : myWord, ('wordNum') : myNum]), 
            'background-color') == highlighted) {
            print(('word ' + myNum) + ' is highighted')
        } else {
            print(('word ' + myNum) + ' is not highlighted')
        }
    }
}

return false

divId = 'id("header-1-1-rtc9")'

WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', 0, FailureHandling.OPTIONAL))

for (def i : (1..30)) {
    word = WebUI.getText(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_2A', [('id') : id, ('wordNum') : i]), 
        FailureHandling.OPTIONAL)

    if (word.length() > 0) {
        if (WebUI.getCSSValue(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_2A', [('id') : id, ('wordNum') : i]), 
            'background-color') == highlighted) {
            print(word + ' is highighted')
        }
    } else {
        break
    }
}

WebUI.closeBrowser()

WebUI.doubleClick(findTestObject('Page_tCC translationNotes/span_OrigWordParmed', [('word') : 'Παῦλος', ('id') : 'xyz8', ('wordNum') : '1']))

WebUI.delay(1)

//WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/text_GLQuote-xyz8'), 1)
return false

List<WebElement> listElement = WebUI.findWebElements(findTestObject('Page_tCC translationNotes/span_OrigLangWord', [('word') : 'εὐσέβειαν']), 
    2).findPropertyValue('xpath')

println(listElement)

println(WebUI.verifyElementVisible(findTestObject('Page_tCC translationNotes/span_OrigLangWord', [('word') : 'εὐσέβειαν'])))

if (WebUI.verifyElementVisible(findTestObject('Page_tCC translationNotes/span_OrigLangWord', [('word') : 'εὐσέβειαν']))) {
    println('found in viewport')

    WebUI.delay(2)

    WebUI.doubleClick(findTestObject('Page_tCC translationNotes/span_OrigLangWord', [('word') : 'εὐσέβειαν']))
}

return false

WebUI.verifyElementVisible(findTestObject('Page_tCC translationNotes/span_OrigLangWord'))

WebUI.verifyElementInViewport(findTestObject('Page_tCC translationNotes/span_OrigLangWord'), 1)

WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/p_m2jl', [('myid') : id]), 1)

//String liSelector = findTestObject('Object Repository/Page_Recent Discussions/Content.(li_Item)').findPropertyValue(
//    'xpath')
String liSelector = findTestObject('Page_tCC translationNotes/span_OrigLangWord', [('word') : 'εὐσέβειαν']).findPropertyValue(
    'xpath')

println(liSelector)

println(liSelector.size())

//List<WebElement> listElement = WebUI.findWebElements(findTestObject('Page_tCC translationNotes/span_OrigLangWord', [('word') : 'εὐσέβειαν']),2)
//println(listElement)
for (def elem : liSelector) {
    println(WebUI.getElementLeftPosition(elem))
}

t1 = 'κατὰ'

println(testHighlightStatus(t1))

t2 = 'πίστιν'

println(testHighlightStatus(t2))

t3 = 'εὐσέβειαν'

println(testHighlightStatus(t3))

def testHighlightStatus(def word) {
    def flag = false

    highlighted = 'rgba(255, 255, 0, 1)'

    println(WebUI.getCSSValue(findTestObject('Page_tCC translationNotes/span_OrigLangWord', [('word') : word]), 'background-color'))

    if (WebUI.getCSSValue(findTestObject('Page_tCC translationNotes/span_OrigLangWord', [('word') : word]), 'background-color') == 
    highlighted) {
        flag = true
    }
    
    return flag
}

