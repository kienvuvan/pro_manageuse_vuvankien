/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserLogic.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.UserInfor;

/**
 * @author kien vu
 *
 */
public interface TblUserLogic {
	/**
	 * Phương thức thực hiện xử lý kiểm tra tài khoản có tồn tại trong CSDL hay không?
	 * @param username giá trị tên tài khoản
	 * @param password giá trị trường password
	 * @return true nếu tài khoản đúng và ngược lại
	 * @throws NoSuchAlgorithmException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean checkExitsAccount(String username, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException, Exception;

	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException, Exception;

	public ArrayList<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException, Exception;
}
