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
	private static final String CHECK_EXITS_EMAIL = "SELECT user_id FROM tbl_user WHERE email = ?";
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
	private static final String EDIT_USER = "UPDATE tbl_user SET group_id = ?, full_name = ?, full_name_kana = ?, tel = ?, email = ?, birthday = ? WHERE user_id = ?";
	private static final String CHECK_EXISTED_USER = "SELECT COUNT(*) FROM tbl_user WHERE user_id = ?";
	private static final String GET_USERINFOR_BY_ID = "SELECT tbl_user.login_name,mst_group.group_id,mst_group.group_name,tbl_user.full_name,tbl_user.full_name_kana,"
			+ "tbl_user.birthday,tbl_user.email,tbl_user.tel,mst_japan.code_level, mst_japan.name_level, "
			+ "tbl_detail_user_japan.start_date,tbl_detail_user_japan.end_date, tbl_detail_user_japan.total "
			+ "FROM tbl_user " + "INNER JOIN mst_group " + "ON mst_group.group_id = tbl_user.group_id "
			+ "LEFT JOIN (tbl_detail_user_japan "
			+ "INNER JOIN mst_japan  ON mst_japan.code_level = tbl_detail_user_japan.code_level )"
			+ " ON tbl_user.user_id = tbl_detail_user_japan.user_id WHERE tbl_user.user_id = ?";

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	private static HashMap<Integer, String> hashMapColumnSort = new HashMap<>();

	/*
	 * (non-Javadoc)
	 * 
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
	public int checkExitsEmail(String email) throws ClassNotFoundException, SQLException {
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
					return resultSet.getInt(1);
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
		return Constant.ID_NOT_EXISTED_EMAIL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#insertUser(manageuser.entities.TblUser)
	 */
	@Override
	public int insertUser(TblUser tblUser) throws SQLException {
		try {
			// Nếu connection khác null
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
		return Constant.ERROR_EXCUTE_DATABASE;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#checkExistedUserInfor(int)
	 */
	@Override
	public boolean checkExistedUser(int userId) throws ClassNotFoundException, SQLException {
		try {
			// Tạo kết nối với database
			connection = connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn kiểm tra người dùng có userId có tồn trong
				// CSDL hay không
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(CHECK_EXISTED_USER);
				preparedStatement.setInt(1, userId);
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Lấy ra giá trị bản ghi số lượng
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					// Nếu userId tồn tại
					if (count > 0) {
						return true;
					}
				}
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(e.toString());
			// Ném ra 1 lỗi
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getUserInforById(int)
	 */
	@Override
	public UserInfor getUserInforById(int userId) throws ClassNotFoundException, SQLException {
		// Tạo đối tượng UserInfor
		UserInfor userInfor = new UserInfor();
		try {
			// Tạo kết nối với database
			connection = connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn lấy ra thông tin người dùng có userId
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(GET_USERINFOR_BY_ID);
				preparedStatement.setInt(1, userId);
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				int index = 1;
				// Lấy ra giá trị bản ghi số lượng
				if (resultSet.next()) {
					// Lấy các thông tin từ kết quả truy vấn
					String loginName = resultSet.getString(index++);
					int groupId = resultSet.getInt(index++);
					String groupName = resultSet.getString(index++);
					String fullName = resultSet.getString(index++);
					String fullNameKana = resultSet.getString(index++);
					Date birthday = resultSet.getDate(index++);
					String email = resultSet.getString(index++);
					String tel = resultSet.getString(index++);
					String codeLevel = resultSet.getString(index++);
					String nameLevel = resultSet.getString(index++);
					Date startDate = resultSet.getDate(index++);
					Date endDate = resultSet.getDate(index++);
					int total = resultSet.getInt(index++);
					// Set đối tượng UserInfor với các thông số lấy được
					// Set thuộc tính userId
					userInfor.setUserId(userId);
					// Set thuộc tính loginName
					userInfor.setLoginName(loginName);
					// Set thuộc tính groupId
					userInfor.setGroupId(groupId);
					// Set thuộc tính groupName
					userInfor.setGroupName(groupName);
					// Set thuộc tính fullName
					userInfor.setFullName(fullName);
					// Set thuộc tính fullNameKana
					userInfor.setFullNameKana(fullNameKana);
					if (birthday != null) {
						userInfor.setBirthday(simpleDateFormat2.format(birthday));
					} else {
						userInfor.setBirthday("");
					}
					userInfor.setEmail(email);
					userInfor.setTel(tel);
					userInfor.setCodeLevel(codeLevel);
					userInfor.setNameLevel(nameLevel);
					if (startDate != null) {
						userInfor.setStartDate(simpleDateFormat2.format(startDate));
					} else {
						userInfor.setStartDate("");
					}
					if (endDate != null) {
						userInfor.setEndDate(simpleDateFormat2.format(endDate));
					} else {
						userInfor.setEndDate("");
					}
					userInfor.setTotalScore(total + "");
				}
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(e.toString());
			// Ném ra 1 lỗi
			throw e;
		}
		// Trả về đối tượng UserInfor
		return userInfor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#editUser(manageuser.entities.TblUser)
	 */
	@Override
	public boolean editUser(TblUser tblUser) throws SQLException {
		try {
			// Nếu connection khác null
			if (connection != null) {
				// Tạo lệnh truy vấn sửa thông tin User vào trong CSDL
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(EDIT_USER);
				int index = 1;
				// Gán các giá trị cho các tham số của câu lệnh truy vấn
				preparedStatement.setInt(index++, tblUser.getGroupId());
				preparedStatement.setString(index++, tblUser.getFullName());
				preparedStatement.setString(index++, tblUser.getFullNameKana());
				preparedStatement.setString(index++, tblUser.getTel());
				preparedStatement.setString(index++, tblUser.getEmail());
				preparedStatement.setDate(index++, tblUser.getBirthday());
				preparedStatement.setInt(index++, tblUser.getUserId());
				// Nếu truy vấn thêm thành công
				if (preparedStatement.executeUpdate() > 0) {
					// Trả về true
					return true;
				}
			}
			// Lỗi
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return false;
	}

}
