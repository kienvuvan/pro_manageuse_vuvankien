/**
 * Copyright (C) 2018 Luvina Academy
 * BaseDaoImpl.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao.impl;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.Connection;

import manageuser.dao.BaseDao;
import manageuser.utils.DatabaseProperties;

/**
 * Class thực thi interface BaseDao đê thực hiện tạo hoặc đóng kết nối với CSDL
 * 
 * @author kien vu
 *
 */
public class BaseDaoImpl implements BaseDao {

	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static String url;
	private static String database;
	private static String driver;
	private static String username;
	private static String password;
	private static HashMap<Integer, String> hashMapColumnSort = new HashMap<>();
	private static final String SELECT_ALL_TABLE = "SHOW TABLES";
	private static final String SELECT_ALL_COLUMN_IN_TABLE = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";

	public BaseDaoImpl() throws ClassNotFoundException, SQLException {
		try {
			getColumnSort();
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
	}

	/**
	 * Phương thức kết nối với database và trả v�? kết nối đó
	 * 
	 * @return Connection 1 kết nối với database
	 * @throws Exception
	 */
	@Override
	public Connection connectDatabase() throws ClassNotFoundException, SQLException {
		try {
			// Đọc các thông số kết nối database trong file properties
			url = DatabaseProperties.getData("url");
			database = DatabaseProperties.getData("database");
			driver = DatabaseProperties.getData("driver");
			username = DatabaseProperties.getData("username");
			password = DatabaseProperties.getData("password");
			Class.forName(driver);
			// Kết nối database
			connection = (Connection) DriverManager.getConnection(url + database, username, password);
		} catch (ClassNotFoundException | SQLException ex) {
			ex.getMessage();
			throw ex;
		}
		return connection;
	}

	/**
	 * Phương thức đóng kết nối với database
	 * 
	 * @throws Exception
	 */
	@Override
	public void closeConnection() throws SQLException {
		try {
			if (connection != null || !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.getMessage();
			throw e;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#getColumnSort()
	 */
	@Override
	public HashMap<Integer, String> getColumnSort() throws ClassNotFoundException, SQLException {
		try {
			connection = connectDatabase();
			if (connection != null) {
				int index = 1;
				preparedStatement = (PreparedStatement) connection.prepareStatement(SELECT_ALL_TABLE);
				// báº£ng trong CSDL
				ResultSet resultSetTables = preparedStatement.executeQuery();
				// Duyá»‡t káº¿t quáº£ tÃ¬m Ä‘Æ°á»£c
				while (resultSetTables.next()) {
					// Truyá»�n cÃ¢u truy váº¥n láº¥y toÃ n bá»™ cÃ¡c cá»™t cÃ³
					// trong báº£ng
					String table_Name = resultSetTables.getString(1);
					preparedStatement = (PreparedStatement) connection.prepareStatement(SELECT_ALL_COLUMN_IN_TABLE);
					// Truyá»�n tham sá»‘ lÃ  tÃªn báº£ng Ä‘ang xÃ©t
					preparedStatement.setString(1, table_Name);
					// Tráº£ vá»� káº¿t quáº£ lÃ  danh sÃ¡ch cÃ¡c cá»™t trong
					// báº£ng Ä‘Ã³
					ResultSet resultSetColumns = preparedStatement.executeQuery();
					// Duyá»‡t Ä‘á»ƒ láº¥y káº¿t quáº£ cÃ¡c cá»™t Ä‘Ã³
					while (resultSetColumns.next()) {
						// ThÃªm vÃ o danh sÃ¡ch cÃ¡c cá»™t
						String column = resultSetColumns.getString(1);
						hashMapColumnSort.put(index++, table_Name + "." + column);
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			throw sqle;
		} finally {
			closeConnection();
		}
		return hashMapColumnSort;
	}

	public static String getData(String value) {
		String result = "";
		if (hashMapColumnSort.containsValue(value)) {
			result = value;
		}
		return result;
	}
}
