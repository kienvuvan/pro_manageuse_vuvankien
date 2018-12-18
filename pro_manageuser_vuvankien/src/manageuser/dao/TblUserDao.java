/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserDao.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;

/**
 * @author kien vu
 *
 */
public interface TblUserDao extends BaseDao {

	/**
	 * Phương thức kiểm tra tài khoản có tồn tại không?
	 * 
	 * @param username
	 *            Tên tài khoản người dùng nhập
	 * @param password
	 *            Mật khẩu người dùng nhập
	 * @return true Nếu tài khoản đúng và ngược lại
	 * @exception NoSuchAlgorithmException
	 * @exception ClassNotFoundException
	 * @exception SQLException
	 * @throws Exception
	 */
	public TblUser getUserByLogIn(String username) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức trả về tổng các Users khi tìm kiếm
	 * 
	 * @param groupId
	 *            giá trị Group tìm kiếm
	 * @param fullName
	 *            giá trị tìm kiếm theo tên
	 * @return số lượng users
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức lấy thông tin UserInfor khi tìm kiếm và có sắp xếp và paging
	 * 
	 * @param offset
	 *            vị trí bắt đầu lấy
	 * @param limit
	 *            số lượng bản ghi lấy
	 * @param groupId
	 *            giá trị group tìm kiếm
	 * @param fullName
	 *            giá trị tên tìm kiếm
	 * @param sortType
	 *            Kiểu giá trị sắp xếp ưu tiên (Constant.FULL_NAME_COLUMN,
	 *            Constant.CODE_LEVEL_COLUMN, Constant.END_DATE_COLUMN)
	 * @param sortByFullName giá trị sắp xếp theo tên (ASC hoặc DESC)
	 * @param sortByCodeLevel giá trị sắp xếp theo trình độ (ASC hoặc DESC)
	 * @param sortByEndDate giá trị sắp xếp theo ngày hết hạn (ASC hoặc DESC)
	 * @return ArrayList<UserInfor> danh sách thông tin user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException;
}
