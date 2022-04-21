package unfoldingWord_Keywords

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

import internal.GlobalVariable


public class HamburgerFunctions_SAVE {
	@Keyword
	// Call with CustomKeywords.'unfoldingWord_Keywords.HamburgerFunctions.toggleAllScripture'(parm) where parm in('on','off,'test')
	// If parm = 'test', returns 'on' or 'off'
	def toggleAllScripture(toState) {
		def newState = toState.toLowerCase()
		def myState = ''
		def drawerOpen = WebUI.waitForElementPresent(findTestObject('Page_tC Create/button_DrawerClose'), 1)
		if (!drawerOpen) {
			try {
				WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
			} catch (Exception e) {
				WebUI.scrollToElement(findTestObject('Page_tCC translationAcademy/chip_Repo'), 2)
				WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
			}
		}
		WebUI.delay(1)
		def state = WebUI.verifyElementChecked(findTestObject('Page_tC Create/input_Expand all Scripture'), 2,FailureHandling.OPTIONAL)
		if (state) {
			myState = 'on'
		} else {
			myState = 'off'
		}
		println('Expand all Scripture switch is ' + myState)
		if (newState != 'test') {
			if (myState != newState) {
				println('Toggling scripture pane ' + newState)
				WebUI.click(findTestObject('Page_tC Create/input_Expand all Scripture'))
				WebUI.delay(1)
				if (WebUI.verifyElementChecked(findTestObject('Page_tC Create/input_Expand all Scripture'), 2,FailureHandling.OPTIONAL)) {
					myState = 'on'
				} else {
					myState = 'off'
				}
			}
		}
		if (!drawerOpen) {
			WebUI.click(findTestObject('Page_tC Create/button_DrawerClose'))
			WebUI.waitForElementVisible(findTestObject('Page_tC Create/button_DrawerOpen'), 5)
		}
		return myState
	}

	@Keyword
	def chooseFile(name) {
		def retCode = true
		def drawerOpen = WebUI.waitForElementPresent(findTestObject('Page_tC Create/button_DrawerClose'), 1, FailureHandling.OPTIONAL)
		if (!drawerOpen) {
			try {
				WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
			} catch (Exception e) {
				println('verifying chip_repo present')
				if (!WebUI.verifyElementPresent(findTestObject('Page_tCC translationAcademy/chip_Repo'), 2, FailureHandling.OPTIONAL)) {
					println('not present')
					retCode = false
				} else {
					println('attempting to scroll to chip_repo')
					if (!WebUI.scrollToElement(findTestObject('Page_tCC translationAcademy/chip_Repo'), 2, FailureHandling.OPTIONAL)) {
						println('cannot scroll to chip_repo')
						retCode = false
					}
				}
				if (retCode) {
					WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
				}
			}
		}
		if (retCode) {
			WebUI.delay(2)
			WebUI.click(findTestObject('Page_tC Create/file_Parmed', [('fileName') : name]))
			//		WebUI.click(findTestObject('Page_tC Create/file_Parmed'))

			if (!drawerOpen) {
				if (WebUI.waitForElementVisible(findTestObject('Page_tC Create/button_DrawerClose'),2, FailureHandling.OPTIONAL)) {
					WebUI.click(findTestObject('Page_tC Create/button_DrawerClose'))
					WebUI.waitForElementVisible(findTestObject('Page_tC Create/button_DrawerOpen'), 5)
				} else {
					retCode = false
				}
			}
		}
		println('returning ' + retCode)
		return retCode
	}

	@Keyword
	def chooseValidationLevel(level) {
		def retCode = true
		def drawerOpen = WebUI.waitForElementPresent(findTestObject('Page_tC Create/button_DrawerClose'), 1, FailureHandling.OPTIONAL)
		if (!drawerOpen) {
			try {
				WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
			} catch (Exception e) {
				WebUI.scrollToElement(findTestObject('Page_tCC translationAcademy/chip_Repo'), 2)
				WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
			}
		}
		WebUI.delay(1)

		if (level.toLowerCase().contains('hi')) {
			WebUI.click(findTestObject('Object Repository/Page_tC Create/radio_ValidationLevel_High'))
		} else if (level.toLowerCase().contains('me')) {
			WebUI.click(findTestObject('Object Repository/Page_tC Create/radio_ValidationLevel_Medium'))
		} else 	if (level.toLowerCase().contains('lo')) {
			WebUI.click(findTestObject('Object Repository/Page_tC Create/radio_ValidationLevel_Low'))
		}

		if (!drawerOpen) {
			if (WebUI.waitForElementVisible(findTestObject('Page_tC Create/button_DrawerClose'),2)) {
				WebUI.click(findTestObject('Page_tC Create/button_DrawerClose'))
				WebUI.waitForElementVisible(findTestObject('Page_tC Create/button_DrawerOpen'), 5)
			} else {
				retCode = false
			}
		}
		println('returning ' + retCode)
		return retCode

	}

	@Keyword
	def testValidationLevel(level) {
		def retCode = true
		def drawerOpen = WebUI.waitForElementPresent(findTestObject('Page_tC Create/button_DrawerClose'), 1, FailureHandling.OPTIONAL)
		if (!drawerOpen) {
			try {
				WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
			} catch (Exception e) {
				WebUI.scrollToElement(findTestObject('Page_tCC translationAcademy/chip_Repo'), 2)
				WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
			}
		}
		WebUI.delay(1)

		if (level.toLowerCase().contains('hi')) {
			level = 'High'
		} else if (level.toLowerCase().contains('me')) {
			level = 'Medium'
		} else 	if (level.toLowerCase().contains('lo') || level.toLowerCase().contains('all')) {
			level = 'Low'
		}

		if (WebUI.verifyElementChecked(findTestObject('Object Repository/Page_tC Create/radio_ValidationLevel_' + level), 1, FailureHandling.STOP_ON_FAILURE)) {
			retCode = true
		} else 	{
			retCode = false
		}

		if (!drawerOpen) {
			if (WebUI.waitForElementVisible(findTestObject('Page_tC Create/button_DrawerClose'),2)) {
				WebUI.click(findTestObject('Page_tC Create/button_DrawerClose'))
				WebUI.waitForElementVisible(findTestObject('Page_tC Create/button_DrawerOpen'), 5)
			} else {
				retCode = false
			}
		}
		println('returning ' + retCode)
		return retCode

	}

}