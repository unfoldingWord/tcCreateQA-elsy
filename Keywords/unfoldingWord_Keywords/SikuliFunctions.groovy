package unfoldingWord_Keywords
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable

//import MobileBuiltInKeywords as Mobile
//import WSBuiltInKeywords as WS
//import WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException

import org.sikuli.script.*


class SikuliFunctions {
	@Keyword
	def fileSaveAs () {
		Screen s = new Screen()
		//		def imagePath = '/Users/cckozie/git/Katalon/tC Create Project/Images/'
		def Pattern saveAs = New Pattern('/Users/cckozie/git/Katalon/tC Create Project/Images/SaveAsButton.png')
		def Pattern save = New Pattern('/Users/cckozie/git/Katalon/tC Create Project/Images/SaveButton.png')
		def Pattern replace = New Pattern('/Users/cckozie/git/Katalon/tC Create Project/Images/ReplaceButton.png')
		//		String tCCName = '/Users/cckozie/Katalon Studio/Sikuli Images/tCC_Name.png'
		//		Pattern name = new Pattern(tCCName)
		s.click(saveAs)
		wait(3)
		//		image = Pattern(imagePath+'SaveButton.png')
		s.click(save)
		//		image = Pattern(imagePath+'ReplaceButton.png')
		//		if (s.exists(return)) {
		//			s.click()
		//		}
	}
}