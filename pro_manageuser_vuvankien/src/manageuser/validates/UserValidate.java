/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserValidate.java Dec 12, 2018, Vu Van Kien
 */
package manageuser.validates;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.MessageErrorProperties;

/**
 * Class chứa các phương thức validate liên quan đến đối tượng User
 * 
 * @author kien vu
 *
 */
public class UserValidate {

	/**
	 * Phương thức kiểm tra thông tin tài khoản mật khẩu người dùng nhập
	 * @param username Tên tài khoản người dùng nhập
	 * @param password Mật khẩu người dùng nhập
	 * @return ArrayList<String> Mảng thông báo lỗi
	 * @throws NoSuchAlgorithmException 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<String> validateLogin(String username, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException, Exception {
		ArrayList<String> message = new ArrayList<>();
		try {
			// Nếu tên tài khoản trống
			if ("".equals(username)) {
				// Thêm thông báo lỗi tài khoản trống
				message.add(MessageErrorProperties.getData("ER001_USERNAME"));
			}
			// Nếu mật khẩu trống
			if ("".equals(password)) {
				// Thêm thông báo lỗi mật khẩu trống
				message.add(MessageErrorProperties.getData("ER001_PASSWORD"));
			}
			// Nếu tên tài khoản và mật khẩu đều đã nhập
			if (message.isEmpty()) {
				TblUserLogic tblUserLogic = new TblUserLogicImpl();
				// Nếu thông tin tài khoản mật khẩu không chính xác
				if (!tblUserLogic.checkExitsAccount(username, password)) {
					// Thêm thông báo lỗi tài khoản hoặc mật khẩu không chính xác
					message.add(MessageErrorProperties.getData("ER016"));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		return message;
	}

}
