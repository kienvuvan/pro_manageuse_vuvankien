/**
 * Copyright (C) 2018 Luvina Academy
 * TblDetailUserDao.java Dec 12, 2018, Vu Van Kien
 */
package manageuser.dao;

import java.sql.SQLException;

import manageuser.entities.TblDetailUserJapan;

/**
 * Interface TblDetailUserDao
 * @author kien vu
 *
 */
public interface TblDetailUserDao extends BaseDao {
	
	/**
	 * Phương thức thêm chi tiết User vào trong CSDL
	 * @param tblDetailUserJapan đối tượng TblDetailUserJapan
	 * @return true nếu thêm thành công và ngược lại
	 * @throws SQLException
	 */
	public boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException;
}
