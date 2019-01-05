/**
 * Copyright (C) 2018 Luvina Academy
 * MstGroupLogicImpl.java Dec 13, 2018, Vu Van Kien
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.dao.MstGroupDao;
import manageuser.dao.impl.MstGroupDaoImpl;
import manageuser.entities.MstGroup;
import manageuser.logics.MstGroupLogic;

/**
 * Class thực hiện các chức năng logic với đối tượng MstGroup
 * 
 * @author kien vu
 *
 */
public class MstGroupLogicImpl implements MstGroupLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.MstGroupLogic#getAllGroups()
	 */
	@Override
	public ArrayList<MstGroup> getAllGroups() throws ClassNotFoundException, SQLException {
		try {
			MstGroupDao mstGroupDaoImpl = new MstGroupDaoImpl();
			return mstGroupDaoImpl.getAllGroups();
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.MstGroupLogic#getGroupNameById(java.lang.String)
	 */
	@Override
	public String getGroupNameById(int groupId) throws ClassNotFoundException, SQLException {
		try {
			MstGroupDao mstGroupDaoImpl = new MstGroupDaoImpl();
			return mstGroupDaoImpl.getGroupNameById(groupId);
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
	}

}
