package com.gorski.ngTesting;

import java.util.HashMap;

import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.gorski.Collections.CSVobject;
import com.gorski.Collections.XLSobject;


public class XLStoCSVTest {

	@Test(description = "sysIdData")
	@Parameters({ "testName", "testGroup", "testParm1" })
	public void runTest(String testName, String testGroup, String testParm1) {
		String[] filesFound = testParm1.split(",");
		HashMap<String,HashMap<Integer,String>> dataMaps = 
				new HashMap<String,HashMap<Integer,String>>();
		/*
		 * collect data
		 */
		for(String fileFound:filesFound) {
			String sheetName[] = fileFound.split("@");
			if(fileFound.contains("xls")) {
				Reporter.log(" reading xls " + fileFound);
				XLSobject xls = new XLSobject(fileFound);
				dataMaps.put(fileFound, xls.getData(sheetName[1]));
			} else if(fileFound.contains("csv")) {
				Reporter.log(" reading csv "+ fileFound);
				CSVobject csv = new CSVobject(fileFound);
				dataMaps.put(fileFound, csv.getData());
			}
		}
		/*
		 * configure data
		 */
		for(String fileFound:filesFound) {
			HashMap<Integer, String> map = dataMaps.get(fileFound);
			System.out.println(fileFound);
			for(Integer key:map.keySet()) {
				System.out.println(fileFound+ " row " + key + ": "+map.get(key));
			}
		}
		/*
		 * compare data
		 */
		SoftAssert softAssert = new SoftAssert();
		HashMap<Integer, String> map0 = dataMaps.get(filesFound[0]);
		HashMap<Integer, String> map1 = dataMaps.get(filesFound[1]);
		for(Integer key:map0.keySet()) {
			Reporter.log("   Compare Row "+key);
			Reporter.log("   map0 "+map0.get(key));
			Reporter.log("   map1 "+map1.get(key));
			String result = compareStrings(map0.get(key),map1.get(key));
			Reporter.log("        "+result);
			softAssert.assertTrue(result.equals(""), "  Bad Match " +map0.get(key));
		}
		
		/*
		 * finalize
		 */
		softAssert.assertAll();
	}

	private String compareStrings(String w1, String w2) {
		String result ="";
		char[] first  = w1.toLowerCase().toCharArray();
		char[] second = w2.toLowerCase().toCharArray();

		int counter = 0;
		int minLength = Math.min(first.length, second.length);
		String newResult= new String(new char[w1.length()]).replace('\0', ' ');

		for(int i = 0; i < minLength; i++)
		{
		        if (first[i] != second[i])
		        {
		            newResult = newResult.substring(0,i)+'^'+newResult.substring(i+1);
		            counter++;    
		        }
		}
		result=newResult;
		if(counter<1) {
			result="";
		}
		return result;
	}
}

