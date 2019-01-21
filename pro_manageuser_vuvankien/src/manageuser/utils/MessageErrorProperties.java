/**
 * Copyright (C) 2018 Luvina Academy
 * MessageErrorProperties.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * Class thực hiện chức đọc file messageerror.properties
 * 
 * @author kien vu
 *
 */
@SuppressWarnings("unchecked")
public class MessageErrorProperties {
	private static HashMap<String, String> hashMapMessageError = new HashMap<>();

	/**
	 * Đọc file properties và bỏ vào trong map
	 */
	static {
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("messageerror.properties"),
					"UTF-8"));
		} catch (IOException e) {
			// In ra lỗi
			System.out.println(new Object(){}.getClass().getSimpleName() + " : "
					+ new Object(){}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
		}
		Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			hashMapMessageError.put(key, properties.getProperty(key));
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
		// Nếu trong map có tồn tại giá trị key thì lấy giá trị trong map
		if (hashMapMessageError.containsKey(key)) {
			value = hashMapMessageError.get(key);
		}
		// Trả ra giá trị nếu không có thì trả về chuỗi ""
		return value;
	}
}
