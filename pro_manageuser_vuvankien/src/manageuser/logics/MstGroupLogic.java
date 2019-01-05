/**
 * Copyright (C) 2018 Luvina Academy
 * MstGroupLogic.java Dec 13, 2018, Vu Van Kien
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.MstGroup;

/**
 * Interface thực hiện các chức năng logic với đối tượng MstGroup
 * 
 * @author kien vu
 *
 */
public interface MstGroupLogic {

	/**
	 * Phương thức thực hiện xử lý lấy toàn bộ thông tin Group
	 * 
	 * @throws ClassNotFoundException,SQLException
	 */
	public ArrayList<MstGroup> getAllGroups() throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức lấy ra giá trị tên group từ giá trị groupId trong CSDL
	 * @param groupId giá trị groupId của group cần lấy tên
	 * @return String tên group
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getGroupNameById(int groupId) throws ClassNotFoundException, SQLException;
}
