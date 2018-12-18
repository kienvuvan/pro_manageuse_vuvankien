/**
 * Copyright (C) 2018 Luvina Academy
 * MsrGroupDaoImpl.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import manageuser.dao.MstGroupDao;
import manageuser.entities.MstGroup;
import manageuser.utils.Constant;

/**
 * @author kien vu
 *
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {
	/**
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MstGroupDaoImpl() throws ClassNotFoundException, SQLException {
		super();
	}

	private Connection connection;
	private static final String GET_ALL_GROUP = "SELECT * FROM mst_group";

	/**
	 * Phương thức lấy ra danh sách toàn bộ thông tin group
	 * 
	 * @throws ClassNotFoundException,
	 *             SQLException
	 */
	@Override
	public ArrayList<MstGroup> getAllGroups() throws ClassNotFoundException, SQLException {
		// Tạo đối tượng ArrayList để lưu trữ 
		ArrayList<MstGroup> listAllGroup = new ArrayList<>();
		// Tạo đối tượng MstGroup với giá trị tất cả
		MstGroup mstGroupAll = new MstGroup();
		mstGroupAll.setGroupId(0);
		mstGroupAll.setGroupName(Constant.ALL);
		// Thêm vào danh sách
		listAllGroup.add(mstGroupAll);
		// Tạo kết nối với CSDL
		try {
			connection = connectDatabase();
			// Nếu kết nối thành công
			if (connection != null) {
				// Tạo câu lệnh truy vấn
				Statement statement = (Statement) connection.createStatement();
				// Trả về danh sách bản ghi sau khi truy vấn
				ResultSet resultSet = statement.executeQuery(GET_ALL_GROUP);
				//Duyệt kết quả trả về
				while (resultSet.next()) {
					int index = 1;
					MstGroup mstGroup = new MstGroup();
					// Lấy thông tin từ resultSet và gán vào đối tượng MstGroup
					mstGroup.setGroupId(resultSet.getInt(index++));
					mstGroup.setGroupName(resultSet.getString(index++));
					// Thêm vào danh sách
					listAllGroup.add(mstGroup);
				}
			}
		// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		// Trả về danh sách MstGroup
		return listAllGroup;
	}

}
