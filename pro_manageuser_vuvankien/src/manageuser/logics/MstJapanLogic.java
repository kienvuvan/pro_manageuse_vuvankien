/**
 * Copyright (C) 2018 Luvina Academy
 * MstJapanLogic.java Dec 25, 2018, Vu Van Kien
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.MstJapan;

/**
 * Interface thực hiện các chức năng logic với đối tượng MstJapan
 * 
 * @author kien vu
 *
 */
public interface MstJapanLogic {

	/**
	 * Phương thức lấy ra danh sách toàn bộ thông tin đối tượng MstGroup trong
	 * CSDL
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<MstJapan> getAllMstJapn() throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức lấy ra giá trị tên trình độ tiếng Nhật từ giá trị codeLevel
	 * trong CSDL
	 * 
	 * @param codeLevel
	 *            giá trị codeLevel của trình độ tiếng Nhật cần lấy tên
	 * @return String tên trình độ tiếng Nhật
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getNameLevelById(String codeLevel) throws ClassNotFoundException, SQLException;
}
