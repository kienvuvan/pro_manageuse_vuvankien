/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserLogicImpl.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.logics.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
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
	public boolean checkExitsAccount(String userName, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		try {
			// Tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDao = new TblUserDaoImpl();
			// Lấy ra đối tượng TblUser từ userName
			TblUser tblUserLogin = tblUserDao.getUserByLogIn(userName);
			// Kiểm tra nếu việc mã hóa dữ liệu từ password và salt ra đúng
			// pass trong CSDL
			if (Common.encrypt(password, tblUserLogin.getSalt()).equals(tblUserLogin.getPassword())) {
				// Trả về true (Tài khoản đúng)
				return true;
			}
			// Nếu có lỗi
		} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : checkExitsAccount - " + e.getMessage());
			// Ném ra 1 lỗi
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
			// Khởi tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDaoImp = new TblUserDaoImpl();
			// Gọi đến hàm getTotalUsers() và trả về kết quả
			return tblUserDaoImp.getTotalUsers(groupId, fullName);
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : getTotalUsers - " + e.getMessage());
			// Ném ra 1 lỗi
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
			// Khởi tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDaoImp = new TblUserDaoImpl();
			// replace ký tự willCard
			fullName = Common.replaceWillCard(fullName);
			// Gọi đến hàm getListUsers() của lớp TblUserDaoImpl và trả về kết
			// quả
			return tblUserDaoImp.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName,
					sortByCodeLevel, sortByEndDate);
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : getListUsers - " + e.getMessage());
			// Ném ra 1 lỗi
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
		TblUser tblUser = new TblUser();
		try {
			// Khởi tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDaoImp = new TblUserDaoImpl();
			// Gọi đến hàm getUserByLogIn() và trả về kết quả
			tblUser = tblUserDaoImp.getUserByLogIn(username);
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : getUserByLogIn - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		return tblUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkExitsUsername(java.lang.String)
	 */
	public int checkExitsUsername(String userName) throws ClassNotFoundException, SQLException {
		try {
			// Khởi tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDaoImp = new TblUserDaoImpl();
			// Gọi đến hàm checkExitsUsername() và trả về kết quả
			return tblUserDaoImp.checkExitsUsername(userName);
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : checkExitsUsername - " + e.getMessage());
			// Ném ra 1 lỗi
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
			// Khởi tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDaoImp = new TblUserDaoImpl();
			// Gọi đến hàm checkExitsEmail() và trả về kết quả
			return tblUserDaoImp.checkExitsEmail(email);
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : checkExitsEmail - " + e.getMessage());
			// Ném ra 1 lỗi
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
			throws ParseException, NoSuchAlgorithmException, ClassNotFoundException, SQLException {
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
		} catch (ParseException | NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : creatUser - " + e.getMessage());
			// Tiến hành rollback
			tblUserDaoImpl.rollBack();
			// Ném ra 1 lỗi
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
			throws ParseException, NoSuchAlgorithmException, ClassNotFoundException, SQLException {
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
					if (!Constant.CODE_LEVEL_DEFAULT.equals(codeLevel)) {
						// Tiến hành sửa thông tin chi tiết
						tblDetailUserDaoImpl.editDetailUserJapan(tblDetailUserJapan);
						// Nếu đối tượng UserInfor không có thông tin chi tiết
					} else {
						// Tiến hành xóa thông tin chi tiết
						tblDetailUserDaoImpl.deleteDetailUserJapan(tblDetailUserJapan.getUserId());
					}
					// Nếu người dùng không có thông tin chi tiết trong CSDL
				} else {
					// Nếu đối tượng UserInfor có thông tin chi tiết
					if (!Constant.CODE_LEVEL_DEFAULT.equals(codeLevel)) {
						// Tiến hành thêm thông tin chi tiết
						tblDetailUserDaoImpl.insertDetailUserJapan(tblDetailUserJapan);
					}
				}
				// Tiến hành commit
				tblUserDaoImpl.commit();
				return true;
			}
		} catch (ParseException | NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : updateUser - " + e.getMessage());
			// Tiến hành rollback
			tblUserDaoImpl.rollBack();
			// Ném ra 1 lỗi
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
	 * @see manageuser.logics.TblUserLogic#deleteUser(int, boolean)
	 */
	@Override
	public boolean deleteUser(int userId) throws ClassNotFoundException, SQLException {
		boolean result = false;
		// Tạo đối tượng TblUserDaoImpl
		TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
		try {
			// Khởi tạo connection
			Connection connection = tblUserDaoImpl.connectDatabase();
			// Gán thuộc tính AutoCommit bằng false
			tblUserDaoImpl.setAutoCommit(false);
			// Tạo đối tượng TblDetailUserDaoImpl
			TblDetailUserJapanDao tblDetailUserDaoImpl = new TblDetailUserJapanDaoImpl();
			// Gán giá trị connection để thực hiện truy vấn
			tblDetailUserDaoImpl.setConnection(connection);
			// Xóa thông tin chi tiết người dùng trong CSDL
			tblDetailUserDaoImpl.deleteDetailUserJapan(userId);
			// Xóa thông tin người dùng trong CSDL
			if (tblUserDaoImpl.deleteUser(userId)) {
				// Tiến hành commit
				tblUserDaoImpl.commit();
				result = true;
				// Nếu là người dùng là admin thì tiến hành rollBack()
			} else {
				// Tiến hành rollback
				tblUserDaoImpl.rollBack();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : deleteUser - " + e.getMessage());
			// Tiến hành rollback
			tblUserDaoImpl.rollBack();
			// Ném ra 1 lỗi
			throw e;
			// Đóng kết nối
		} finally {
			tblUserDaoImpl.closeConnection();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkExistedUserInfor(int)
	 */
	@Override
	public boolean checkExistedUser(int userId) throws ClassNotFoundException, SQLException {
		try {
			// Tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
			// Gọi đến hàm checkExistedUser() và trả về kết quả
			return tblUserDaoImpl.checkExistedUser(userId);
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : checkExistedUser - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getUserInforById(int)
	 */
	public UserInfor getUserInforById(int userId) throws ClassNotFoundException, SQLException {
		try {
			// Tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
			// Gọi đến hàm getUserInforById() và trả về kết quả
			return tblUserDaoImpl.getUserInforById(userId);
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : getUserInforById - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#changePassword(manageuser.entities.
	 * TblUser)
	 */
	@Override
	public boolean changePassword(int userId, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		try {
			// Tạo đối tượng TblUserDaoImpl
			TblUserDao tblUserDaoImpl = new TblUserDaoImpl();
			// Tạo đối tượng TblUser
			TblUser tblUser = new TblUser();
			// Set thuộc tính userId
			tblUser.setUserId(userId);
			// Tạo salt
			String salt = Common.creatSalt(Constant.SALT_LENGHT);
			// Set thuộc tính salt
			tblUser.setSalt(salt);
			// Mã hóa mật khẩu
			password = Common.encrypt(password, salt);
			// Set thuộc tính password
			tblUser.setPassword(password);
			// Gọi đến hàm changePassword() và trả về kết quả
			return tblUserDaoImpl.changePassword(tblUser);
		} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblUserLogicImpl : changePassword - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
	}

}
