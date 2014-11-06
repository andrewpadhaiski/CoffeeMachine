package by.epam.coffeemashine.controller.constant;

import by.epam.coffeemashine.util.ConfigurationManager;

public class Page {
	
	public static final String INDEX = ConfigurationManager
			.getProperty("path.page.index");	
	public static final String LANDING = ConfigurationManager
			.getProperty("path.page.landing");
	public static final String PROFILE = ConfigurationManager
			.getProperty("path.page.common.profile");
	public static final String USER_COFFEE = ConfigurationManager
			.getProperty("path.page.user.coffee");
	public static final String USER_INGREDIENT = ConfigurationManager
			.getProperty("path.page.user.ingredient"); 
	public static final String USER_ORDER = ConfigurationManager
			.getProperty("path.page.user.order");
	public static final String USER_THANKS = ConfigurationManager
			.getProperty("path.page.user.thanks");
	public static final String USER_ORDERS = ConfigurationManager
			.getProperty("path.page.user.orders");
	public static final String ADMIN_COFFEE = ConfigurationManager
			.getProperty("path.page.admin.coffee");
	public static final String ADMIN_INGREDIENT = ConfigurationManager
			.getProperty("path.page.admin.ingredient");
	public static final String ADMIN_ORDERS = ConfigurationManager
			.getProperty("path.page.admin.orders");	
	
}
