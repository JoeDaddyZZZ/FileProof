package com.gorski.Collections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.testng.Reporter;

public class CSVobject {
	private HashMap<Integer, String> rowMap = new HashMap<Integer, String>();
	private String fileName;

	public CSVobject(String fileName) {
		super();
		this.fileName = fileName;
		this.collectData();
	}
	public HashMap<Integer,String> getData() {
		return rowMap;
	}

	private void collectData() {
		String line="";
        BufferedReader fileReader;
		try {
			fileReader = new BufferedReader(new FileReader(fileName));
        Integer rowNum = 0;
        while ((line = fileReader.readLine()) != null) {
            try {
                String[] tokens = line.split(",");
                rowMap.put(rowNum, line);
                rowNum++;
            } catch(Exception e) {
                Reporter.log("Test Failed at step: "+rowNum);
                try {
					fileReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        }
        fileReader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
