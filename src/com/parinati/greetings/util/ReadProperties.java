package com.parinati.greetings.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ReadProperties {
	private static final Logger logger = Logger.getLogger(ReadProperties.class);

	public HashMap<String, String> readProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		HashMap<String, String> mailProperties = new HashMap<String, String>();
		try {
			logger.info(">>>" + new File(".").getAbsolutePath());

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			mailProperties.put("filePath", prop.getProperty("filePath"));
			mailProperties.put("senderEmail", prop.getProperty("senderEmail"));
			mailProperties.put("senderPassword", prop.getProperty("senderPassword"));
			mailProperties.put("mailProtocol", prop.getProperty("mailProtocol"));
			mailProperties.put("draftFolder", prop.getProperty("draftFolder"));

		} catch (IOException ex) {
			ex.printStackTrace();
			logger.info(ex.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mailProperties;
	}
}
