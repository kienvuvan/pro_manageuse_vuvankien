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

	protected Connection connection;
	private static PreparedStatement preparedStatement;
	private static String url;
	private static String database;
	private static String driver;
	private static String username;
	private static String password;
	// Câu lệnh truy vấn lấy ra danh sách tất cả các bảng đang có trong CSDL
	private static final String SELECT_ALL_TABLE = "SHOW TABLES";
	// Câu lệnh truy vấn lấy ra danh sách tất cả các cột trong 1 bảng
	private static final String SELECT_ALL_COLUMN_IN_TABLE = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#connectDatabase()
	 */
	@Override
	public Connection connectDatabase() throws ClassNotFoundException, SQLException {
		try {
			// Đọc các thông số kết nối database trong file properties
			// Địa chỉ CSDL
			url = DatabaseProperties.getData("url");
			// Tên CSDL
			database = DatabaseProperties.getData("database");
			// Driver kết nối
			driver = DatabaseProperties.getData("driver");
			// Tên tài khoản
			username = DatabaseProperties.getData("username");
			// Mật khẩu
			password = DatabaseProperties.getData("password");
			Class.forName(driver);
			// Kết nối database
			connection = (Connection) DriverManager.getConnection(url + database, username, password);
		// Nếu lỗi
		} catch (ClassNotFoundException | SQLException ex) {
			ex.getMessage();
			throw ex;
		}
		// Trả về connection vừa kết nối
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#getConnection()
	 */
	@Override
	public Connection getConnection() {
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#setConnection(com.mysql.jdbc.Connection)
	 */
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#closeConnection()
	 */
	@Override
	public void closeConnection() throws SQLException {
		try {
			// Nếu kết nối chưa đóng
			if (connection != null || !connection.isClosed()) {
				// Đóng kết nối
				connection.close();
			}
			// Nếu có lỗi trong quá trình đóng kết nối
		} catch (SQLException e) {
			e.getMessage();
			throw e;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#setAutoCommit()
	 */
	@Override
	public void setAutoCommit(boolean status) throws SQLException {
		try {
			// Nếu connection không null thì setAutoCommit bằng tham số status truyền vào
			if (connection != null) {
				connection.setAutoCommit(status);
			}
			// Nếu lỗi
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#commit()
	 */
	@Override
	public void commit() throws SQLException {
		try {
			// Nếu connection không null thì tiến hành commit
			if (connection != null) {
				connection.commit();
			}
			// Nếu lỗi
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#rollBack()
	 */
	@Override
	public void rollBack() throws SQLException {
		try {
			// Nếu connection không null thì tiến hành rollBack
			if (connection != null) {
				connection.rollback();
			}
			// Nếu lỗi
		} catch (SQLException e) {
			e.printStackTrace();
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
		// Tạo đối tượng HashMap để lưu trữ tên cột được lấy ra từ CSDL
		HashMap<Integer, String> hashMapColumnSort = new HashMap<>();
		try {
			// Tạo kết nối với CSDL
			connection = connectDatabase();
			// Nếu kết nối thành công
			if (connection != null) {
				int index = 1;
				// Truyền câu lệnh truy vấn lấy ra danh sách tất cả các bảng trong CSDL
				preparedStatement = (PreparedStatement) connection.prepareStatement(SELECT_ALL_TABLE);
				// Lấy tất cả các bảng trong CSDL
				ResultSet resultSetTables = preparedStatement.executeQuery();
				// Duyệt danh sách các bảng lấy ra từ CSDL
				while (resultSetTables.next()) {
					// Lấy tên bảng
					String table_Name = resultSetTables.getString(1);
					// Truyền câu lệnh truy vấn lấy ra danh sách tất cả các cột của 1 bảng trong CSDL
					preparedStatement = (PreparedStatement) connection.prepareStatement(SELECT_ALL_COLUMN_IN_TABLE);
					// Set tham số tên bảng vào trong câu lệnh truy vấn
					preparedStatement.setString(1, table_Name);
					// Truyền tham số là tên bảng muốn lấy tên các cột
					ResultSet resultSetColumns = preparedStatement.executeQuery();
					// Duyệt danh sách tên các cột lấy ra được
					while (resultSetColumns.next()) {
						// Lấy giá trị tên cột
						String column = resultSetColumns.getString(1);
						// Thêm giá trị vào HashMap
						hashMapColumnSort.put(index++, table_Name + "." + column);
					}
				}
			}
		// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		// Trả về HashMap chứa tên cột
		return hashMapColumnSort;
	}

}
