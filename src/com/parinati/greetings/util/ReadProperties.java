package com.parinati.greetings.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ReadProperties {
	private static final Logger logger = Logger.getLogger(ReadProperties.class);

	public Properties readProperties() {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			logger.info(">>>" + new File(".").getAbsolutePath());

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

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
		return prop;
	}
}
