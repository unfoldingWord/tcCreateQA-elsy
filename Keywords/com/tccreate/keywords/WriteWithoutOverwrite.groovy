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
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import internal.GlobalVariable
//import java.sql.Timestamp;
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

public class WriteWithoutOverwrite {
	@Keyword
	def buildVersion(String file_name) {
		try{
			FileWriter fstream = new FileWriter(file_name,true);
			BufferedWriter out = new BufferedWriter(fstream);
			PrintWriter write = new PrintWriter(out);

			KeywordUtil.logInfo("  Line Added on: " + new java.util.Date()+"\n");
			def version= WebUI.getText(findTestObject('Object Repository/Page_tC Create/tcc-version number'))
			KeywordUtil.logInfo(": The current version and build number is " + version + "\n")


			out.close();
		}catch (Exception e){
			System.err.println("Error while writing to file: " +
					e.getMessage());
		}
	}
}
