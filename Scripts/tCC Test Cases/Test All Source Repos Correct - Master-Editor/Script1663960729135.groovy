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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

md_resources =
	[['en_ta', 'translate/', 'bita-humanbehavior/', '01.md'],
	['en_tw', 'bible/', 'kt/', 'adoption.md'],
	['en_tq', 'tq_TIT.tsv'],
	['en_obs', 'content/', '01.md'],
	['en_obs-tq', 'tq_OBS.tsv'],
	['en_obs-tn', 'tn_OBS.tsv'],
	['en_obs-sn', 'sn_OBS.tsv'],
	['en_obs-sq', 'sq_OBS.tsv']]


org = 'unfoldingWord®'
lang = 'en -'

//for (resource in md_resources) {
md_resources.each { resource ->
	
	println('Testing ' + resource)
	
	WebUI.callTestCase(findTestCase('tCC Components/tCC md Open For Edit'), [('$username') : '', ('$password') : '', ('organization') : org, ('language') : lang, ('resource') : resource], FailureHandling.STOP_ON_FAILURE)

	sourceRepo = WebUI.getText(findTestObject('Object Repository/Page_tC Create/chip_Source_Repo'))
	
	println('Source repo text is ' + sourceRepo)
	
	WebUI.click(findTestObject('Object Repository/Page_tC Create/chip_Source_Repo'))
	
	tC_Create_Window = WebUI.getWindowIndex()
	
	git_Window = tC_Create_Window + 1
	
	WebUI.switchToWindowIndex(git_Window)
	
	gitURL = WebUI.getUrl()
	
	myVersion = WebUI.getText(findTestObject('Object Repository/Page_Git Repo/text_Source_Version'))
	
	println('Displayed version is ' + myVersion)
	
	WebUI.click(findTestObject('Object Repository/Page_Git Repo/button_Releases'))
	
	latestRelease = WebUI.getText(findTestObject('Object Repository/Page_Git Repo/text_Last_Release'))
	
	println('Latest release is ' + latestRelease)
	
	repoExpected =  'unfoldingWord/' + resource[0]
	
	longRepo = repoExpected + '/src/branch/master/'
	
	if (!gitURL.contains(longRepo)) {		
		msg = '"' + longRepo + '" was not found in the source URL "' + gitURL + '".'
		println('ERROR: ' + msg)
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because ' + msg)
	}
	
	if (!sourceRepo.contains(repoExpected)) {
		msg = '"' + repoExpected + '" was not found in the source repo chip text "' + sourceRepo + '".'
		println('ERROR: ' + msg)
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because ' + msg)
	}
	
	if (myVersion.replaceAll("[^0-9]", "") != latestRelease.replaceAll("[^0-9]", "")) {
		msg = 'the source repo version "' + myVersion + '" is not the same as the latest release "' + latestRelease + '".'
		println('ERROR: ' + msg)
		CustomKeywords.'unfoldingWord_Keywords.SendMessage.SendFailMessage'('Test failed because ' + msg)
	}
	
//	return false
	
	WebUI.closeBrowser()
	
}

GlobalVariable.scriptRunning = false

