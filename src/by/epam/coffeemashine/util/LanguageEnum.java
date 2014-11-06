package by.epam.coffeemashine.util;

import java.util.Locale;

public enum LanguageEnum {
	RU {
		{
			this.current = new Locale("ru", "RU");
		}
	},
	EN {
		{
			this.current = new Locale("en", "US");
		}
	};

	Locale current;

	public Locale getCurrent() {
		return current;
	}
}
