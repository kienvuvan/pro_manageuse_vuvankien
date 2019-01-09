/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserLogicImpl.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.logics.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.TblUserDao;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.TblDetailUserJapan;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class thực hiện các chức năng logic với đối tượng TblUsers
 * 
 * @author kien vu
 *
 */
public class TblUserLogicImpl implements TblUserLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkExitsAccount(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean checkExitsAccount(String username, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
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
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException {
		try {
			// replace ký tự willCard
			fullName = Common.replaceWillCard(fullName);
			// Gọi đến hàm getTotalUsers của lớp TblUserDaoImpl
			TblUserDao tblUserDaoImp = new TblUserDaoImpl();
			return tblUserDaoImp.getTotalUsers(groupId, fullName);
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getListUsers(int, int, int,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ArrayList<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException {
		try {
			TblUserDao tblUserDaoImp = new TblUserDaoImpl();
			// replace ký tự willCard
			fullName = Common.replaceWillCard(fullName);
			// Gọi đến hàm getListUsers của lớp TblUserDaoImpl
			return tblUserDaoImp.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName,
					sortByCodeLevel, sortByEndDate);
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getUserByLogIn(java.lang.String)
	 */
	@Override
	public TblUser getUserByLogIn(String username) throws ClassNotFoundException, SQLException {
		try {
			TblUserDao tblUserDaoImp = new TblUserDaoImpl();
			return tblUserDaoImp.getUserByLogIn(username);
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkExitsUsername(java.lang.String)
	 */
	public boolean checkExitsUsername(String userName) throws ClassNotFoundException, SQLException {
		try {
			TblUserDao tblUserDaoImp = new TblUserDaoImpl();
			return tblUserDaoImp.checkExitsUsername(userName);
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkExitsEmail(java.lang.String)
	 */
	@Override
	public int checkExitsEmail(String email) throws ClassNotFoundException, SQLException {
		try {
			TblUserDao tblUserDaoImp = new TblUserDaoImpl();
			return tblUserDaoImp.checkExitsEmail(email);
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.logics.TblUserLogic#creatUser(manageuser.entities.UserInfor)
	 */
	@Override
	public boolean creatUser(UserInfor userInfor)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		// Tạo đối tượng TblUserDaoImpl
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		try {
			// Khởi tạo connection
			Connection connection = tblUserDaoImpl.connectDatabase();
			// Gán thuộc tính AutoCommit bằng false
			tblUserDaoImpl.setAutoCommit(false);
			// Thực hiện truy vấn thêm User vào CSDL
			int lastId = tblUserDaoImpl.insertUser(Common.creatTblUserFromUserInfor(userInfor));
			// Nếu thêm thành công
			if (lastId != Constant.ERROR_EXCUTE_DATABASE) {
				// Nếu User có trình độ tiếng Nhật
				if (!Constant.CODE_LEVEL_DEFAULT_VALUE.equals(userInfor.getCodeLevel())) {
					// Tạo đối tượng TblDetailUserDaoImpl
					TblDetailUserJapanDao tblDetailUserDaoImpl = new TblDetailUserJapanDaoImpl();
					// Gán giá trị connection để thực hiện truy vấn
					tblDetailUserDaoImpl.setConnection(connection);
					// Gán giá trị user_id bằng giá trị user_id vừa được trả về
					// khi thêm vào bảng TblUser trong CSDL
					userInfor.setUserId(lastId);
					// Nếu thêm thành công thông tin chi tiết User
					tblDetailUserDaoImpl.insertDetailUserJapan(Common.creatTblDetailUserJapanFromUserInfor(userInfor));
				}
				// Tiến hành commit
				tblUserDaoImpl.commit();
				return true;
			}
			// Nếu có lỗi
		} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			// Tiến hành rollback
			tblUserDaoImpl.rollBack();
			throw e;
			// Đóng kết nối
		} finally {
			tblUserDaoImpl.closeConnection();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.logics.TblUserLogic#updateUser(manageuser.entities.UserInfor)
	 */
	@Override
	public boolean updateUser(UserInfor userInfor, boolean existedDetailUserJapan)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		// Tạo đối tượng TblUserDaoImpl
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		try {
			// Khởi tạo connection
			Connection connection = tblUserDaoImpl.connectDatabase();
			// Gán thuộc tính AutoCommit bằng false
			tblUserDaoImpl.setAutoCommit(false);
			// Thực hiện truy vấn sửa thông tin trong bảng TblUser
			boolean resultEditUser = tblUserDaoImpl.editUser(Common.creatTblUserFromUserInfor(userInfor));
			// Nếu thêm TblUser thành công
			if (resultEditUser) {
				// Tạo đối tượng TblDetailUserJapan từ UserInfor
				TblDetailUserJapan tblDetailUserJapan = Common.creatTblDetailUserJapanFromUserInfor(userInfor);
				// Tạo đối tượng TblDetailUserDaoImpl
				TblDetailUserJapanDao tblDetailUserDaoImpl = new TblDetailUserJapanDaoImpl();
				// Gán giá trị connection để thực hiện truy vấn
				tblDetailUserDaoImpl.setConnection(connection);
				// Lấy ra trình độ tiếng Nhật của đối tượng UserInfor
				String codeLevel = userInfor.getCodeLevel();
				// Nếu người dùng có thông tin chi tiết trong CSDL
				if (existedDetailUserJapan) {
					// Nếu đối tượng UserInfor có thông tin chi tiết
					if(!Constant.CODE_LEVEL_DEFAULT.equals(codeLevel)){
						// Tiến hành sửa thông tin chi tiết
						tblDetailUserDaoImpl.editDetailUserJapan(tblDetailUserJapan);
					// Nếu đối tượng UserInfor không có thông tin chi tiết
					}else{
						// Tiến hành xóa thông tin chi tiết
						tblDetailUserDaoImpl.deleteDetailUserJapan(tblDetailUserJapan);
					}
				// Nếu người dùng không có thông tin chi tiết trong CSDL
				}else{
					// Nếu đối tượng UserInfor có thông tin chi tiết
					if(!Constant.CODE_LEVEL_DEFAULT.equals(codeLevel)){
						// Tiến hành thêm thông tin chi tiết
						tblDetailUserDaoImpl.insertDetailUserJapan(tblDetailUserJapan);
					}
				}
				// Tiến hành commit
				tblUserDaoImpl.commit();
				return true;
			}
		} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			// Tiến hành rollback
			tblUserDaoImpl.rollBack();
			throw e;
			// Đóng kết nối
		} finally {
			tblUserDaoImpl.closeConnection();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkExistedUserInfor(int)
	 */
	@Override
	public boolean checkExistedUser(int userId) throws ClassNotFoundException, SQLException {
		// Tạo đối tượng TblUserDaoImpl
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		return tblUserDaoImpl.checkExistedUser(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getUserInforById(int)
	 */
	public UserInfor getUserInforById(int userId) throws ClassNotFoundException, SQLException {
		// Tạo đối tượng TblUserDaoImpl
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		return tblUserDaoImpl.getUserInforById(userId);
	}

}
