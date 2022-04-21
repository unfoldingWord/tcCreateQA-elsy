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
import org.openqa.selenium.Keys as Keys
import org.sikuli.script.*

if (1 == 1) {
    (width, height) = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getScreenResolution'()

    int xOffset = (width / 2) - 200

    println('xOffset is ' + xOffset)

    int yOffset = (-(height) / 2) + 100

    println('yOffset is ' + yOffset)

    WebUI.openBrowser('https://git.door43.org/unfoldingWord/en_tn/raw/branch/master/en_tn_57-TIT.tsv')

    WebUI.maximizeWindow()

    WebUI.rightClickOffset(findTestObject('recordings/pre_RawText'), xOffset, yOffset)

    WebUI.delay(1)

    Screen s = new Screen()
	
	if (s.exists('/Users/cckozie/git/Katalon_tC_Create/Images/SaveAsButton.png', 5)) {
        s.click()
    }
    
    if (s.exists('/Users/cckozie/git/Katalon_tC_Create/Images/SaveButton.png', 5)) {
        WebUI.delay(1)

        s.click()
    }
    
    if (s.exists('/Users/cckozie/git/Katalon_tC_Create/Images/ReplaceButton.png', 5)) {
        WebUI.delay(1)

        s.click()
    }
    
    new File('/Users/cckozie/Downloads/en_tn_57-TIT.tsv').eachLine({ def line ->
            println(line)
        })
} else {

	ids = []
	
	origQuotes = []
	
	first = true
	
	new File('/Users/cckozie/Downloads/en_tn_57-TIT.tsv').splitEachLine('\t', { def fields ->
	        if ((fields[4]) != '') {
	            if (!(first)) {
	                println(((fields[3]) + ':') + (fields[5]))
	
	                ids.add(fields[3])
	
	                origQuotes.add(fields[5])
	            } else {
	                first = false
	            }
	        }
	    } //Read data from CSV
	    )
	
	//println(var1)
	return false
	
	
	//CustomKeywords.'unfoldingWord_Keywords.SikuliFunctions.fileSaveAs'()
	
	//WebUI.clickOffset(findTestObject('recordings/pre_RawText'), xOffset + 50, yOffset + 80)
	WebUI.delay(1)
	
	return false
	
	WebUI.callTestCase(findTestCase('tCC Components/tCC tN Open For Edit'), [:], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.click(findTestObject('Object Repository/recordings/span_English - unfoldingWorden_tnmaster'))
	
	WebUI.switchToWindowTitle('unfoldingWord/en_tn: unfoldingWord® Translation Notes - en_tn_57-TIT.tsv at master - en_tn - Door43 Content Service')
	
	WebUI.click(findTestObject('Object Repository/recordings/a_Raw'))
	
	WebUI.rightClickOffset(findTestObject('recordings/pre_RawText'), xOffset, yOffset)
	
	WebUI.delay(1)
	
	//WebUI.clickOffset(findTestObject('recordings/pre_RawText'), xOffset + 50, yOffset + 80)
	WebUI.clickOffset(findTestObject('recordings/pre_RawText'), xOffset + 50, yOffset + 80)
	
}