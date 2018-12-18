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
	public Connection connectDatabase() throws Exception;

	/**
	 * Phương thức đóng kết nối với database
	 */
	public void closeConnection() throws Exception;
	
	/**
	 * Phương thức lấy tên các cột của các bảng trong CSDL
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws Exception 
	 * 
	 */
	public HashMap<Integer, String> getColumnSort() throws ClassNotFoundException, SQLException;
	
}
