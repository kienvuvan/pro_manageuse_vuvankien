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
			TblDetailUserJapanDao tblDetailUserJapanDaoImpl = new TblDetailUserJapanDaoImpl();
			return tblDetailUserJapanDaoImpl.checkExistedDetailUser(userId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

}
