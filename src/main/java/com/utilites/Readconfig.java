package com.utilites;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Readconfig {

	Properties pro;

	public Readconfig() {

		File src = new File("E:\\naveen\\projects\\hybrid-framework\\configurations\\config.properties");
		try {
			FileInputStream fis = new FileInputStream(src);
			pro = new Properties();
			pro.load(fis);
		} catch (Exception e) {
			System.out.println("exception" + e.getMessage());
		}
	}

	public String getapplicationurl() {

		String url = pro.getProperty("baseurl");
		return url;

	}

	public String getusername() {
		String username = pro.getProperty("username");

		return username;

	}

	public String getpassword() {
		String password = pro.getProperty("password");
		return password;

	}
}
