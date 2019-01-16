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
			// Tạo đối tượng MstGroupDaoImpl
			MstGroupDao mstGroupDaoImpl = new MstGroupDaoImpl();
			// Trả về ArrayList<MstGroup>
			return mstGroupDaoImpl.getAllGroups();
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("MstGroupLogicImpl : getAllGroups - " + e.getMessage());
			// Ném ra 1 lỗi
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
			// Tạo đối tượng MstGroupDaoImpl
			MstGroupDao mstGroupDaoImpl = new MstGroupDaoImpl();
			// Trả về tên group
			return mstGroupDaoImpl.getGroupNameById(groupId);
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("MstGroupLogicImpl : getGroupNameById - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
	}

}
