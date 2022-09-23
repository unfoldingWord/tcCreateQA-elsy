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

println('>>>>> resource in tCC md Open for Edit is ' + resource)

WebUI.callTestCase(findTestCase('tCC Components/tCC Login'), [('user') : $username, ('password') : $password, ('newSession') : true], FailureHandling.STOP_ON_FAILURE)

if (binding.hasVariable('organization')) {
	myOrg = organization
} else {
	myOrg = GlobalVariable.organization
}
println('organization is ' + myOrg)

if (binding.hasVariable('language')) {
	myLanguage = language
} else {
	myLanguage = ''
}
println('language is ' + myLanguage)


println('>>>>> resource in tCC md Open for Edit after Login is ' + resource)

if (WebUI.callTestCase(findTestCase('tCC Components/tCC Select Org-Lang-Resource'), [('organization') : myOrg, ('language') : myLanguage
        , ('resource') : resource], FailureHandling.STOP_ON_FAILURE) == false) {
    KeywordUtil.markFailed('Exiting script because organization was not found..')
	CustomKeywords.'com.tccreate.keywords.selectOrg.checkBranchAlert'()
    WebUI.closeBrowser()

    return null
} else if (GlobalVariable.alertFlag == true) {
    if ($username == 'tc02') {
        println('Exiting script because of permissions alert as expected.')
    } else {
        KeywordUtil.markFailed('Exiting script because of unexpected permissions alert.')
    }
    
    WebUI.closeBrowser()

    return null
}

// THESE ELEMENTS SHOULD BE PARAMATERIZED TO MAKE IT MORE FLEXIBLE
//WebUI.click(findTestObject('Page_tC Create/span_translate'))

//WebUI.click(findTestObject('Page_tC Create/span_bita-humanbehavior'))

//WebUI.click(findTestObject('Page_tC Create/span_01.md'))

