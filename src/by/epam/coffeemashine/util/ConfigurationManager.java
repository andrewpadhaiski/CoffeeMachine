package by.epam.coffeemashine.util;

import java.util.ResourceBundle;

public class ConfigurationManager {
	private static final String CONFIGURATION_PATH = "by.epam.coffeemashine.util.configuration";

	private static final ResourceBundle BUNDLE = ResourceBundle
			.getBundle(CONFIGURATION_PATH);

	private ConfigurationManager() {
	}

	public static String getProperty(String key) {
		return BUNDLE.getString(key);
	}

}
