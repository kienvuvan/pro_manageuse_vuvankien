/**
 * Copyright (C) 2018 Luvina Academy
 * MstGroupDao.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;
import manageuser.entities.MstGroup;

/**
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
	public List<MstGroup> getAllGroups() throws ClassNotFoundException, SQLException;
}
