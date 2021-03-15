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
import org.openqa.selenium.Keys as Keys

import internal.GlobalVariable

public class Hotkeys {
	@Keyword

	def sendKeys(element,function) {
		// Supported functions: copy, paste, all

		function = function. toLowerCase()

		if (GlobalVariable.browser == '' || GlobalVariable.browser == null) {
			GlobalVariable.browser = GetTestingConfig.getBrowserAndVersion()
		}

		if (element == 'null') {
			element = null
		}

		println('sendKeys to ' + element + ' in ' + GlobalVariable.browser + ' on ' + GlobalVariable.systemOS)

		if (GlobalVariable.systemOS.contains('Windows')) {
			if (function == 'copy') {
				WebUI.sendKeys(findTestObject(element), Keys.chord(Keys.CONTROL, 'c'))
			} else if (function == 'paste') {
				WebUI.sendKeys(findTestObject(element), Keys.chord(Keys.CONTROL, 'v'))
			}else if (function.contains('all')) {
				WebUI.sendKeys(findTestObject(element), Keys.chord(Keys.CONTROL, 'a'))
			}
		} else if (GlobalVariable.systemOS.contains('Mac')) {
			if (function.contains('all')) {
				//					Object cmd = Keys.TAB
				WebUI.sendKeys(findTestObject(element), Keys.chord(Keys.COMMAND, 'a'))
			} else {
				if (GlobalVariable.browser.contains('firefox')) {
					if (function == 'copy') {
						WebUI.sendKeys(findTestObject(element), Keys.chord(Keys.COMMAND, 'c'))
					} else if (function == 'paste') {
						WebUI.sendKeys(findTestObject(element), Keys.chord(Keys.COMMAND, 'v'))
					}
				} else if (GlobalVariable.browser.contains('chrome')) {
					if (function == 'copy') {
						WebUI.sendKeys(findTestObject(element), Keys.chord(Keys.CONTROL, Keys.INSERT))
					} else if (function == 'paste') {
						WebUI.sendKeys(findTestObject(element), Keys.chord(Keys.SHIFT, Keys.INSERT))
					}				}
			}
		}
	}

}
