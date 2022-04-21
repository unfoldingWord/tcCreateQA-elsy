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

// 03/01/21 Modified to test for scripture links causing a 404 error (issue 679) and call tCC md Open For Edit instead of tCC tA Open For Edit

resource = ['unfoldingWord/en_ta', 'translate/', 'bita-humanbehavior/', '01.md']

WebUI.callTestCase(findTestCase('tCC Components/tCC md Open For Edit'), [('$username') : '', ('$password') : '', ('resource') : resource], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Page_tCC translationAcademy/section_BentOver'))

WebUI.click(findTestObject('Page_tCC translationAcademy/text_BentOver'))

WebUI.click(findTestObject('Page_tCC translationAcademy/header_BentOver'))

if (!(WebUI.getText(findTestObject('Page_tCC translationAcademy/text_BentOver')).contains('Yahweh'))) {
    println('ERROR: Block quote text was deleted')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because block quote text was deleted.')
} else {
    println('Block quote text is correct')
}

if (CustomKeywords.'unfoldingWord_Keywords.TestVersion.isVersionGreater'('1.0.4')) {
    WebUI.click(findTestObject('Page_tC Create/button_Preview'))

    WebUI.click(findTestObject('Page_tCC translationAcademy/section_Having BIRTH PAINS'))

    elementBirthPains = 'Page_tCC translationAcademy/blockquote_Be in pain and labor' //text_Having Birth Pains header'

    WebUI.sendKeys(findTestObject(elementBirthPains), Keys.chord(Keys.COMMAND, Keys.DOWN))

    WebUI.sendKeys(findTestObject(elementBirthPains), Keys.chord(Keys.SPACE))

    WebUI.delay(1)

    if (WebUI.getText(findTestObject(elementBirthPains)).contains('&gt;')) {
        println('ERROR: "&gt;" was displayed instead of ">" for block quotes')

        CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because "&gt;" was displayed instead of ">" for block quotes.')

        WebUI.click(findTestObject('Page_tC Create/button_Preview'))

        WebUI.scrollToElement(findTestObject('Page_tCC translationAcademy/chip_Repo'), 2)

        WebUI.delay(2)
    } else {
        try {
            WebUI.sendKeys(findTestObject(elementBirthPains), Keys.chord(Keys.ENTER), FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.sendKeys(findTestObject(elementBirthPains), '> This is line 1', FailureHandling.STOP_ON_FAILURE)

            WebUI.sendKeys(findTestObject(elementBirthPains), Keys.chord(Keys.ENTER), FailureHandling.CONTINUE_ON_FAILURE)

            WebUI.sendKeys(findTestObject(elementBirthPains), '> This is line 2', FailureHandling.CONTINUE_ON_FAILURE)
        }
        catch (Exception e) {
            println('ERROR: Failed to send keystrokes to the block')

            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because script was unable to send keystrokes to the block.')
        } 
		
		WebUI.scrollToPosition(0, 0)
        
        WebUI.click(findTestObject('Page_tC Create/button_preview'))

        WebUI.scrollToElement(findTestObject('Page_tCC translationAcademy/paragraph_My little children'), 2)
		
		bText = WebUI.getText(findTestObject('Page_tCC translationAcademy/paragraph_My little children'))
		
		lf = (char)10
		
		eText = 'ULT)' + lf + 'This is line 1' + lf + 'This is line 2'
		
//		println(bText)
		
//		println(eText)		

        if (!bText.contains(eText)) {
            println('ERROR: line feeds were deleted in block quotes')
			println('Text is ' + bText)
            CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because line feeds were deleted in block quotes.')
        } else {
            println('Line feeds were correctly included in block quotes')
        }
    }
} else {
    println('Test for block quote line feeds was bypassed')
}

println('Testing for sections visible and invisible')

println('Showing sections')

WebUI.delay(1)

WebUI.click(findTestObject('Page_tCC translationAcademy/button_Sections'))

if (!(WebUI.verifyElementVisible(findTestObject('Page_tCC translationAcademy/text_BentOver')))) {
    println('ERROR: tA text for "Bent over" is not visible when sections are open')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tA text for "Bent over" is not visible when sections are open.')
} else {
    println('tA text for "Bent over" is visible when sections are open')
}

if (!(WebUI.verifyElementVisible(findTestObject('Page_tCC translationAcademy/text_Be in pain and labor to give birth')))) {
    println('ERROR: tA text for "Be in pain and labor" is not visible when sections are open')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tA text for "Be in pain and labor" is not visible when sections are open.')
} else {
    println('tA text for "Be in pain and labor" is visible when sections are open')
}

println('Hiding sections')

WebUI.scrollToPosition(0, 0)

WebUI.delay(2)

WebUI.click(findTestObject('Page_tCC translationAcademy/button_Sections'))

if (!(WebUI.verifyElementNotVisible(findTestObject('Page_tCC translationAcademy/text_BentOver')))) {
    println('ERROR: tA text for "Bent over" is visible when sections are closed')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tA text for "Bent over" is visible when sections are closed.')
} else {
    println('tA text for "Bent over" is not visible when sections are closed as expected')
}

present = WebUI.verifyElementPresent(findTestObject('Page_tCC translationAcademy/text_Be in pain and labor to give birth'), 
    1, FailureHandling.OPTIONAL)

if (present && !(WebUI.verifyElementNotVisible(findTestObject('Page_tCC translationAcademy/text_Be in pain and labor to give birth'), 
    FailureHandling.OPTIONAL))) {
    println('ERROR: tA text for "Be in pain and labor" is visible when sections are closed')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tA text for "Be in pain and labor" is visible when sections are closed.')
} else {
    println('tA text for "Be in pain and labor" is not visible when sections are closed as expected')
}

println('Testing for sections and blocks visible')

println('Showing sections and blocks')

WebUI.click(findTestObject('Page_tCC translationAcademy/button_Sections'))

WebUI.click(findTestObject('Page_tCC translationAcademy/button_Blocks'))

WebUI.delay(1)

if (!(WebUI.waitForElementVisible(findTestObject('Page_tCC translationAcademy/text_BentOver'), 1, FailureHandling.OPTIONAL)) || 
!(WebUI.waitForElementVisible(findTestObject('Page_tCC translationAcademy/text_Be in pain and labor to give birth'), 1, 
    FailureHandling.OPTIONAL))) {
    println('ERROR: tA text is not visible when Sections and Blocks have are opened')

    CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because tA text is not visible when Sections and Blocks are open.')
} else {
    println('tA text is visible with Sections and Blocks open as expected')
}

// Test that links don't cause a 404 error
WebUI.click(findTestObject('Page_tCC translationAcademy/button_Sections'))

WebUI.click(findTestObject('Page_tCC translationAcademy/button_Blocks'))

WebUI.delay(1)

WebUI.scrollToPosition(0, 0)

WebUI.clickOffset(findTestObject('Page_tCC translationAcademy/link_metonymies'), 0, 0)

//WebUI.click(findTestObject('Page_tCC translationAcademy/link_metonymies'))

WebUI.delay(2)

if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/Page_tC Create/p_Some common metonymies'), 1, FailureHandling.OPTIONAL) ||
		WebUI.verifyElementPresent(findTestObject('Page_tCC translationAcademy/h1_Page Not Found'), 1, FailureHandling.OPTIONAL)) {
	println('ERROR: Clicking on a scripture link in tA causes a 404 error')
	
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because clicking on a scripture link in tA causes a 404 error.')
	
	WebUI.closeBrowser()
	
	return false
}

WebUI.closeBrowser()

resource = ['unfoldingWord/en_obs-sn', 'content/', '01/', '01.md']

WebUI.callTestCase(findTestCase('tCC Components/tCC md Open For Edit'), [('$username') : '', ('$password') : '', ('resource') : resource], FailureHandling.STOP_ON_FAILURE)

header = "God’s Spirit"

WebUI.scrollToElement(findTestObject('Object Repository/Page_tC Create/header_mdParmed', [('header') : header]) ,1)

WebUI.click(findTestObject('Object Repository/Page_tC Create/header_mdParmed', [('header') : header]))

WebUI.clickOffset(findTestObject('Page_tC Create/link_scripture_24_08'), 0, 0)

WebUI.delay(2)

if ((!WebUI.verifyElementPresent(findTestObject('Page_tC Create/link_scripture_24_08'), 1, FailureHandling.OPTIONAL)) ||
	WebUI.verifyElementPresent(findTestObject('Page_tCC translationAcademy/h1_Page Not Found'), 1, FailureHandling.OPTIONAL)) {

	println('ERROR: Clicking on a scripture link in OBS-sn causes a 404 error')
	
	CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because clicking on a scripture link to in OBS-sn causes a 404 error.')
}
GlobalVariable.scriptRunning = false

WebUI.closeBrowser()

