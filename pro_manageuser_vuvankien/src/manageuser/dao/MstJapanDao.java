/**
 * Copyright (C) 2018 Luvina Academy
 * LevelJapanDao.java Dec 12, 2018, Vu Van Kien
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.MstJapan;

/**
 * Interface MstJapanDao
 * @author kien vu
 *
 */
public interface MstJapanDao extends BaseDao {
	
	/**
	 * Phương thức lấy ra danh sách tất cả thông tin về trình độ tiếng Nhật trong CSDL
	 * @return Danh sách đối tượng MstJapan
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<MstJapan> getAllMstJapan() throws ClassNotFoundException, SQLException;
	
	/**
	 * Phương thức lấy ra tên trình độ tiếng Nhật bởi codeLevel
	 * @param codeLevel giá trị codeLevel của trình độ tiếng Nhật cần lấy tên
	 * @return tên của trình độ tiếng Nhật
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getNameLevelById(String codeLevel) throws ClassNotFoundException, SQLException;
}
