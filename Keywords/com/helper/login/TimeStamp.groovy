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
import groovy.time.TimeCategory as TimeCategory
import internal.GlobalVariable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import groovy.transform.CompileStatic
import org.apache.commons.lang3.time.DateUtils
import java.time.ZoneId
import com.kms.katalon.core.util.KeywordUtil

public class TimeStamp {
	@CompileStatic
	@Keyword
	public static String getCurrentDateTime() {
		String timeZoneId= 'EST'
		String dateTimeFormat= ''
		DateFormat format = setDateFormat(dateTimeFormat)
		setTimeZone(format, timeZoneId)
		Date date = new Date()
		KeywordUtil.logInfo("The current Date and Time is: " + format.format(date))
		return format.format(date)
	}
	
	
	private static SimpleDateFormat setDateFormat(String dateFormat) {
		DateFormat format
		if (dateFormat==null)
			format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a")
		else
			format = new SimpleDateFormat(dateFormat)
		return format
	}
	private static setTimeZone(DateFormat format, String timeZoneId) {
		if (timeZoneId != null)
			format.setTimeZone(TimeZone.getTimeZone(timeZoneId))
		KeywordUtil.logInfo("The current TimeZone ID is: " + format.getTimeZone().ID)
		return
	}

}