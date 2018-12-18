/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserLogicImpl.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.logics.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.dao.TblUserDao;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;
import manageuser.utils.Common;

/**
 * @author kien vu
 *
 */
public class TblUserLogicImpl implements TblUserLogic {

	/**
	 * @throws NoSuchAlgorithmException
	 * 
	 */
	@Override
	public boolean checkExitsAccount(String username, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException, Exception {
		try {
			TblUserDao tblUserDao = new TblUserDaoImpl();
			TblUser tblUserLogin = tblUserDao.getUserByLogIn(username);
			// Kiểm tra nếu việc mã hóa dữ liệu từ password và salt ra đúng
			// pass trong CSDL
			if (Common.encrypt(password, tblUserLogin.getSalt()).equals(tblUserLogin.getPassword())) {
				// Trả về true (Tài khoản đúng)
				return true;
			}
		} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getTotalUsers(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException, Exception {
		try {
			// Nếu giá trị tìm kiếm theo tên null
			if (fullName == null) {
				// Gán bằng ""
				fullName = "";
			}
			// Gọi đến hàm getTotalUsers của lớp TblUserDaoImpl
			return new TblUserDaoImpl().getTotalUsers(groupId, fullName);
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see manageuser.logics.TblUserLogic#getListUsers(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException, Exception {
		// Nếu giá trị tìm kiếm theo tên null
		if (fullName == null) {
			// Gán bằng ""
			fullName = "";
		}else{
			// replace ký tự willCard
			fullName = Common.replaceWillCard(fullName);
		}
		// Gọi đến hàm getListUsers của lớp TblUserDaoImpl
		return new TblUserDaoImpl().getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
	}

}
