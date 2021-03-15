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

import internal.GlobalVariable

public class selectOrg {
	@Keyword
	def organization(String organization) {
		if (organization == '') {
			organization = GlobalVariable.organization
		}
		println('Choosing organization ' + organization)

		WebUI.click(findTestObject('Page_tC Create/listOption_organisation_Parmed', [('org_code') : organization]))

		def alerted = WebUI.waitForAlert(2)

		if (alerted == true) {
			println('Alerted on Organization')

			GlobalVariable.alertFlag = true
		} else {
			println('Not alerted on organization')

			GlobalVariable.alertFlag = false
		}
	}
	@Keyword
	def language(String language) {
		if (language == '') {
			language = GlobalVariable.langCode
		}
		println('Choosing language ' + language)

		WebUI.click(findTestObject('Page_tC Create/combo_Select Language'))

		WebUI.click(findTestObject('Page_tC Create/listOption_Language_Parmed', [('lang_code') : language]))
	}
	@Keyword
	def resource(String resource) {
		println('Choosing resource ' + resource)
		WebUI.click(findTestObject('Page_tC Create/resource_Parmed', [('resource') : resource]))

		//WebUI.click(findTestObject('Page_tC Create/resource_Parmed', resource))
	}
}
