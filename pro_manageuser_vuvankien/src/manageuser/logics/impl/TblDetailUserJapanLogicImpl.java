/**
 * Copyright (C) 2019 Luvina Academy
 * TblDetailUserJapanLogicImpl.java Jan 9, 2019, Vu Van Kien
 */
package manageuser.logics.impl;

import java.sql.SQLException;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.logics.TblDetailUserJapanLogic;

/**
 * thực hiện các chức năng logic với đối tượng TblDetailUserJapan
 * 
 * @author kien vu
 *
 */
public class TblDetailUserJapanLogicImpl implements TblDetailUserJapanLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.logics.TblDetailUserJapanLogic#checkExistedDetailUser(int)
	 */
	@Override
	public boolean checkExistedDetailUserJapan(int userId) throws ClassNotFoundException, SQLException {
		try {
			// Tạo đối tượng TblDetailUserJapanDaoImpl
			TblDetailUserJapanDao tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
			// Trả về giá trị khi kiểm tra người dùng có thông tin chi tiết hay
			// không?
			return tblDetailUserJapanDaoImpl.checkExistedDetailUser(userId);
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("MstJapanLogicImpl : checkExistedDetailUserJapan - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
	}

}
