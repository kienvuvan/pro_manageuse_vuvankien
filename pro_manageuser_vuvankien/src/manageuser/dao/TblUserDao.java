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
 * Interface TblUserDao
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
	 * @param sortByFullName
	 *            giá trị sắp xếp theo tên (ASC hoặc DESC)
	 * @param sortByCodeLevel
	 *            giá trị sắp xếp theo trình độ (ASC hoặc DESC)
	 * @param sortByEndDate
	 *            giá trị sắp xếp theo ngày hết hạn (ASC hoặc DESC)
	 * @return ArrayList<UserInfor> danh sách thông tin user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức kiểm tra tài khoản đã tồn tại trong CSDL hay chưa?
	 * 
	 * @param userName
	 *            chuỗi tài khoản cần kiểm tra
	 * @return int trả về giá trị userId của email đó nếu tồn tại và ngược lại trả về -1
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int checkExitsUsername(String userName) throws ClassNotFoundException, SQLException;

	/**
	 * Phương thức kiểm tra email đã tồn tại trong CSDL hay chưa?
	 * 
	 * @param email
	 *            chuỗi email cần kiểm tra
	 * @return trả về giá trị userId của email đó và ngược lại trả về -1 nếu không tồn tại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int checkExitsEmail(String email) throws ClassNotFoundException, SQLException;
	
	/**
	 * Phương thức thực hiện thêm 1 đối tượng TblUser vào trong CSDL
	 * @param tblUser đối tượng tblUser cần thêm vào CSDL
	 * @return -1 nếu thêm bị lỗi và ngược lại trả về giá trị id của trường user_id trong CSDL
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public int insertUser(TblUser tblUser) throws SQLException;
	
	/**
	 * Phương thức thực hiện sửa thông tin người dùng trong CSDL
	 * @param tblUser thông tin mới cần được sửa
	 * @return true nếu sửa thành công và ngược lại
	 * @throws SQLException 
	 */
	public boolean editUser(TblUser tblUser) throws SQLException;
	
	/**
	 * Phương thức thực hiện chức năng xóa người dùng trong CSDL
	 * @param userId giá trị id của người dùng cần xóa
	 * @return true nếu xóa thành công và ngược lại
	 * @throws SQLException 
	 */
	public boolean deleteUser(int userId) throws SQLException;
	
	/**
	 * Phương thức thực hiện chức năng thay đổi mật khẩu người dùng
	 * @param tblUser đối tượng TblUser
	 * @return true nếu thay đổi thành công và ngược lại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean changePassword(TblUser tblUser) throws ClassNotFoundException, SQLException;
	
	/**
	 * Phương thức kiểm tra xem người dùng có tồn tại hay không trong CSDL
	 * @param userId id của người dùng cần kiểm tra
	 * @return true nếu người dùng có userId tồn tại trong CSDL và ngược lại
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkExistedUser(int userId) throws ClassNotFoundException, SQLException;
	
	/**
	 * Phương thức lấy ra thông tin người dùng
	 * @param userId giá trị Id của người dùng trong CSDL
	 * @return UserInfor thông tin người dùng
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public UserInfor getUserInforById(int userId) throws ClassNotFoundException, SQLException;
}
