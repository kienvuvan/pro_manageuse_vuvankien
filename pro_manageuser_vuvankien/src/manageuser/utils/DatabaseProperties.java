/**
 * Copyright (C) 2018 Luvina Academy
 * DatabaseProperties.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author kien vu
 *
 */
@SuppressWarnings("unchecked")
public class DatabaseProperties {
	private static HashMap<String, String> hashMapDatabase = new HashMap<>();

	/**
	 * Đọc file properties và bỏ vào trong map
	 */
	static {
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"), "UTF-8"));
		} catch (IOException e) {
			e.getMessage();
		}
		Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			hashMapDatabase.put(key, properties.getProperty(key));
		}
	}

	/**
	 * Hàm đọc từ trong map
	 * 
	 * @param key:
	 *            key cần đọc
	 * 
	 * @return giá trị của key
	 */
	public static String getData(String key) {
		String value = "";
		if (hashMapDatabase.containsKey(key)) {
			value = hashMapDatabase.get(key);
		}
		return value;
	}
}
