/**
 * Copyright (C) 2018 Luvina Academy
 * TblDetailUserDaoImpl.java Dec 12, 2018, Vu Van Kien
 */
package manageuser.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.entities.TblDetailUserJapan;

/**
 * Class thực thi interface TblDetailUserDao đê thực hiện các thao tác CSDL với
 * đối tượng TblDetailUser
 * 
 * @author kien vu
 *
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	// Câu lệnh truy vấn thêm thông tin chi tiết người dùng vào bảng
	// tbl_detail_user_japan trong CSDL
	private static final String INSERT_DETAIL_USER = "INSERT INTO tbl_detail_user_japan(user_id,code_level,start_date,end_date,total) VALUES(?,?,?,?,?);";
	// Câu lệnh truy vấn sửa thông tin chi tiết người dùng của bảng
	// tbl_detail_user_japan trong CSDL
	private static final String EDIT_DETAIL_USER = "UPDATE tbl_detail_user_japan SET code_level = ?, start_date = ?, end_date = ?, total = ? WHERE user_id = ?";
	// Câu lệnh truy vấn xóa thông tin chi tiết người dùng của bảng
	// tbl_detail_user_japan trong CSDL
	private static final String DELETE_DETAIL_USER = "DELETE FROM tbl_detail_user_japan WHERE user_id = ?";
	// Câu lệnh truy vấn kiểm tra người dùng có thông tin chi tiết không?
	private static final String CHECK_EXISTED_DETAIL_USER = "SELECT user_id FROM tbl_detail_user_japan WHERE user_id = ?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.dao.TblDetailUserDao#insertDetailUserJapan(manageuser.entities
	 * .TblDetailUserJapan)
	 */
	@Override
	public boolean insertDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException {
		try {
			// Nếu kết nối không null
			if (connection != null) {
				// Tạo lệnh truy vấn thêm thông tin chi tiết User vào trong CSDL
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(INSERT_DETAIL_USER);
				int index = 1;
				// Gán các giá trị cho các tham số trong câu lệnh truy vấn
				preparedStatement.setInt(index++, tblDetailUserJapan.getUserId());
				preparedStatement.setString(index++, tblDetailUserJapan.getCodeLevel());
				preparedStatement.setDate(index++, tblDetailUserJapan.getStartDate());
				preparedStatement.setDate(index++, tblDetailUserJapan.getEndDate());
				preparedStatement.setInt(index++, tblDetailUserJapan.getTotal());
				// Nếu thêm thành công
				if (preparedStatement.executeUpdate() > 0) {
					return true;
				}
			}
			// Nếu có lỗi
		} catch (SQLException e) {
			// In ra lỗi
			System.out.println("TblDetailUserJapanDaoImpl : insertDetailUserJapan - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblDetailUserDao#checkExistedDetailUser(int)
	 */
	@Override
	public boolean checkExistedDetailUser(int userId) throws ClassNotFoundException, SQLException {
		try {
			// Tạo kết nối với CSDL
			connectDatabase();
			// Nếu kết nối không null
			if (connection != null) {
				// Tạo lệnh truy vấn thêm chi tiết User vào trong CSDL
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(CHECK_EXISTED_DETAIL_USER);
				// Gán các giá trị cho các tham số trong câu lệnh truy vấn
				preparedStatement.setInt(1, userId);
				// Trả về danh sách các bản ghi khi thực hiện truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu tồn tại bản ghi
				if (resultSet.next()) {
					return true;
				}
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println("TblDetailUserJapanDaoImpl : checkExistedDetailUser - " + e.getMessage());
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
	 * @see manageuser.dao.TblDetailUserJapanDao#editDetailUserJapan(manageuser.
	 * entities.TblDetailUserJapan)
	 */
	@Override
	public boolean editDetailUserJapan(TblDetailUserJapan tblDetailUserJapan) throws SQLException {
		try {
			// Nếu kết nối không null
			if (connection != null) {
				// Tạo lệnh truy vấn sửa thông tin chi tiết User vào trong CSDL
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(EDIT_DETAIL_USER);
				int index = 1;
				// Gán các giá trị cho các tham số trong câu lệnh truy vấn
				preparedStatement.setString(index++, tblDetailUserJapan.getCodeLevel());
				preparedStatement.setDate(index++, tblDetailUserJapan.getStartDate());
				preparedStatement.setDate(index++, tblDetailUserJapan.getEndDate());
				preparedStatement.setInt(index++, tblDetailUserJapan.getTotal());
				preparedStatement.setInt(index++, tblDetailUserJapan.getUserId());
				// Nếu thêm thành công
				if (preparedStatement.executeUpdate() > 0) {
					return true;
				}
			}
			// Nếu có lỗi
		} catch (SQLException e) {
			// In ra lỗi
			System.out.println("TblDetailUserJapanDaoImpl : editDetailUserJapan - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.dao.TblDetailUserJapanDao#deleteDetailUserJapan(manageuser.
	 * entities.TblDetailUserJapan)
	 */
	@Override
	public boolean deleteDetailUserJapan(int userId) throws SQLException {
		try {
			// Nếu kết nối không null
			if (connection != null) {
				// Tạo lệnh truy vấn xóa thông tin chi tiết User vào trong CSDL
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(DELETE_DETAIL_USER);
				// Gán các giá trị cho các tham số trong câu lệnh truy vấn
				preparedStatement.setInt(1, userId);
				// Nếu thêm thành công
				if (preparedStatement.executeUpdate() > 0) {
					return true;
				}
			}
			// Nếu có lỗi
		} catch (SQLException e) {
			// In ra lỗi
			System.out.println("TblDetailUserJapanDaoImpl : deleteDetailUserJapan - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		return false;
	}

}
