package sandbox

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

import internal.GlobalVariable
import com.kms.katalon.core.webui.common.WebUiCommonHelper

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory

import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider

import javax.imageio.ImageIO;
import java.io.File;

public class screenshotHelper {
	// THIS DOES TAKE A SCREENSHOT BUT IT IS NOT OF THE AREA EXPECTED.
	@Keyword
	def getWebElementScreenshot(TestObject object) {
		println(1)
		println(object)
		WebDriver driver = DriverFactory.getWebDriver()
		println(2)
		WebElement element = WebUiCommonHelper.findWebElement(object, 10)
		println(3)
		println(element)
		Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, element)
		println(4)
		ImageIO.write(screenshot.getImage(),"PNG",new File('/Users/cckozie/git/Katalon_tC_Create/Screenshots/MyScreenshot.png'))
	}
}
