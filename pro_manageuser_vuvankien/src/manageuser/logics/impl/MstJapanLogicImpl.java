/**
 * Copyright (C) 2018 Luvina Academy
 * MstJapanLogicImpl.java Dec 25, 2018, Vu Van Kien
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.dao.MstJapanDao;
import manageuser.dao.impl.MstJapanDaoImpl;
import manageuser.entities.MstJapan;
import manageuser.logics.MstJapanLogic;

/**
 * Class thực hiện các chức năng logic với đối tượng MstJapan
 * 
 * @author kien vu
 *
 */
public class MstJapanLogicImpl implements MstJapanLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.MstJapanLogic#getAllMstJapn()
	 */
	@Override
	public ArrayList<MstJapan> getAllMstJapn() throws ClassNotFoundException, SQLException {
		try {
			// Tạo đối tượng MstJapanDaoImpl
			MstJapanDao mstJapanDaoImpl = new MstJapanDaoImpl();
			// Trả về ArrayList<MstJapan>
			return mstJapanDaoImpl.getAllMstJapan();
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : "
					+ new Object(){}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.MstJapanLogic#getGroupNameById(int)
	 */
	@Override
	public String getNameLevelById(String codeLevel) throws ClassNotFoundException, SQLException {
		try {
			// Tạo đối tượng MstJapanDaoImpl
			MstJapanDao mstJapanDaoImpl = new MstJapanDaoImpl();
			// Trả về tên của trình độ tiếng Nhật
			return mstJapanDaoImpl.getNameLevelById(codeLevel);
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : "
					+ new Object(){}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
	}

}
