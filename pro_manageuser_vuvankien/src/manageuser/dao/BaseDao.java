/**
 * Copyright (C) 2018 Luvina Academy
 * BaseDao.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.Connection;

/**
 * Interface BaseDao
 * @author kien vu
 *
 */
public interface BaseDao {
	
	/**
	 * Phương thức kết nối với database và trả v�? kết nối đó
	 * @return Connection 1 kết nối với database
	 */
	public Connection connectDatabase() throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức đóng kết nối với database
	 */
	public void closeConnection() throws SQLException;
	
	/**
	 * Phương thức setAutoCommit
	 * @throws SQLException 
	 */
	public void setAutoCommit(boolean status) throws SQLException;

	/**
	 * Phương thức commit
	 * @throws SQLException 
	 */
	public void commit() throws SQLException;
	
	/**
	 * Phương thức rollBack
	 * @throws SQLException 
	 */
	public void rollBack() throws SQLException;
	
	/**
	 * Phương thức lấy ra kết nối với CSDL
	 * @return trả về 1 connection
	 */
	public Connection getConnection();
	
	/**
	 * Phương thức gán giá trị cho 1 Connection
	 * @param connection giá trị connection
	 */
	public void setConnection(Connection connection);
	
	/**
	 * Phương thức lấy ra danh sách tên các cột trong CSDL
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public HashMap<Integer, String> getColumnSort() throws ClassNotFoundException, SQLException;
}
