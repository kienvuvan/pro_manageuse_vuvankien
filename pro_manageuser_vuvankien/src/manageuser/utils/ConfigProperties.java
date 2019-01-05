/**
 * Copyright (C) 2018 Luvina Academy
 * ConfigProperties.java Dec 13, 2018, Vu Van Kien
 */
package manageuser.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * Class thực hiện đọc file config.properties
 * 
 * @author kien vu
 *
 */
@SuppressWarnings("unchecked")
public class ConfigProperties {
	private static HashMap<String, Integer> hashMapConfig = new HashMap<>();

	/**
	 * Đọc file properties và bỏ vào trong map
	 */
	static {
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"), "UTF-8"));
		} catch (IOException e) {
			e.getMessage();
		}
		Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			hashMapConfig.put(key, Integer.valueOf(properties.getProperty(key)));
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
	public static int getData(String key) {
		int value = 0;
		// Nếu trong map có tồn tại giá trị key thì lấy giá trị trong map
		if (hashMapConfig.containsKey(key)) {
			value = hashMapConfig.get(key);
		}
		// Trả ra giá trị nếu không có thì trả về chuỗi ""
		return value;
	}
}
