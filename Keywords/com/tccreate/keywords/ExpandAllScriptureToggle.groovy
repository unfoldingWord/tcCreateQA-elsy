package com.tccreate.keywords

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


public class ExpandAllScriptureToggle {
	@Keyword

	def toggleAllScripture(toState) {
		def newState = toState.toLowerCase()
		def myState = ''
		def drawerOpen = WebUI.waitForElementPresent(findTestObject('Object Repository/Page_tC Create/button_DrawerClose') ,2, FailureHandling.OPTIONAL)
		if (!drawerOpen) {
			try {
				WebUI.click(findTestObject('Object Repository/Page_tC Create/Hamburger Menu Button'), FailureHandling.STOP_ON_FAILURE)
			} catch (Exception e) {
				WebUI.scrollToElement(findTestObject('Object Repository/Page_tC Create/chip_Repo'), 2)
				WebUI.click(findTestObject('Page_tC Create/button_DrawerOpen'), FailureHandling.STOP_ON_FAILURE)
			}
		}

		WebUI.delay(1)

		def state = WebUI.verifyElementChecked(findTestObject('Object Repository/Page_tC Create/input_Expand all Scripture_default'), 2,FailureHandling.OPTIONAL)
		if (state) {
			myState = 'on'
		} else {
			myState = 'off'
		}
		println('Expand all Scripture switch is ' + myState)
		if (newState != 'test') {
			if (myState != newState) {
				println('Toggling scripture pane ' + newState)
				WebUI.click(findTestObject('Object Repository/Page_tC Create/input_Expand all Scripture_default'))
				WebUI.delay(1)
			}
		}

		if (!drawerOpen) {
			WebUI.click(findTestObject('Page_tC Create/button_DrawerClose'))
			WebUI.waitForElementVisible(findTestObject('Object Repository/Page_tC Create/Hamburger Menu Button'), 5)
		}
		return myState
	}
}
