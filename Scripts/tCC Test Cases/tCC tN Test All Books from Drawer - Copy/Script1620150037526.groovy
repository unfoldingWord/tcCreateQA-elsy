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

WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [('$username') : '', ('$password') : '', ('file') : ''], 
    FailureHandling.STOP_ON_FAILURE)

if (1 == 2) {
    numStr = ''

    myFile.each({ def a ->
            if (a.isNumber()) {
                numStr += a
            }
        })

    num = numStr.toInteger()

    if (num < 40) {
        OT = true
    } else {
        OT = false
    }
}

books = []

new File('/Users/' + GlobalVariable.pcUser + '/Katalon Studio/Files/Reference/Bible_Books.csv').splitEachLine(',', { def fields ->
//new File('/Users/' + GlobalVariable.pcUser + '/Katalon Studio/Files/Reference/NT_Books.csv').splitEachLine(',', { def fields ->
        bookNum = (fields[0])

        if (bookNum.length() < 2) {
            bookNum = ('0' + bookNum)
        }
        
        bookAbrv = (fields[1])

        bookName = (((('en_tn_' + bookNum) + '-') + bookAbrv) + '.tsv')

        books.add(bookName)
    })

println(books)

for (def book : books) {
    println('Opening ' + book)

    CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.chooseFile'(book)

    WebUI.delay(2)
}

GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

