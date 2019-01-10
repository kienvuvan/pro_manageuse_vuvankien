/**
 * Copyright (C) 2018 Luvina Academy
 * MsrGroupDaoImpl.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.dao.MstGroupDao;
import manageuser.entities.MstGroup;

/**
 * Class thực thi interface MstGroupDao đê thực hiện các thao tác CSDL với đối
 * tượng MstGroup
 * 
 * @author kien vu
 *
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {

	// Câu lệnh truy vấn lấy ra tất cả các bản ghi của bảng mst_group trong CSDL
	private static final String GET_ALL_MST_GROUP = "SELECT * FROM mst_group";
	// Câu lệnh truy vấn lấy ra tên của group theo id của group
	private static final String GET_GROUP_NAME_BY_ID = "SELECT group_name FROM mst_group WHERE group_id = ?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstGroupDao#getAllGroups()
	 */
	@Override
	public ArrayList<MstGroup> getAllGroups() throws ClassNotFoundException, SQLException {
		// Tạo đối tượng ArrayList để lưu trữ
		ArrayList<MstGroup> listAllMstGroup = new ArrayList<>();
		try {
			// Tạo kết nối với CSDL
			connection = connectDatabase();
			// Nếu kết nối thành công
			if (connection != null) {
				// Truyền câu lệnh truy vấn vào trong PreparedStatement
				PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_MST_GROUP);
				// Trả về danh sách bản ghi sau khi truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Duyệt kết quả trả về
				while (resultSet.next()) {
					int index = 1;
					MstGroup mstGroup = new MstGroup();
					// Lấy thông tin từ resultSet và gán vào đối tượng MstGroup
					// Set thuộc tính groupId
					mstGroup.setGroupId(resultSet.getInt(index++));
					// Set thuộc tính groupName
					mstGroup.setGroupName(resultSet.getString(index++));
					// Thêm vào danh sách
					listAllMstGroup.add(mstGroup);
				}
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			// Ném ra 1 lỗi
			throw e;
		// Đóng kết nối
		} finally {
			closeConnection();
		}
		// Trả về danh sách MstGroup
		return listAllMstGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstGroupDao#getGroupNameById(java.lang.String)
	 */
	@Override
	public String getGroupNameById(int groupId) throws ClassNotFoundException, SQLException {
		String result = "";
		// Tạo kết nối với CSDL
		try {
			connection = connectDatabase();
			// Nếu kết nối thành công
			if (connection != null) {
				// Truyền câu lệnh truy vấn vào trong PreparedStatement
				PreparedStatement preparedStatement = connection.prepareStatement(GET_GROUP_NAME_BY_ID);
				// Set tham số groupId cho câu lệnh truy vấn
				preparedStatement.setInt(1, groupId);
				// Lấy ra kết quả bản ghi
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu có tồn tại bản ghi
				if (resultSet.next()) {
					// Lấy ra kết quả tên group
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
