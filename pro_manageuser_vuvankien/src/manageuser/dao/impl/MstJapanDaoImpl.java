/**
 * Copyright (C) 2018 Luvina Academy
 * MstJapanDaoImpl.java Dec 12, 2018, Vu Van Kien
 */
package manageuser.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.dao.MstJapanDao;
import manageuser.entities.MstJapan;

/**
 * Class thực thi interface MstJapanDao đê thực hiện các thao tác CSDL với đối
 * tượng MstJapan
 * 
 * @author kien vu
 *
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {

	// Câu lệnh truy vấn lấy ra tất cả các bản ghi của bảng mst_japn trong CSDL
	private static final String GET_ALL_MST_JAPAN = "SELECT * FROM mst_japan";
	// Câu lệnh truy vấn lấy ra tên của trình độ tiếng Nhật theo code_level
	private static final String GET_NAME_LEVEL_BY_CODE_LEVEL = "SELECT name_level FROM mst_japan WHERE code_level = ?";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstJapanDao#getAllMstJapan()
	 */
	@Override
	public ArrayList<MstJapan> getAllMstJapan() throws ClassNotFoundException, SQLException {
		// Tạo đối tượng ArrayList để lưu trữ
		ArrayList<MstJapan> listAllMstJapan = new ArrayList<>();
		try {
			// Tạo kết nối với CSDL
			connection = connectDatabase();
			// Nếu kết nối thành công
			if (connection != null) {
				// Tạo câu lệnh truy vấn
				PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_MST_JAPAN);
				// Trả về danh sách bản ghi sau khi truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Duyệt kết quả trả về
				while (resultSet.next()) {
					int index = 1;
					// Tạo đối tượng MstJapan
					MstJapan mstJapan = new MstJapan();
					// Lấy thông tin từ resultSet và gán vào đối tượng MstGroup
					mstJapan.setCodeLevel(resultSet.getString(index++));
					mstJapan.setNameLevel(resultSet.getString(index++));
					// Thêm vào danh sách
					listAllMstJapan.add(mstJapan);
				}
			}
		// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw e;
		// Đóng kết nối
		} finally {
			closeConnection();
		}
		// Trả về danh sách MstGroup
		return listAllMstJapan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstJapanDao#getCodeNameById(java.lang.String)
	 */
	@Override
	public String getNameLevelById(String codeLevel) throws ClassNotFoundException, SQLException {
		String result = "";
		try {
			// Tạo kết nối với CSDL
			connection = connectDatabase();
			// Nếu kết nối thành công
			if (connection != null) {
				// Truyền câu lệnh truy vấn vào PreparedStatement
				PreparedStatement preparedStatement = connection.prepareStatement(GET_NAME_LEVEL_BY_CODE_LEVEL);
				// Set tham số codeLevel cho câu lệnh truy vấn
				preparedStatement.setString(1, codeLevel);
				// Lấy ra kết quả bản ghi được trả về
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu có bản ghi được trả về
				if (resultSet.next()) {
					// Lấy ra tên trình độ tiếng Nhật và gán vào biến result
					result = resultSet.getString(1);
				}
			}
		// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw e;
		// Đóng kết nối
		} finally {
			closeConnection();
		}
		// Trả về kết quả
		return result;
	}

}
