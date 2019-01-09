/**
 * Copyright (C) 2019 Luvina Academy
 * TblDetailUserJapanLogic.java Jan 9, 2019, Vu Van Kien
 */
package manageuser.logics;

import java.sql.SQLException;

/**
 * @author kien vu
 *
 */
public interface TblDetailUserJapanLogic {

	/**
	 * Phương thức kiểm tra thông tin chi tiết người dùng có tồn tại trong CSDL hay không?
	 * @param userId id của người dùng cần kiểm tra
	 * @return true nếu thông tin chi tiết người dùng tồn tại và ngược lại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean checkExistedDetailUserJapan(int userId) throws ClassNotFoundException, SQLException;
}
