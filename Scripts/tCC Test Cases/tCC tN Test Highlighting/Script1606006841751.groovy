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
import org.sikuli.script.*
import java.awt.datatransfer.Clipboard as Clipboard
import java.awt.datatransfer.Transferable as Transferable
import java.awt.datatransfer.DataFlavor as DataFlavor
import java.awt.Toolkit as Toolkit

// 02/10/21	Modified to use the HotKeys custom keyword for select-all and copy

// ============= CURRENTLY IGNORES OCCURRANCE NUMBER WHEN PROCESSING ELLIPSIS =============

download = true

myFile = 'en_tn_65-3JN.tsv' // SET TO FILE TO BE TESTED
myfile1 = 'tn_3JN.tsv'
myId = ''					// SET TO THE ID OF THE CHECK TO START TESTING WITH. IF EMPTY, STARTS WITH FIRST ID.

fName = '/Users/' + GlobalVariable.pcUser + '/Downloads/' + myFile

//system = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getOperatingSystem'()

if (!new File(fName).exists() || download) {
		
	WebUI.openBrowser('https://git.door43.org/unfoldingWord/en_tn/raw/branch/master/' + myFile)
	
	WebUI.maximizeWindow()
	
//	myBrowser = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getBrowserAndVersion'()
	if (GlobalVariable.browser == '' || GlobalVariable.browser == null) {
		GlobalVariable.browser = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getBrowserAndVersion'()
	}
	
	CustomKeywords.'unfoldingWord_Keywords.HotKeys.sendKeys'(null, 'all')
	
	CustomKeywords.'unfoldingWord_Keywords.HotKeys.sendKeys'(null, 'copy')
	
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
	
	Transferable contents = clipboard.getContents(null)
	
	fileText = ((contents.getTransferData(DataFlavor.stringFlavor)) as String)
	
	println(fileText)
		
	oFile = new File(fName)
	
	oFile.delete()
	
	oFile.write(fileText)
}

numStr = ''
myFile.each {a ->
	if (a.isNumber()) {
		numStr += a
	}
}
num = numStr.toInteger()
if (num < 40) {
	OT = true
} else {
	OT = false
}

details = false //Print details?

stopOnError = true

maxErrors = 50

errors = 0

bypass = false

highlighted = 'rgba(255, 255, 0, 1)'

elips = '…'

ids = []

origQuotes = []

chapters = []

verses = []

occurs = []

first = true

paged = false

puncts = [',',';',':','"','?','.']

new File('/Users/' + GlobalVariable.pcUser + '/Downloads/' + myFile).splitEachLine('\t', { def fields ->
        if ((fields[2]) != 'intro') {
            if (!(first)) {
                (fields[5]) = (fields[5]).trim()

                occur = (fields[6]).trim()
				
				occur = (occur.toInteger())
				
				println(((fields[3]) + ':') + (fields[5]) + ':' + fields[6])

                ids.add(fields[3])
				
				for (punct in puncts) {
					(fields[5]) = (fields[5]).replace(punct, '')
				}
				
				if (OT) {
					fields[5] = fields[5].replace('־',' ')
				}

                origQuotes.add(fields[5])

                chapters.add(fields[1])

                verses.add(fields[2])
				
				occurs.add(occur)
				
            } //Read data from CSV
            else {
                first = false
            }
        }
    })

WebUI.callTestCase(findTestCase('tCC Components/tCC tsv Open For Edit'), [('$username') : '', ('$password') : '', ('file') : myfile1], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.waitForElementPresent(findTestObject('Page_tCC translationNotes/button_ViewColumns'), 20)

WebUI.click(findTestObject('Page_tCC translationNotes/button_ViewColumns'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_ID'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_Quote'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_SupportReference'))

WebUI.click(findTestObject('Page_tCC translationNotes/columns_Note'))

WebUI.click(findTestObject('Page_tCC translationNotes/btnX_CloseColumns'))

WebUI.delay(1)

lastChapter = ''

lastVerse = ''

now = new Date()

//nFormat = ('MMddyyhhmmss')
fName = (((('highlights_' + myFile) + '-') + now.format('MMddyyhhmmss')) + '.txt')

String logsDir = '/Users/' + GlobalVariable.pcUser + GlobalVariable.logsDir
		
oFile = new File(logsDir + '/' + fName)

//oFile.delete()

i = 0

ultLeft = (WebUI.getElementLeftPosition(findTestObject('Page_tCC translationNotes/text_unfoldingWord Literal Text v15')) - 
30)

//println('ultLeft is ' + ultLeft)

//for (id in ids) {
ids.each({ def id ->
        msg = ('\n>>>>> ' + id)

        println(msg)

        oFile.append(msg)

        words = []

		occur = occurs[i]
		
		occurUsed = false

        println((('string is:' + (origQuotes[i])) + ':  and length is ') + (origQuotes[i]).length() + ' with occurrence of ' + occur)

		if (occur > 2) {
			System.exit(1)
		}
		
        if ((origQuotes[i]).length() > 0) {
            words = (origQuotes[i]).split('…| ')
        }
        
        if (details) {
            println(origQuotes[i])

            println(words)
        }
        
        divId = (((((('id("header-' + (chapters[i])) + '-') + (verses[i])) + '-') + id) + '")')
		
		//println('divId:' + divId)

        if (WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_1', [('myDiv') : divId]), 
            1, FailureHandling.OPTIONAL)) {
            println('scrolling')

            WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_1', [('myDiv') : divId]), 
                1) //		System.exit(1)
			
        } else {
            println('paging')
			
            paged = true

            WebUI.click(findTestObject('Object Repository/Page_tCC translationNotes/button_NextPage'))

            println('waiting')

            WebUI.waitForElementPresent(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_1', [('myDiv') : divId]), 
                5)
        }
        
        println('done scrolling')

        WebUI.delay(1)
		
		if (myId != '' && id != myId) {
			//println('id:'+id + '  myId:'+myId)
			i ++
			return false
		} else {
			println('found myId')
			myId = ''
	        if (WebUI.verifyElementPresent(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_1', [('myDiv') : divId]), 
	            1, FailureHandling.OPTIONAL)) {
	            println('scrolling')
	
	            WebUI.scrollToElement(findTestObject('Page_tCC translationNotes/span_OrigWordParmed_1', [('myDiv') : divId]), 
	                1) //		System.exit(1)
	        } else {
				println('did not find divId')
				i ++
				return false
            }
		}

        if (((chapters[i]) != lastChapter) || (((verses[i]) != lastVerse) || paged)) {
            msg = (((('\nReading spans for ' + (chapters[i])) + ':') + (verses[i])) + '\n')

            paged = false

            println(msg)

            oFile.append(msg)

            ultLeft = (WebUI.getElementLeftPosition(findTestObject('Page_tCC translationNotes/sup_ULT_Reference', [('chpt') : chapters[
                        i], ('verse') : verses[i]])) - 10)

            if (details) {
                //println('ultLeft is ' + ultLeft)
            }
            
            spans = []
			
			fncs = []

            for (def myNum : (1..100)) {
                word = WebUI.getText(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('myDiv') : divId, ('chpt') : chapters[
                            i], ('verse') : verses[i], ('wordNum') : myNum]), FailureHandling.OPTIONAL)
				
//				wordText = WebUI.getText(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('myDiv') : divId, ('chpt') : chapters[
//                            i], ('verse') : verses[i], ('wordNum') : myNum]), FailureHandling.OPTIONAL)
				
//				println('wordText:' + wordText + ' - length:' + wordText.length())
				//println('word:' + word + ' - length:' + word.length())
				

                wordLeft = WebUI.getElementLeftPosition(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('myDiv') : divId
                            , ('chpt') : chapters[i], ('verse') : verses[i], ('wordNum') : myNum]))
				
				if (!word.contains('fn')) {

	                if ((word.length() > 0) && (wordLeft < ultLeft)) {
	                    spans.add(word)
	                } else {
	                    break
	                }
				} else {
					//println('word ' + myNum + ' contains fn')
					fncs.add(myNum)
				}
            }
            
            // Convert original language array to string
            origLang = ''

            origLang = spans.join(',')
			
			olString = origLang

			for (punct in puncts) {
				olString = olString.replace(punct, ' ')
			}
			
			olString.replace('  ',' ')
			
            if (details) {
                println(spans)

                println(olString)
            }
            
            println('olString length is ' + olString.length())

            lastChapter = (chapters[i])

            lastVerse = (verses[i])
        }
        
        bgColors = []
		//println('fncs:' + fncs)

        for (def wordNum : (1..spans.size())) {
			if (!fncs.contains(wordNum,)) {
	            bgColors.add(WebUI.getCSSValue(findTestObject('Page_tCC translationNotes/span_OLW_Parmed', [('myDiv') : divId
	                            , ('chpt') : chapters[i], ('verse') : verses[i], ('wordNum') : wordNum]), 'background-color'))
			} else {
				println('did not add color for word ' + wordNum)
			}
        }
        
        println('Background colors:' + bgColors)

        origQuote = (origQuotes[i])
		
        ellipsis = (origQuote).contains(elips)

		elipsCount = origQuote.count(elips)
		
		el = 1
		
		if (ellipsis) {
			spansSave = spans
			olStringSave = olString
		}
		
		while ( el <= elipsCount) {
			msg = '\n===== Processing check with ellipsis====='
			
			println(msg)
		
//			oFile.append(msg)
		
			println(olString)
		
			//Find quote words before the elipsis			
			elpLoc = origQuote.indexOf(elips)
			preWords = origQuote.substring(0, elpLoc)
			
			preWordsCount = preWords.count(' ') + 1
		
			println('preWords:' + preWords)
			
			//Find quote words after the elipsis
			println('el:' + el)
			println('elipsCount:' + elipsCount)
			if (el < elipsCount) {
				nextElp = origQuote.indexOf(elips, elpLoc+1)
				println('nextElp:' + nextElp)
				postWords = origQuote.substring(elpLoc + 1, nextElp)
				println('postWords:' + postWords)
			} else {
				postWords = origQuote.substring(elpLoc + 1)
				println('postWordsA:' + postWords)
			}
		
			println('postWords:' + postWords)
			
			//Find the instance of prewords closest to the postwords
			preLocs = indexesOfAll(olString,preWords)
			
//			end = postWords.indexOf(' |־')
			end = postWords.indexOf(' ')
			
			if (end < 0) {
				end = postWords.indexOf(elips)
			}
			
			if (end < 0) {
				end = postWords.length()
			}
			
			postWord = postWords.substring(0,end)
			println(postWord)
			
			postLocs = indexesOfAll(olString,postWord)
			
			println('preLocs:' + preLocs)
			println('preLocs[0]:' + preLocs[0])
			println('postLocs:' + postLocs)
			
			
			for (loc in postLocs) {
				println(loc)
				if (loc > preLocs[0]) {
					pLoc = loc
					break
				}
			}
			println(pLoc)
			
			for (int i : (preLocs.size()-1..0)) {
				if (preLocs[i] < pLoc) {
					pI = i
					break
				}
			}
			
			println('preLoc:' + preLocs[pI])
			
			closest = preLocs[pI]
			
			println('closest:' + closest)
		
			left = olString.substring(0, closest)
			
			println('string to start of quote:' + left)
		
			lWords = left.count(' ')
		
			//These should not be highlighted
			println(lWords + ' words before quote')
		
			i1 = 0
		
			i11 = (i1 + 1)
		
			while (i1 < lWords) {
				wordStr = ((('word ' + i11) + ', ') + (spans[i1]))
		
				msg = spans[i1] + ' should not be highlighted\n'
		
//				oFile.append(msg)
				
				println(msg)
		
                testWordHighlight(i1, false)
		
				i1++
			}
			
			//These should be highlighted
			lQuoteWords = (preWords.count(' ') + 1)
		
			println(lQuoteWords + ' quoted words before the ellipsis')
		
			for (int i2 : (i1..(i1 + lQuoteWords) - 1)) {
				i21 = (i2 + 1)
		
				wordStr = ((('word ' + i21) + ', ') + (spans[i2]))
		
				msg = spans[i2] + ' should be highlighted\n'
		
//				oFile.append(msg)
				
				println(msg)
		
		        testWordHighlight(i2, true)
		
				i2++
			}
			
			//The string beginning after the ellipsis is processed as strings without ellipsis
			spans = spans.drop(lWords + lQuoteWords)
		
		    bgColors = bgColors.drop(lWords + lQuoteWords)
		
			println('spans:' + spans)
		
			println('closest:' + closest)
		
			for (int i : (1..lQuoteWords)) {
				pos = olString.indexOf(' ', closest)
		
				closest = (pos + 1)
		
				println('pos:' + pos)
			}
			
			println('Start of string remainder:' + pos)
		
			olString = olString.substring(pos + 1)
		
			println('Remainder:' + olString)
			
			println('dropping lQuoteWords:' + lQuoteWords)
			
			println('origQuote:' + origQuote)
			
			origQuote = origQuote.substring(elpLoc+1, origQuote.length())
			
			words = (origQuote).split('…| ')
		
			println('new origQuote:' + origQuote) //		sp = 0
		
			el ++
		
		}
		
        testHighlight()

        if (ellipsis) {
            spans = spansSave

            olString = olStringSave 
			
        }
        
        i++
    })

//oFile.close()

println(errors + ' highlighting errors found in ' + myFile)

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

def testWordHighlight(def sp, def state) {
    if (!(state)) {
        if ((bgColors[sp]) == highlighted) {
            msg = (wordStr + ' is highlighted but should not be\n')

            println('++++++++++ ' + msg)

            oFile.append(msg)

            errors++

            if (stopOnError && (errors > maxErrors)) {
				oFile.close()
                System.exit(1)
            }
        } else {
            if (details) {
                print(wordStr + ' is not highlighted as expected\n')
            }
        }
    } else {
        if ((bgColors[sp]) == highlighted) {
            if (details) {
                print(wordStr + ' is highlighted as expected\n')
            }
        } else {
            msg = (wordStr + ' is not highlighted but should be\n')

            println('---------- ' + msg)

            println('Background color is ' + (bgColors[sp]))

            oFile.append(msg)

            errors++

            if (stopOnError && (errors > maxErrors)) {
				oFile.close()
                System.exit(1)
            }
        }
    }
}

def testHighlight() {
    numWords = words.size()

    if (details) {
        println('words = ' + words)

        println(('There are ' + numWords) + ' words in the quote string')
		
		println('Occurrence is ' + occur)
    }
    
    if (origQuote.length() > 0) {
		
		// Process occurrence number 
		finds = []
		w = olString.indexOf(origQuote)
		
		if (occur > 1 && !occurUsed && !ellipsis) {
			println('processing occurrence ' + occur)
			while (w >= 0) {
				finds.add(w)
				w = olString.indexOf(origQuote, w + 1)
			}
			w = finds[occur-1]
			println('testing for occurrence word at ' + w)
			occurUsed = true
		}
		
        if (w >= 0) {
        
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
	                testWordHighlight(sp, false)
	            } else {
	                testWordHighlight(sp, true)
	            }
	        }
        } else {
		
			msg = 'ERROR: Failed to find snippet in original language string'
			println(msg)
            println('olString:' + olString)
            println('origQuote:' + origQuote)
			oFile.append(msg)
			errors ++
        }
    } else {
        msg = 'Bypassed because there is no orignial language quote specified'

        println(msg)

        oFile.append(msg)
    }
}

def indexesOfAll(string,search) {
	indexes = []
	index = 0
	while (index >= 0) {
		index = string.indexOf(search, index)
		println(index)
		if (index >= 0) {
			indexes.add(index)
			index++
		}
	}
	return indexes
}
