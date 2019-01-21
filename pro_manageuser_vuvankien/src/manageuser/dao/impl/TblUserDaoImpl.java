/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserDaoImpl.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import manageuser.dao.TblUserDao;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class thực thi interface TblUserDao đê thực hiện các thao tác CSDL với đối
 * tượng TblUser
 * 
 * @author kien vu
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {
	// Câu lệnh truy vấn lấy ra thông tin mật khẩu, salt của tài khoản admin với
	// tên đăng nhập
	private static final String GET_USER_ADMIN_BY_LOGIN_NAME = "SELECT pass, salt FROM tbl_user WHERE rule = ? AND BINARY login_name = ?";
	// Câu lệnh kiểm tra tài khoản tồn tại
	private static final String CHECK_EXITS_USERNAME = "SELECT user_id FROM tbl_user WHERE login_name = ?";
	// Câu lệnh kiểm tra email tồn tại
	private static final String CHECK_EXITS_EMAIL = "SELECT user_id FROM tbl_user WHERE email = ? ";
	// Câu lệnh lấy ra tổng số User
	private static final String GET_TOTAL_USERS = "SELECT COUNT(*) as number " + "FROM tbl_user "
			+ "INNER JOIN mst_group " + "ON mst_group.group_id = tbl_user.group_id "
			+ "LEFT JOIN (tbl_detail_user_japan " + "INNER JOIN mst_japan "
			+ "ON mst_japan.code_level = tbl_detail_user_japan.code_level ) "
			+ "ON tbl_user.user_id = tbl_detail_user_japan.user_id WHERE 1=1 ";
	// Câu lệnh lấy ra thông tin User
	private static final String GET_LIST_USER = "SELECT tbl_user.user_id,tbl_user.full_name,tbl_user.birthday,"
			+ "mst_group.group_name, tbl_user.email,tbl_user.tel, mst_japan.name_level, "
			+ "tbl_detail_user_japan.end_date, tbl_detail_user_japan.total " + "FROM tbl_user "
			+ "INNER JOIN mst_group " + "ON mst_group.group_id = tbl_user.group_id "
			+ "LEFT JOIN (tbl_detail_user_japan "
			+ "INNER JOIN mst_japan  ON mst_japan.code_level = tbl_detail_user_japan.code_level )"
			+ " ON tbl_user.user_id = tbl_detail_user_japan.user_id WHERE 1=1 ";
	// Câu lệnh truy vấn chèn thêm 1 đối tượng User vào trong bảng tbl_user
	private static final String INSERT_USER = "INSERT INTO tbl_user(group_id, login_name,pass, full_name, full_name_kana, tel, email, birthday, rule,salt) VALUES(?,?,?,?,?,?,?,?,?,?);";
	// Câu lệnh truy vấn sửa 1 đối tượng User vào trong bảng tbl_user với
	// user_id truyền vào
	private static final String EDIT_USER = "UPDATE tbl_user SET group_id = ?, full_name = ?, full_name_kana = ?, tel = ?, email = ?, birthday = ? WHERE user_id = ?";
	// Câu lệnh truy vấn xóa đối tượng User trong bảng tbl_user
	private static final String DELETE_USER = "DELETE FROM tbl_user WHERE user_id = ? AND rule != ?";
	// Câu lệnh truy vấn thay đổi mật khẩu đối tượng User (cả admin) trong bảng
	// tbl_user
	private static final String CHANGE_PASSWORD = "UPDATE tbl_user SET pass = ?, salt = ? WHERE user_id = ?";
	// Câu lệnh truy vấn kiểm tra tồn tại đối tượng User trong bảng tbl_user
	private static final String CHECK_EXISTED_USER = "SELECT COUNT(*) FROM tbl_user WHERE user_id = ?";
	// Câu lệnh truy vấn lấy thông tin UserInfor trong bảng tbl_user
	private static final String GET_USERINFOR_BY_ID = "SELECT tbl_user.login_name,mst_group.group_id,mst_group.group_name,tbl_user.full_name,tbl_user.full_name_kana,"
			+ "tbl_user.birthday,tbl_user.email,tbl_user.tel,mst_japan.code_level, mst_japan.name_level, "
			+ "tbl_detail_user_japan.start_date,tbl_detail_user_japan.end_date, tbl_detail_user_japan.total, tbl_user.rule "
			+ "FROM tbl_user " + "INNER JOIN mst_group " + "ON mst_group.group_id = tbl_user.group_id "
			+ "LEFT JOIN (tbl_detail_user_japan "
			+ "INNER JOIN mst_japan  ON mst_japan.code_level = tbl_detail_user_japan.code_level )"
			+ " ON tbl_user.user_id = tbl_detail_user_japan.user_id WHERE tbl_user.user_id = ?";
	// Định dạng ngày tháng năm theo kiểu yyyy/MM/dd
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	// Định dạng ngày tháng năm theo kiểu yyyy-MM-dd
	private static SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	// Map chưa tên các cột của các bảng trong CSDL
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
			connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn lấy ra tài khoản có tên tài khoản tồn tại
				// trong CSDL và có quyền admin
				PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ADMIN_BY_LOGIN_NAME);
				int index = 1;
				// Set tham số rule cho câu lệnh truy vấn
				preparedStatement.setInt(index++, Constant.RULE_ADMIN);
				// Set tham số login_name cho câu lệnh truy vấn
				preparedStatement.setString(index++, username);
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu tài khoản tồn tại
				if (resultSet.next()) {
					index = 1;
					// Lấy thông tin pass
					tblUser.setPassword(resultSet.getString(index++));
					// Lấy thông tin phần salt để tạo ra pass
					tblUser.setSalt(resultSet.getString(index++));
				}
			}
			// Lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
			// Đóng kết nối
		} finally {
			closeConnection();
		}
		// Trả về kết quả TblUser
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
			connectDatabase();
			// Nếu tạo kết nối thành công
			if (connection != null) {
				// Câu lệnh truy vấn tổng bản ghi
				StringBuilder stringBuilderQueryUser = new StringBuilder(GET_TOTAL_USERS);
				// Nếu có thêm phần tìm kiếm theo group
				if (groupId > Constant.GROUP_ID_DEFAULT) {
					// Thêm điều kiện tìm kiếm theo group
					stringBuilderQueryUser.append(" AND mst_group.group_id = ?");
				}
				// Nếu có thêm phần tìm kiếm theo tên
				if (!Common.isEmpty(fullName)) {
					// Thêm điều kiện tìm kiếm theo tên
					stringBuilderQueryUser.append(" AND tbl_user.full_name LIKE ?");
				}
				// Truyền câu lệnh truy vấn vào PreparedStatement
				PreparedStatement preparedStatement = connection.prepareStatement(stringBuilderQueryUser.toString());
				int index = 1;
				// Set các tham số truyền vào câu lệnh truy vấn
				// Nếu có phần tìm kiếm theo group
				if (groupId > Constant.GROUP_ID_DEFAULT) {
					// Set tham số groupId cho câu lệnh truy vấn
					preparedStatement.setInt(index++, groupId);
				}
				// Nếu có phần tìm kiếm theo tên
				if (!Common.isEmpty(fullName)) {
					// Set tham số fullName vào câu lệnh truy vấn
					preparedStatement.setString(index++, "%" + fullName + "%");
				}
				// Trả về kết quả các bản ghi của câu lệnh truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu có bản ghi được trả về
				if (resultSet.next()) {
					// Trả về kết quả giá trị bản ghi
					return resultSet.getInt(1);
				}
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
			// Đóng kết nối
		} finally {
			closeConnection();
		}
		// Trả về giá trị 0
		return Constant.NUMBER_ZERO;
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
			connectDatabase();
			// Nếu kết nối thành công
			if (connection != null) {
				// Câu lệnh truy vấn lấy thông tin người dùng
				StringBuilder stringBuilderQueryUser = new StringBuilder(GET_LIST_USER);
				// Nếu có thêm phần tìm kiếm theo group
				if (groupId > Constant.GROUP_ID_DEFAULT) {
					// Thêm điều kiện tìm kiếm theo group
					stringBuilderQueryUser.append(" AND mst_group.group_id = ?");
				}
				// Nếu có thêm phần tìm kiếm theo tên
				if (!Common.isEmpty(fullName)) {
					// Thêm điều kiện tìm kiếm theo tên
					stringBuilderQueryUser.append(" AND tbl_user.full_name LIKE ?");
				}
				// Thêm phần sắp xếp và vị trị lấy bản ghi
				stringBuilderQueryUser.append(getOrderBy(sortType, sortByFullName, sortByCodeLevel, sortByEndDate));
				// Thêm phần vị trị và giới hạn bản ghi lấy ra
				stringBuilderQueryUser.append(" LIMIT " + offset + " , " + limit);
				// Truyền câu lệnh truy vấn vào PreparedStatement
				PreparedStatement preparedStatement = connection.prepareStatement(stringBuilderQueryUser.toString());
				int index = 1;
				// Set các tham số truyền vào câu lệnh truy vấn
				// Nếu có tìm kiếm theo groupId
				if (groupId > Constant.GROUP_ID_DEFAULT) {
					// Set tham số groupId cho câu lệnh truy vấn
					preparedStatement.setInt(index++, groupId);
				}
				// Nếu có tìm kiếm theo fullName
				if (!Common.isEmpty(fullName)) {
					// Set tham số fullName cho câu lệnh truy vấn
					preparedStatement.setString(index++, "%" + fullName + "%");
				}
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Duyệt danh sách các bản ghi trả về
				while (resultSet.next()) {
					index = 1;
					// Lấy các thông tin từ kết quả truy vấn
					// Lấy ra giá trị userId
					int userId = resultSet.getInt(index++);
					// Lấy ra giá trị fullName
					String name = resultSet.getString(index++);
					// Lấy ra giá trị birthday
					Date birthday = resultSet.getDate(index++);
					// Lấy ra giá trị groupName
					String groupName = resultSet.getString(index++);
					// Lấy ra giá trị email
					String email = resultSet.getString(index++);
					// Lấy ra giá trị tel
					String tel = resultSet.getString(index++);
					// Lấy ra giá trị nameLevel
					String nameLevel = resultSet.getString(index++);
					// Lấy ra giá trị endDate
					Date endDate = resultSet.getDate(index++);
					// Lấy ra giá trị total
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
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
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
			// Kiểm tra cột sắp xếp ưu tiên
			switch (sortType) {
			// Nếu giá trị cột sắp xếp ưu tiên là tên
			case Constant.FULL_NAME_COLUMN:
				// Giá trị order by của cột fullName được đặt trước
				orderBy = " ORDER BY " + getData(Constant.FULL_NAME_COLUMN) + " " + sortByFullName + " , "
						+ getData(Constant.CODE_LEVEL_COLUMN) + " " + sortByCodeLevel + " , "
						+ getData(Constant.END_DATE_COLUMN) + " " + sortByEndDate;
				break;
			// Nếu giá trị cột sắp xếp ưu tiên là trình độ
			case Constant.CODE_LEVEL_COLUMN:
				// Giá trị order by của cột codeLevel được đặt trước
				orderBy = " ORDER BY " + getData(Constant.CODE_LEVEL_COLUMN) + " " + sortByCodeLevel + " , "
						+ getData(Constant.FULL_NAME_COLUMN) + " " + sortByFullName + " , "
						+ getData(Constant.END_DATE_COLUMN) + " " + sortByEndDate;
				break;
			// Nếu giá trị cột sắp xếp ưu tiên là ngày hết hạn
			case Constant.END_DATE_COLUMN:
				// Giá trị order by của cột endDate được đặt trước
				orderBy = " ORDER BY " + getData(Constant.END_DATE_COLUMN) + " " + sortByEndDate + " , "
						+ getData(Constant.CODE_LEVEL_COLUMN) + " " + sortByCodeLevel + " , "
						+ getData(Constant.FULL_NAME_COLUMN) + " " + sortByFullName;
				break;
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
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
	public int checkExitsUsername(String userName) throws ClassNotFoundException, SQLException {
		try {
			// Tạo kết nối với database
			connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn lấy ra tài khoản có tên tài khoản tồn tại
				// trong CSDL không?
				PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EXITS_USERNAME);
				int index = 1;
				preparedStatement.setString(index++, userName);
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu tài khoản tồn tại
				if (resultSet.next()) {
					// Nếu tồn tại trong CSDL thì trả về giá trị id
					return resultSet.getInt(1);
				}
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
			// Đóng kết nối
		} finally {
			closeConnection();
		}
		// Trả về -1 nếu không tồn tại trong CSDL
		return Constant.ID_NOT_EXISTED_LOGIN_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#checkExitsEmail(java.lang.String)
	 */
	@Override
	public boolean checkExitsEmail(String email, int userId) throws ClassNotFoundException, SQLException {
		try {
			// Tạo kết nối với database
			connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo đối tượng StringBuilder chứa câu lệnh truy vấn kiểm tra
				// email tồn tại
				StringBuilder queryCheckExitsEmail = new StringBuilder(CHECK_EXITS_EMAIL);
				// Nếu là trường hợp sửa thông tin
				if (userId > Constant.ID_ADD_USER) {
					// Thêm điều kiện kiểm tra user_id khác giá trị userId
					// truyền vào
					queryCheckExitsEmail.append(" AND user_id != ?");
				}
				// Tạo lệnh truy vấn kiểm tra email có tồn tại trong CSDL không
				int index = 1;
				// Truyền câu lệnh truy vấn vào trong PreparedStatement
				PreparedStatement preparedStatement = connection.prepareStatement(queryCheckExitsEmail.toString());
				// Truyền giá trị email vào tham số đầu trong câu lệnh truy vấn
				preparedStatement.setString(index++, email);
				// Nếu là trường hợp sửa thông tin
				if (userId > Constant.ID_ADD_USER) {
					// Truyền giá trị userId vào tham số tiếp theo trong câu
					// lệnh truy vấn
					preparedStatement.setInt(index++, userId);
				}
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu email tồn tại
				if (resultSet.next()) {
					// Trả về true nếu email đó tồn tại trong CSDL
					return true;
				}
			}
			// Lỗi
		} catch (SQLException | ClassNotFoundException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
			// Đóng kết nối
		} finally {
			closeConnection();
		}
		// Trả về false nếu không tồn tại
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
			// Nếu connection khác null
			if (connection != null) {
				// Tạo lệnh truy vấn thêm User vào trong CSDL
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
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
					ResultSet resultSet = preparedStatement.getGeneratedKeys();
					if (resultSet.next()) {
						// Trả về giá trị id cuối cùng vừa thêm vào
						return resultSet.getInt(1);
					}
				}
			}
			// Nếu có lỗi
		} catch (SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
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
		// Nếu value tồn tại trong HashMap
		if (hashMapColumnSort.containsValue(value)) {
			result = value;
		}
		// Trả về kết quả
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
			connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn kiểm tra người dùng có userId có tồn trong
				// CSDL hay không
				PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EXISTED_USER);
				// Truyền tham số userId cho câu lệnh truy vấn
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
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
			// Đóng connection
		} finally {
			closeConnection();
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
			connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn lấy ra thông tin người dùng có userId
				PreparedStatement preparedStatement = connection.prepareStatement(GET_USERINFOR_BY_ID);
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
					String total = resultSet.getString(index++);
					int rule = resultSet.getInt(index++);
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
					// Nếu ngày sinh tồn tại thì chuyển về kiểu hiển thị định
					// dạng yyyy-MM-dd
					if (birthday != null) {
						userInfor.setBirthday(simpleDateFormat2.format(birthday));
						// Ngược lại set chuỗi ""
					} else {
						userInfor.setBirthday("");
					}
					// Set thuộc tính email
					userInfor.setEmail(email);
					// Set thuộc tính tel
					userInfor.setTel(tel);
					// Set thuộc tính codeLevel
					userInfor.setCodeLevel(codeLevel);
					// Set thuộc tính nameLevel
					userInfor.setNameLevel(nameLevel);
					// Nếu ngày cấp chứng chỉ tồn tại thì chuyển về kiểu hiển
					// thị định dạng yyyy-MM-dd
					if (startDate != null) {
						userInfor.setStartDate(simpleDateFormat2.format(startDate));
						// Ngược lại set chuỗi ""
					} else {
						userInfor.setStartDate("");
					}
					// Nếu ngày hết hạn tồn tại thì chuyển về kiểu hiển thị định
					// dạng yyyy-MM-dd
					if (endDate != null) {
						userInfor.setEndDate(simpleDateFormat2.format(endDate));
						// Ngược lại set chuỗi ""
					} else {
						userInfor.setEndDate("");
					}
					// Set thuộc tính total
					userInfor.setTotalScore(Common.formatString(total, ""));
					// Set thuộc tính rule
					userInfor.setRule(rule);
				}
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
			// Đóng connection
		} finally {
			closeConnection();
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
				PreparedStatement preparedStatement = connection.prepareStatement(EDIT_USER);
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
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#deleteUser(int)
	 */
	public boolean deleteUser(int userId) throws SQLException {
		try {
			// Nếu connection khác null
			if (connection != null) {
				// Tạo lệnh truy vấn xóa thông tin User trong trong CSDL
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
				// Gán giá trị userId cho các tham số của câu lệnh truy vấn
				int index = 1;
				preparedStatement.setInt(index++, userId);
				preparedStatement.setInt(index++, Constant.RULE_ADMIN);
				// Nếu truy vấn thêm thành công
				if (preparedStatement.executeUpdate() > 0) {
					// Trả về true
					return true;
				}
			}
			// Lỗi
		} catch (SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		// Nếu là xóa admin thì trả về false
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.dao.TblUserDao#changePassword(manageuser.entities.TblUser)
	 */
	public boolean changePassword(TblUser tblUser) throws ClassNotFoundException, SQLException {
		try {
			// Tạo kết nối CSDL
			connectDatabase();
			// Nếu connection khác null
			if (connection != null) {
				// Tạo lệnh truy vấn xóa thông tin User trong trong CSDL
				PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PASSWORD);
				int index = 1;
				// Gán giá trị userId cho các tham số của câu lệnh truy vấn
				preparedStatement.setString(index++, tblUser.getPassword());
				preparedStatement.setString(index++, tblUser.getSalt());
				preparedStatement.setInt(index++, tblUser.getUserId());
				// preparedStatement.setInt(1, userId);
				// Nếu truy vấn thêm thành công
				if (preparedStatement.executeUpdate() > 0) {
					// Trả về true
					return true;
				}
			}
			// Lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		return false;
	}
}
