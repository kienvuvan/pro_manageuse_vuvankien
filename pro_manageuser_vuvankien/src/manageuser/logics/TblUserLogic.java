/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserLogic.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;

/**
 * Interface thực hiện các chức năng logic với đối tượng TblUsers
 * @author kien vu
 *
 */
public interface TblUserLogic {
	/**
	 * Phương thức thực hiện xử lý kiểm tra tài khoản có tồn tại trong CSDL hay
	 * không?
	 * 
	 * @param username
	 *            giá trị tên tài khoản
	 * @param password
	 *            giá trị trường password
	 * @return true nếu tài khoản đúng và ngược lại
	 * @throws NoSuchAlgorithmException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean checkExitsAccount(String username, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException;

	/**
	 * Phuong thuc lay tong so User thoa man cac dieu kien tim kiem
	 * 
	 * @param groupId
	 *            gia tri groupId tim kiem
	 * @param fullName
	 *            gia tri ten tim kiem
	 * @return int so User thoa man dieu kien tim kiem
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException;

	/**
	 * Phuong thuc lay danh sach User thoa man dieu kien tim kiem va sap xep tu
	 * 1 vi tri trong danh sach ban ghi tra ve
	 * 
	 * @param offset
	 *            vi tri bat dau lay
	 * @param limit
	 *            so ban ghi can lay
	 * @param groupId
	 *            gia tri groupId tim kiem
	 * @param fullName
	 *            gia tri ten tim kiem
	 * @param sortType
	 *            kieu sap xep uu tien
	 * @param sortByFullName
	 *            kieu sap xep theo ten
	 * @param sortByCodeLevel
	 *            kieu sap xep theo trinh do
	 * @param sortByEndDate
	 *            kieu sap xep theo ngay het han
	 * @return ArrayList<UserInfor> danh sach UserInfor
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException;

	/**
	 * Phuong thuc lay User tu gia tri username
	 * 
	 * @param username
	 *            ten nguoi dung
	 * @return TblUser thong tin cua nguoi dung
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public TblUser getUserByLogIn(String username) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức kiểm tra tài khoản đã tồn tại trong CSDL hay chưa?
	 * 
	 * @param userName
	 *            chuỗi tài khoản cần kiểm tra
	 * @return true nếu đã tồn tại và ngược lại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean checkExitsUsername(String userName) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức kiểm tra email đã tồn tại trong CSDL hay chưa?
	 * 
	 * @param email
	 *            chuỗi email cần kiểm tra
	 * @return true nếu tồn tại và ngược lại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean checkExitsEmail(String email) throws ClassNotFoundException, SQLException;

	/**
	 * 
	 * @param userInfor
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean creatUser(UserInfor userInfor) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
}
