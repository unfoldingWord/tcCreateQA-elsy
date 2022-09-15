import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.configuration.RunConfiguration as RC
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.io.File as File

class Test_Case_Setup {
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		println('setup start')
		GlobalVariable.systemOS = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getOperatingSystem'()
		GlobalVariable.tcMessages = []
		def executionProfile = RC.getExecutionProfile()
		KeywordUtil.logInfo('Execution profile is ' + executionProfile)
		println('Execution profile is ' + executionProfile)
		if (GlobalVariable.systemOS == '' || GlobalVariable.systemOS == null) {
			GlobalVariable.systemOS = CustomKeywords.'unfoldingWord_Keywords.GetTestingConfig.getOperatingSystem'()
		}
		if (GlobalVariable.pcUser == '' || GlobalVariable.pcUser == null) {
			def dirName = RC.getProjectDir()
			println('Project path is ' + dirName)
			GlobalVariable.projectPath = dirName
			def loc = dirName.indexOf('/Users/')
			def git = dirName.indexOf('/git')
			def user = dirName.substring(loc+7,git)
			GlobalVariable.pcUser = user
		}
		GlobalVariable.scriptRunning = true
		println('setup success')
	}
}

