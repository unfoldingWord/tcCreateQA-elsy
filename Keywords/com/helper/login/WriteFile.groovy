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
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import internal.GlobalVariable

public class WriteFile {
	private String path;
	private boolean append_to_file = false;

	//Writeover file
	public WriteFile (String file_path) {
		path = file_path; }

	//Appends to file
	public WriteFile (String file_path, boolean append_value){
		path = file_path;
		append_to_file = append_value;
	}
	public void WriteToFile (String textline) throws IOException {
		FileWriter write = new FileWriter(path, append_to_file);
		PrintWriter print_line = new PrintWriter(write);
		print_line.printf("%s" + "%n", textline);
		print_line.close(); }

	public void WriteOutput() throws IOException
	{
		FileWriter write = new FileWriter(path, append_to_file);
		FileOutputStream file_name = new FileOutputStream("file.txt");
		WriteFile data = new WriteFile(file_name)
		System.setOut(new PrintStream(file_name));
	}
}