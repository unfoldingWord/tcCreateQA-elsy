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
import org.openqa.selenium.Keys as Keys
import org.sikuli.script.*

if (path == "") {
	path = '/Users/cckozie/Katalon Studio/Sikuli Images/'
}

if (match == "") {
	match = 0.85
}

imageFile = path + file

Screen s = new Screen()

Pattern source = new Pattern(imageFile).similar(match)

sourceReg = s.find(source)

w = sourceReg.getW()

dFrom = new Location(sourceReg.getX(),sourceReg.getY()+5)

dTo = new Location(sourceReg.getX()+sourceReg.getW(),sourceReg.getY()+5)

dFrom.click()

s.dragDrop(dFrom,dTo)

s.type('c', Key.CMD)

WebUI.click(findTestObject(target))

s.type('v', Key.CMD)

