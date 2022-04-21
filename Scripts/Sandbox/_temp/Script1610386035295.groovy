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
import groovy.io.FileType as FileType
import org.apache.commons.io.FileUtils as FileUtils
import java.awt.datatransfer.Clipboard as Clipboard
import java.awt.datatransfer.Transferable as Transferable
import java.awt.datatransfer.DataFlavor as DataFlavor
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import org.openqa.selenium.Keys as Keys
import groovy.time.*
import com.kms.katalon.core.testobject.ConditionType as ConditionType

expected = 'On line 1 Bad TSV Header, expecting:"Book, Chapter, Verse, ID, SupportReference, OrigQuote, Occurrence, GLQuote, OccurrenceNote", found:"TIT, front, intro, m2jl, , ab, 0, , # Introduction to Titus<br><br>## Part 1: Ge..."   On line 1 Headers different at character 1: B (x42) vs T (x54)   On line 1 TSV Header has incorrect length, should be 82; found 3027'
found = 'On line 1 Bad TSV Header, expecting:"Book, Chapter, Verse, ID, SupportReference, OrigQuote, Occurrence, GLQuote, OccurrenceNote", found:"TIT, front, intro, m2jl, , ab, 0, , # Introduction to Titus<br><br>## Part 1: Ge..."   On line 1 Headers different at character 1: B (x42) vs T (x54)   On line 1 TSV Header has incorrect length, should be 82; found 3027'

l = 0
for (c in expected) {
	ordExp = (int) c
	ordFnd = (int) found[l]
	if (ordExp != ordFnd) {
		println('Character number ' + l + ' does not match. ' + c + ':' + ordExp + ' vs ' + found[l] + ':' + ordFnd)
	}
	l ++
}