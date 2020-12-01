
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
import java.io.File as File

//import com.kms.katalon.core.webui.common.WebUiCommonHelper
details = false

if (1 == 1) {
    WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], 
        FailureHandling.STOP_ON_FAILURE)

    WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

    WebUI.click(findTestObject('Page_tCC translationNotes/columns_ID'))

    WebUI.click(findTestObject('Page_tCC translationNotes/columns_OrigQuote'))

    WebUI.click(findTestObject('Page_tCC translationNotes/columns_SupportReference'))

    WebUI.click(findTestObject('Page_tCC translationNotes/columns_OccurrenceNote'))

    WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

    WebUI.delay(1)
}

highlighted = 'rgba(255, 255, 0, 1)'

elips = '…'

ids = []

origQuotes = []

chapters = []

verses = []

first = true

new File('/Users/cckozie/Downloads/en_tn_57-TIT.tsv').splitEachLine('\t', { def fields ->
        if ((fields[2]) != 'intro') {
            if (!(first)) {
				fields[5] = fields[5].trim()
                println(((fields[3]) + ':') + (fields[5]))

                ids.add(fields[3])

                (fields[5]) = (fields[5]).replace(',', '')

                (fields[5]) = (fields[5]).replace(';', '')

                origQuotes.add(fields[5])

                chapters.add(fields[1])

                verses.add(fields[2])
            } else {
                first = false
            }
        } //Read data from CSV
    })

lastChapter = ''

lastVerse = ''

now = new Date()

//nFormat = ('MMddyyhhmmss')
fName = (('highlights_' + now.format('MMddyyhhmmss')) + '.txt')

oFile = new File('/Users/cckozie/Katalon Studio/Files/' + fName)

oFile.delete()

i = 0

ultLeft = (WebUI.getElementLeftPosition(findTestObject('Page_tCC translationNotes/text_unfoldingWord Literal Text v15')) - 
30)

println('ultLeft is ' + ultLeft)

//for (id in ids) {
ids.each { id ->
    msg = ('\n>>>>> ' + id)

    println(msg)

    oFile.append(msg)

    words = []
	println('string is:' + origQuotes[i] + ':  and length is ' + origQuotes[i].length())
	if (origQuotes[i].length() > 0) {
		words = (origQuotes[i]).split('…| ')
	}
	
    if (details) {
        println(origQuotes[i])

        println(words)
    }
    
    ellipsis = (origQuotes[i]).contains(elips)

    divId = (((((('id("header-' + (chapters[i])) + '-') + (verses[i])) + '-') + id) + '")')

    if (WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_1', [('myDiv') : divId]), 
        1, FailureHandling.OPTIONAL)) {
        println('scrolling')

        WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_1', [('myDiv') : divId]), 
            1)
    } else {
        println('paging')

        WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'))

        WebUI.delay(2)
    }
    
    println('done scrolling')

    WebUI.delay(1)

    if (((chapters[i]) != lastChapter) || ((verses[i]) != lastVerse)) {
        msg = (((('\nReading spans for ' + (chapters[i])) + ':') + (verses[i])) + '\n')

        println(msg)

        oFile.append(msg)

        ultLeft = (WebUI.getElementLeftPosition(findTestObject('Page_tCC translationNotes/sup_ULT_Reference', [('chpt') : chapters[
                    i], ('verse') : verses[i]])) - 10)

        if (details) {
            println('ultLeft is ' + ultLeft)
        }
        
        spans = []

        for (def myNum : (1..30)) {
            word = WebUI.getText(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('myDiv') : divId, ('chpt') : chapters[
                        i], ('verse') : verses[i], ('wordNum') : myNum]), FailureHandling.OPTIONAL)

            wordLeft = WebUI.getElementLeftPosition(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('myDiv') : divId
                        , ('chpt') : chapters[i], ('verse') : verses[i], ('wordNum') : myNum]))

            if ((word.length() > 0) && (wordLeft < ultLeft)) {
                spans.add(word)
            } else {
                break
            }
        }
        
        // Convert original language array to string
		origLang = ''
        origLang = spans.join(',')

        //		olString = origLang.replace(',,','%')
        olString = origLang.replace(',', ' ')

        //		olString = origLang.replace('%',', ')
        if (details) {
            println(spans)

            println(olString)
        }
        println('olString length is ' + olString.length())
        lastChapter = (chapters[i])

        lastVerse = (verses[i])
    }
    
    bgColors = []

    for (def wordNum : (1..spans.size())) {
        bgColors.add(WebUI.getCSSValue(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('myDiv') : divId
                        , ('chpt') : chapters[i], ('verse') : verses[i], ('wordNum') : wordNum]), 'background-color'))
    }
    
    if (!(ellipsis)) {
        numWords = words.size()
		if (details) {
			println('words = ' + words)
			println('There are ' + numWords + ' words in the quote string')
		}
		if (origQuotes[i].length() > 0) {
			
            w = olString.indexOf(origQuotes[i])

            left = olString.substring(0, w)

            lWords = left.count(' ')

            if (details) {
                println('start of quote string is ' + w)

                println(('There are ' + lWords) + ' words left of the quote string')

                println(('There are ' + numWords) + ' words in the quote string')
            }
            
            for (int sp : (0..spans.size() - 1)) {
                sp1 = (sp + 1)

                wordStr = ((('word ' + sp1) + ', ') + (spans[sp]))

                if ((sp < lWords) || (sp >= (lWords + numWords))) {
                    if ((bgColors[sp]) == highlighted) {
                        msg = (wordStr + ' is highighted but should not be\n')

                        println('++++++++++ ' + msg)

                        oFile.append(msg)
                    } else {
                        if (details) {
                            print(wordStr + ' is not highlighted as expected\n')
                        }
                    }
                } else {
                    if ((bgColors[sp]) == highlighted) {
                        if (details) {
                            print(wordStr + ' is highighted as expected\n')
                        }
                    } else {
                        msg = (wordStr + ' is not highlighted but should be\n')

                        println('---------- ' + msg)

                        println('Background color is ' + (bgColors[sp]))

                        oFile.append(msg)
                    }
                }
            }
        } else {
            msg = 'Bypassed because there is no orignial language quote specified'
            println(msg)
            oFile.append(msg)
        }
    } else {
        msg = 'processing check with ellipsis'
        println(msg)
        oFile.append(msg)
		
		//Find quote words before the elipsis
		elpLoc = origQuote.indexOf(elips)
		preWords = origQuote.subString(0,elpLoc -1)
		
		//Find quote words after the elipsis
		
    }
    
    i++
}

WebUI.closeBrowser()

