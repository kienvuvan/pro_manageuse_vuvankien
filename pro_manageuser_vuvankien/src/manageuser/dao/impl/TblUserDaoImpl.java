/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserDaoImpl.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;

import manageuser.dao.TblUserDao;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.utils.Constant;

/**
 * Class thực thi interface TblUserDao đê thực hiện các thao tác CSDL với đối
 * tượng TblUser
 * 
 * @author kien vu
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {
	private static final String GET_USER_ADMIN_BY_ID = "SELECT pass, salt FROM tbl_user WHERE rule = 0 AND login_name = ?";
	private static final String CHECK_EXITS_USERNAME = "SELECT COUNT(*) FROM tbl_user WHERE login_name = ?";
	private static final String CHECK_EXITS_EMAIL = "SELECT COUNT(*) FROM tbl_user WHERE email = ?";
	private static final String GET_TOTAL_USERS = "SELECT COUNT(*) as number " + "FROM tbl_user "
			+ "INNER JOIN mst_group " + "ON mst_group.group_id = tbl_user.group_id "
			+ "LEFT JOIN (tbl_detail_user_japan " + "INNER JOIN mst_japan "
			+ "ON mst_japan.code_level = tbl_detail_user_japan.code_level ) "
			+ "ON tbl_user.user_id = tbl_detail_user_japan.user_id WHERE 1=1 ";
	private static final String GET_LIST_USER = "SELECT tbl_user.user_id,tbl_user.full_name,tbl_user.birthday,"
			+ "mst_group.group_name, tbl_user.email,tbl_user.tel, mst_japan.name_level, "
			+ "tbl_detail_user_japan.end_date, tbl_detail_user_japan.total " + "FROM tbl_user "
			+ "INNER JOIN mst_group " + "ON mst_group.group_id = tbl_user.group_id "
			+ "LEFT JOIN (tbl_detail_user_japan "
			+ "INNER JOIN mst_japan  ON mst_japan.code_level = tbl_detail_user_japan.code_level )"
			+ " ON tbl_user.user_id = tbl_detail_user_japan.user_id WHERE 1=1 ";
	private static final String INSERT_USER = "INSERT INTO tbl_user(group_id, login_name,pass, full_name, full_name_kana, tel, email, birthday, rule,salt) VALUES(?,?,?,?,?,?,?,?,?,?);";

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private static HashMap<Integer, String> hashMapColumnSort = new HashMap<>();

	/*
	 * (non-Javadoc)
	 * @see manageuser.dao.TblUserDao#getUserByLogIn(java.lang.String)
	 */
	@Override
	public TblUser getUserByLogIn(String username) throws ClassNotFoundException, SQLException {
		TblUser tblUser = new TblUser();
		try {
			// Tạo kết nối với database
			connection = connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn lấy ra tài khoản có tên tài khoản tồn tại
				// trong CSDL và có quyền admin
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(GET_USER_ADMIN_BY_ID);
				preparedStatement.setString(1, username);
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu tài khoản tồn tại
				if (resultSet.next()) {
					int index = 1;
					// Lấy thông tin pass
					tblUser.setPassword(resultSet.getString(index++));
					// Lấy thông tin phần salt để tạo ra pass
					tblUser.setSalt(resultSet.getString(index++));
				}
			}
			// Lỗi
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		// Đóng kết nối
		} finally {
			closeConnection();
		}
		// Tài khoản hoặc mật khẩu sai
		return tblUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getTotalUsers(int, java.lang.String)
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException {
		try {
			// Tạo kết nối với CSDL
			connection = connectDatabase();
			// Nếu tạo kết nối thành công
			if (connection != null) {
				// Câu lệnh truy vấn tổng bản ghi
				StringBuilder stringBuilderQueryUser = new StringBuilder(GET_TOTAL_USERS);
				// Nếu có thêm phần tìm kiếm theo group
				if (groupId > 0) {
					// Thêm điều kiện tìm kiếm theo group
					stringBuilderQueryUser.append(" AND mst_group.group_id = ?");
				}
				// Nếu có thêm phần tìm kiếm theo tên
				if (!"".equals(fullName)) {
					// Thêm điều kiện tìm kiếm theo tên
					stringBuilderQueryUser.append(" AND tbl_user.full_name LIKE ?");
				}
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(stringBuilderQueryUser.toString());
				int index = 1;
				// Set các tham số truyền vào câu lệnh truy vấn
				if (groupId > 0) {
					preparedStatement.setInt(index++, groupId);
				}
				if (!"".equals(fullName)) {
					preparedStatement.setString(index++, "%" + fullName + "%");
				}
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		// Đóng kết nối
		} finally {
			closeConnection();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getListUsers(int, int, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException {
		// Tạo đối tượng ArrayList để lưu trữ
		ArrayList<UserInfor> listUserInfor = new ArrayList<>();
		try {
			// Tạo kết nối CSDL
			connection = connectDatabase();
			// Nếu kết nối thành công
			if (connection != null) {
				// Câu lệnh truy vấn lấy thông tin người dùng
				StringBuilder stringBuilderQueryUser = new StringBuilder(GET_LIST_USER);
				// Nếu có thêm phần tìm kiếm theo group
				if (groupId > 0) {
					// Thêm điều kiện tìm kiếm theo group
					stringBuilderQueryUser.append(" AND mst_group.group_id = ?");
				}
				// Nếu có thêm phần tìm kiếm theo tên
				if (!"".equals(fullName)) {
					// Thêm điều kiện tìm kiếm theo tên
					stringBuilderQueryUser.append(" AND tbl_user.full_name LIKE ?");
				}
				// Thêm phần sắp xếp và vị trị lấy bản ghi
				stringBuilderQueryUser.append(getOrderBy(sortType, sortByFullName, sortByCodeLevel, sortByEndDate));
				stringBuilderQueryUser.append(" LIMIT " + offset + " , " + limit);
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(stringBuilderQueryUser.toString());
				int index = 1;
				// Set các tham số truyền vào câu lệnh truy vấn
				if (groupId > 0) {
					preparedStatement.setInt(index++, groupId);
				}
				if (!"".equals(fullName)) {
					preparedStatement.setString(index++, "%" + fullName + "%");
				}
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					index = 1;
					// Lấy các thông tin từ kết quả truy vấn
					int userId = resultSet.getInt(index++);
					String name = resultSet.getString(index++);
					Date birthday = resultSet.getDate(index++);
					String groupName = resultSet.getString(index++);
					String email = resultSet.getString(index++);
					String tel = resultSet.getString(index++);
					String nameLevel = resultSet.getString(index++);
					Date endDate = resultSet.getDate(index++);
					int total = resultSet.getInt(index++);
					// Set đối tượng UserInfor với các thông số lấy được
					UserInfor userInfor = new UserInfor();
					userInfor.setUserId(userId);
					userInfor.setFullName(name);
					if (birthday != null) {
						userInfor.setBirthday(simpleDateFormat.format(birthday));
					} else {
						userInfor.setBirthday("");
					}
					userInfor.setGroupName(groupName);
					userInfor.setEmail(email);
					userInfor.setTel(tel);
					userInfor.setNameLevel(nameLevel);
					if (endDate != null) {
						userInfor.setEndDate(simpleDateFormat.format(endDate));
					} else {
						userInfor.setEndDate("");
					}
					userInfor.setTotalScore(total + "");
					// Thêm vào danh sách
					listUserInfor.add(userInfor);
				}
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		// Đóng kết nối
		} finally {
			closeConnection();
		}
		// Trả về danh sách UserInfor
		return listUserInfor;
	}

	/**
	 * Phương thức tạo chuỗi sắp xếp
	 * 
	 * @param sortType
	 *            Giá trị cột sắp xếp ưu tiên
	 * @param sortByFullName
	 *            Kiểu sắp xếp theo tên (ASC, DESC)
	 * @param sortByCodeLevel
	 *            Kiểu sắp xếp theo trình độ (ASC, DESC)
	 * @param sortByEndDate
	 *            Kiểu sắp xếp theo ngày hết hạn (ASC, DESC)
	 * @return Chuỗi sắp xêp
	 * @throws ClassNotFoundException,
	 *             SQLException
	 */
	private String getOrderBy(String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException {
		String orderBy = "";
		try {
			// Nếu hashMap rỗng
			if (hashMapColumnSort.isEmpty()) {
				// Get map
				hashMapColumnSort = getColumnSort();
			}
			switch (sortType) {
			case Constant.FULL_NAME_COLUMN:
				orderBy = " ORDER BY " + getData(Constant.FULL_NAME_COLUMN) + " " + sortByFullName + " , "
						+ getData(Constant.CODE_LEVEL_COLUMN) + " " + sortByCodeLevel + " , "
						+ getData(Constant.END_DATE_COLUMN) + " " + sortByEndDate;
				break;
			// Nếu giá trị cột sắp xếp ưu tiên là trình độ
			case Constant.CODE_LEVEL_COLUMN:
				// Cột code_level sẽ được xếp lên đầu
				orderBy = " ORDER BY " + getData(Constant.CODE_LEVEL_COLUMN) + " " + sortByCodeLevel + " , "
						+ getData(Constant.FULL_NAME_COLUMN) + " " + sortByFullName + " , "
						+ getData(Constant.END_DATE_COLUMN) + " " + sortByEndDate;
				break;
			// Nếu giá trị cột sắp xếp ưu tiên là ngày hết hạn
			case Constant.END_DATE_COLUMN:
				// Cột end_date sẽ được xếp lên đầu
				orderBy = " ORDER BY " + getData(Constant.END_DATE_COLUMN) + " " + sortByEndDate + " , "
						+ getData(Constant.CODE_LEVEL_COLUMN) + " " + sortByCodeLevel + " , "
						+ getData(Constant.FULL_NAME_COLUMN) + " " + sortByFullName;
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		// Trả về chuỗi sắp xếp
		return orderBy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#checkExitsUsername(java.lang.String)
	 */
	@Override
	public boolean checkExitsUsername(String userName) throws ClassNotFoundException, SQLException {
		try {
			// Tạo kết nối với database
			connection = connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn lấy ra tài khoản có tên tài khoản tồn tại
				// trong CSDL và có quyền admin
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(CHECK_EXITS_USERNAME);
				preparedStatement.setString(1, userName);
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu tài khoản tồn tại
				if (resultSet.next()) {
					// Nếu tồn tại
					if (resultSet.getInt(1) > 0) {
						return true;
					}
				}
			}
			// Lỗi
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		// Đóng kết nối
		} finally {
			closeConnection();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#checkExitsEmail(java.lang.String)
	 */
	@Override
	public boolean checkExitsEmail(String email) throws ClassNotFoundException, SQLException {
		try {
			// Tạo kết nối với database
			connection = connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn lấy ra tài khoản có tên tài khoản tồn tại
				// trong CSDL và có quyền admin
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(CHECK_EXITS_EMAIL);
				preparedStatement.setString(1, email);
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu tài khoản tồn tại
				if (resultSet.next()) {
					// Nếu tồn tại
					if (resultSet.getInt(1) > 0) {
						return true;
					}
				}
			}
			// Lỗi
		} catch (SQLException | ClassNotFoundException e) {
			e.getMessage();
			throw e;
			// Đóng kết nối
		} finally {
			closeConnection();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#insertUser(manageuser.entities.TblUser)
	 */
	@Override
	public int insertUser(TblUser tblUser) throws SQLException {
		try {
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn thêm User vào trong CSDL
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(INSERT_USER);
				int index = 1;
				// Gán các giá trị cho các tham số của câu lệnh truy vấn
				preparedStatement.setInt(index++, tblUser.getGroupId());
				preparedStatement.setString(index++, tblUser.getLoginName());
				preparedStatement.setString(index++, tblUser.getPassword());
				preparedStatement.setString(index++, tblUser.getFullName());
				preparedStatement.setString(index++, tblUser.getFullNameKana());
				preparedStatement.setString(index++, tblUser.getTel());
				preparedStatement.setString(index++, tblUser.getEmail());
				preparedStatement.setDate(index++, tblUser.getBirthday());
				preparedStatement.setInt(index++, tblUser.getRule());
				preparedStatement.setString(index++, tblUser.getSalt());
				// Nếu truy vấn thêm thành công
				if (preparedStatement.executeUpdate() > 0) {
					// Trả về giá trị id cuối cùng vừa thêm vào
					return (int) preparedStatement.getLastInsertID();
				}
			}
			// Lỗi
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return -1;
	}

	/**
	 * Phương thức lấy ra giá trị trong map
	 * 
	 * @param value
	 *            giá trị kiểm tra để lấy
	 * @return giá trị trong map
	 */
	public static String getData(String value) {
		String result = "";
		if (hashMapColumnSort.containsValue(value)) {
			result = value;
		}
		return result;
	}
}
