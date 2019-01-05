/**
 * Copyright (C) 2018 Luvina Academy
 * MstGroupDao.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.MstGroup;

/**
 * Interface MstGroupDao
 * @author kien vu
 *
 */
public interface MstGroupDao extends BaseDao{
	/**
	 * Phương thức lấy toàn bộ danh sách group
	 * @return Danh sách đối tượng MstGroup
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<MstGroup> getAllGroups() throws ClassNotFoundException, SQLException;
	
	/**
	 * Phương thức lấy tên nhóm từ groupId
	 * @param groupId mã nhóm
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String getGroupNameById(int groupId) throws ClassNotFoundException, SQLException;
}
