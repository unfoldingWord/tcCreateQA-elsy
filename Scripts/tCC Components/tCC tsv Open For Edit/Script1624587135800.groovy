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

//LOGIN AND OPEN THE en-tN FOR EDITING
// 02/10/21	Replaced the try/catch with a test for length of the 'file' parameter (the 'file' parameter is effectively optional)
// 03/04/21 Modified to make language an option input parameter

if (binding.hasVariable('file') && file.contains('.tsv')) {
	myFile = file
} else {
	myFile = GlobalVariable.tNFile
}
println('tsv file is ' + myFile)

if (binding.hasVariable('language')) {
	myLanguage = language
} else {
	myLanguage = ''
}
println('language is ' + myLanguage)

if (binding.hasVariable('organization')) {
	myOrg = organization
} else {
	myOrg = ''
}
println('language is ' + myLanguage)

if (binding.hasVariable('resource')) {
	myResource = resource
} else {
	myResource = '/en_tn'
}
println('language is ' + myLanguage)

WebUI.callTestCase(findTestCase('tCC Components/tCC Login'), [('user') : $username, ('password') : $password, ('newSession') : true], FailureHandling.STOP_ON_FAILURE)

if (WebUI.callTestCase(findTestCase('tCC Components/tCC Select Org-Lang-Resource'), [('organization') : myOrg, ('language') : myLanguage, ('file') : myFile,
         ('resource') : myResource], FailureHandling.STOP_ON_FAILURE) == false) {
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

//println('file is [' + file + ']')
//println('length is [' + file.length() + ']')
WebUI.scrollToElement(findTestObject('Page_tC Create/file_Parmed', [('fileName') : myFile]), 1)

WebUI.click(findTestObject('Page_tC Create/file_Parmed', [('fileName') : myFile]))

//if (file.length() > 0 && file.contains('.tsv')) {
//	myFile = file
//	WebUI.click(findTestObject('Page_tC Create/file_Parmed', [('fileName') : myFile]))
//} else {
//	WebUI.click(findTestObject('Page_tC Create/file_Parmed', [('fileName') : GlobalVariable.tNFile]))
//}

