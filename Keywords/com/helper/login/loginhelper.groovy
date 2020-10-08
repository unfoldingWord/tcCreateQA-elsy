package com.helper.login

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

import internal.GlobalVariable

public class loginhelper {
	@Keyword
	public void loginApp(String url, String Username, String Password) {
		WebUI.openBrowser(url)
		WebUI.waitForPageLoad(3)
		WebUI.maximizeWindow()
		WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/input__username'), Username)
		WebUI.sendKeys(findTestObject('Object Repository/Page_tC Create/input__password'), Password)
		WebUI.click(findTestObject('Object Repository/Page_tC Create/button_Login'))
		WebUI.delay(1)
	}
	@Keyword
	public void logintoapp() {
		loginApp(GlobalVariable.url, GlobalVariable.user1Name, GlobalVariable.user1Password)
	}
}

