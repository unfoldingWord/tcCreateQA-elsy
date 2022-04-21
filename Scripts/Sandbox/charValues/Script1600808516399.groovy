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
import com.kms.katalon.core.configuration.RunConfiguration as RC
import java.io.File

myFile = 'en_tn_08-RUT.tsv'
println(myFile)
//myFile.each {a -> 
//	println a
//}

return false

puncts = [',',';',':','"','?','.']

s1 = 'וַ⁠יָּ֥מָת אֱלִימֶ֖לֶךְ אִ֣ישׁ נָעֳמִ֑י וַ⁠תִּשָּׁאֵ֥ר הִ֖יא וּ⁠שְׁנֵ֥י בָנֶֽי⁠הָ׃'
s2 = 'הִ֖יא וּ⁠שְׁנֵ֥י בָנֶֽי⁠הָ׃'
//s1 = 'וַ⁠יָּ֥מָת אֱלִימֶ֖לֶךְ אִ֣ישׁ נָעֳמִ֑י וַ⁠תִּשָּׁאֵ֥ר הִ֖יא וּ⁠שְׁנֵ֥י'
//s2 = 'הִ֖יא וּ⁠שְׁנֵ֥י'
for (punct in puncts) {
	s1 = s1.replace(punct,'')
	s2 = s2.replace(punct,'')
}
println(s1)
println(s2)
s3 = s1.indexOf(s2)
println(s3)

return false
myFile = 'en_tn_41-MAT.tsv'
fName = 'test.txt'
highlighted = 'rgba(255, 255, 0, 1)'
details = true
errors = 0
stopOnError = true
maxErrors = 0

oFile = new File('/Users/cckozie/Katalon Studio/Files/' + fName)

oFile.delete()


//origString = "Ἀβραὰμ ἐγέννησεν τὸν Ἰσαάκ, Ἰσαὰκ δὲ ἐγέννησεν τὸν Ἰακώβ, Ἰακὼβ δὲ ἐγέννησεν τὸν Ἰούδαν καὶ τοὺς ἀδελφοὺς αὐτοῦ,"
//origQuote = "Ἰσαὰκ…ἐγέννησεν…Ἰακὼβ…ἐγέννησεν"
origString = 'Ἰούδας δὲ ἐγέννησεν τὸν Φαρὲς καὶ τὸν Ζάρα ἐκ τῆς Θαμάρ, Φαρὲς δὲ ἐγέννησεν τὸν Ἑσρώμ, Ἑσρὼμ δὲ ἐγέννησεν τὸν Ἀράμ'
//origQuote = 'Φαρὲς…Ζάρα…Ἑσρώμ…Ἀράμ'
origQuote = 'Φαρὲς…ἐγέννησεν…Ἑσρὼμ…ἐγέννησεν'

bgColors = ['rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(255, 255, 0, 1)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(255, 255, 0, 1)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(255, 255, 0, 1)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(0, 0, 0, 0)', 'rgba(255, 255, 0, 1)']

elips = '…'

olString = origString.replace(',','')
olString = origString.replace(';','')
olString = origString.replace('"','')

words = []
words = (origQuote).split('…| ')
println('words:' + words)

spans = []
spans = origString.split(' ')
println('spans:' + spans)

ellipsis = (origQuote).contains(elips)

elipsCount = origQuote.count(elips)

el = 1

while ( el <= elipsCount) {
    msg = '\n===== Processing check with ellipsis=====\n'
	
    println(msg)

    oFile.append(msg)

	println(olString)

    olStringSave = olString

    //Find quote words before the elipsis
    elpLoc = origQuote.indexOf(elips)

    preWords = origQuote.substring(0, elpLoc)
	
	preWordsCount = preWords.count(' ') + 1

    println('preWords:' + preWords)
	
    //Find quote words after the elipsis
	if (el < elipsCount) {
		nextElp = origQuote.indexOf(elips, elpLoc+1)
		postWords = origQuote.substring(elpLoc + 1, nextElp)
	} else {
		postWords = origQuote.substring(elpLoc + 1)
	}

    println('postWords:' + postWords)
	
	//Find the instance of prewords closest to the postwords
	preLocs = indexesOfAll(olString,preWords)
	
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
	//println('loc:' + loc)
	
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

		oFile.append(msg)
		
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

		oFile.append(msg)
		
		println(msg)
		
        testWordHighlight(i2, true)

        i2++
    }
    
    //The string beginning after the ellipsis is processed as strings without ellipsis
    spansSave = spans

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

//	ellipsis = (origQuote).contains(elips)
	el ++

}

def testWordHighlight(def sp, def state) {
	if (!(state)) {
		if ((bgColors[sp]) == highlighted) {
			msg = (wordStr + ' is highighted but should not be\n')

			println('++++++++++ ' + msg)

			oFile.append(msg)

			errors++

			if (stopOnError && (errors > maxErrors)) {
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
				print(wordStr + ' is highighted as expected\n')
			}
		} else {
			msg = (wordStr + ' is not highlighted but should be\n')

			println('---------- ' + msg)

			println('Background color is ' + (bgColors[sp]))

			oFile.append(msg)

			errors++

			if (stopOnError && (errors > maxErrors)) {
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
	}
	
	if (origQuote.length() > 0) {
		w = olString.indexOf(origQuote)

		if (w < 0) {
			println('olString:' + olString)

			println('origQuote:' + origQuote)
		}
		
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