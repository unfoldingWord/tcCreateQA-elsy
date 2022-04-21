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

// SELECT THE ORGANIZATION, LANGUAGE, AND RESOURCE TO BE TESTED

// 03/01/21	Modified to allow selecting md files by drilling down in file path when 'resource' is a List

//INPUT PARAMETERS - 
//	- organization (if blank will use the GlobalVariable.oganization)
//	- resource (string or list)
//	- language	(if blank will use the GlobalVariable.langCode)

println('resource is ' + resource)

if (organization == "") {
	organization = GlobalVariable.organization
}

if (language == '') {
	language = GlobalVariable.langCode
}

println('Choosing organization ' + organization)

if (WebUI.waitForElementPresent(organization, 5, FailureHandling.OPTIONAL) == false) {
	println('Failed to find organization option. Suspect infinite spinner')

	WebUI.takeScreenshot()

	return false
} else {
	WebUI.click(organization)

	alerted = WebUI.waitForAlert(2)

	if (alerted == true) {
		println('Alerted on Organization')

		GlobalVariable.alertFlag = true
	} else {
		println('Not alerted on organization')

		GlobalVariable.alertFlag = false
		
		if (resource instanceof List) {
			listFlag = true
			resources = resource
			resource = resources[0]
		} else {
			listFlag = false
		}
		
		if (resource.indexOf('/') == 0) {
			resource = resource.substring(1, resource.length())
		}
		rsrc = 'unfoldingWord/' + resource
		if (WebUI.verifyElementPresent(findTestObject('Page_tC Create/resource_Parmed', [('resource') : rsrc]), 1, FailureHandling.OPTIONAL)) {
			resource = rsrc
		} else {
			resource = 'Door43-Catalog' + resource
		}
		
		println('Choosing resource ' + resource)

		WebUI.click(findTestObject('Page_tC Create/resource_Parmed', [('resource') : resource]))

		println('Choosing language ' + language)

		WebUI.click(findTestObject('Page_tC Create/combo_Select Language'))

		WebUI.click(findTestObject('Page_tC Create/listOption_Language_Parmed', [('lang_code') : language]))
		
		if (listFlag) {
			for (def r : (1..resources.size()-1)) {
				println('Choosing ' + resources[r])
				WebUI.click(findTestObject('Page_tC Create/resource_Parmed', [('resource') : resources[r]]))
			}
		}
	}
	
	return true
}

