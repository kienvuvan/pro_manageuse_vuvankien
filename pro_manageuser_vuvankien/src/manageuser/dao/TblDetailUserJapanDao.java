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
public interface TblDetailUserJapanDao extends BaseDao {
	
	/**
	 * Phương thức thêm thông tin chi tiết User vào trong CSDL
	 * @param tblDetailUserJapan đối tượng TblDetailUserJapan
	 * @return true nếu thêm thành công và ngược lại
	 * @throws SQLException
	 */
	public boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException;
	
	/**
	 * Phương thức sửa thông tin chi tiết User trong CSDL
	 * @param tblDetailUserJapan đối tượng TblDetailUserJapan
	 * @return true nếu sửa thành công và ngược lại
	 * @throws SQLException
	 */
	public boolean editDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException;
	
	/**
	 * Phương thức xóa thông tin chi tiết người dùng trong CSDL
	 * @param int giá trị id của user cần xóa
	 * @return true nếu xóa thành công và ngược lại
	 * @throws SQLException
	 */
	public boolean deleteDetailUserJapan(int userId) throws SQLException;
	
	/**
	 * Phương thức kiểm tra thông tin chi tiết người dùng có tồn tại trong CSDL hay không?
	 * @param userId id của người dùng cần kiểm tra
	 * @return true nếu thông tin chi tiết người dùng tồn tại và ngược lại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean checkExistedDetailUser(int userId) throws ClassNotFoundException, SQLException;

}
