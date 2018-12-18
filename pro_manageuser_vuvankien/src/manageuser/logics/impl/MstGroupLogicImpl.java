/**
 * Copyright (C) 2018 Luvina Academy
 * MstGroupLogicImpl.java Dec 13, 2018, Vu Van Kien
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.dao.impl.MstGroupDaoImpl;
import manageuser.entities.MstGroup;
import manageuser.logics.MstGroupLogic;

/**
 * @author kien vu
 *
 */
public class MstGroupLogicImpl implements MstGroupLogic {

	private MstGroupDaoImpl mstGroupDaoImpl;

	public MstGroupLogicImpl() throws ClassNotFoundException, SQLException {
		super();
		mstGroupDaoImpl = new MstGroupDaoImpl();
	}

	/**
	 * Phương thức thực hiện xử lý lấy toàn bộ thông tin Group
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException
	 */
	@Override
	public ArrayList<MstGroup> getAllGroups() throws ClassNotFoundException, SQLException, Exception {
		try {
			return mstGroupDaoImpl.getAllGroups();
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
	}

}
