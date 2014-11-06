package by.epam.coffeemashine.model.data.constant;

import by.epam.coffeemashine.util.ConfigurationManager;

public class DbParameter {

	public static final String USER = "user";
	public static final String PASSWORD = "password";
	public static final String RECONNECT = "autoReconnect";
	public static final String ENCODING = "characterEncoding";
	public static final String UNICODE = "useUnicode";

	public static final String URL = ConfigurationManager.getProperty("db.url");

	public static final String USER_VALUE = ConfigurationManager
			.getProperty("db.user");
	public static final String PASSWORD_VALUE = ConfigurationManager
			.getProperty("db.password");
	public static final String RECONNECT_VALUE = ConfigurationManager
			.getProperty("db.reconnect");
	public static final String ENCODING_VALUE = ConfigurationManager
			.getProperty("db.encoding");
	public static final String UNICODE_VALUE = ConfigurationManager
			.getProperty("db.unicode");
	
	public static final int POOL_SIZE = Integer.valueOf(ConfigurationManager
			.getProperty("db.poolsize"));

}
