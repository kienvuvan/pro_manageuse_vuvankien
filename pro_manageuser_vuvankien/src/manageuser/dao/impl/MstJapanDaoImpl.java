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

	private static final String GET_ALL_MST_JAPAN = "SELECT * FROM mst_japan";
	private static final String GET_NAME_LEVEL_BY_ID = "SELECT name_level FROM mst_japan WHERE code_level = ?";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstJapanDao#getAllMstJapan()
	 */
	@Override
	public ArrayList<MstJapan> getAllMstJapan() throws ClassNotFoundException, SQLException {
		// Tạo đối tượng ArrayList để lưu trữ
		ArrayList<MstJapan> listAllMstJapan = new ArrayList<>();
		// Tạo kết nối với CSDL
		try {
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
			e.getMessage();
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
		// Tạo kết nối với CSDL
		try {
			connection = connectDatabase();
			// Nếu kết nối thành công
			if (connection != null) {
				PreparedStatement preparedStatement = connection.prepareStatement(GET_NAME_LEVEL_BY_ID);
				preparedStatement.setString(1, codeLevel);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					result = resultSet.getString(1);
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
		return result;
	}

}
