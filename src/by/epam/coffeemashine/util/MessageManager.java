package by.epam.coffeemashine.util;

import java.util.ResourceBundle;

public class MessageManager {
	private static final String MESSAGES_PATH = "by.epam.coffeemashine.util.messages";

	private static ResourceBundle bundle = ResourceBundle
			.getBundle(MESSAGES_PATH, LanguageEnum.EN.getCurrent());

	private MessageManager() {
	}

	public static String getMessage(String key) {
		return bundle.getString(key);
	}
	
	public static void setLanguage(String str) {
		LanguageEnum lang = LanguageEnum.valueOf(str.toUpperCase());
		bundle = ResourceBundle.getBundle(MESSAGES_PATH, lang.getCurrent());
	}
}
