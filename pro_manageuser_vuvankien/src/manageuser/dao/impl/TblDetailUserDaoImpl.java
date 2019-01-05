/**
 * Copyright (C) 2018 Luvina Academy
 * TblDetailUserDaoImpl.java Dec 12, 2018, Vu Van Kien
 */
package manageuser.dao.impl;

import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import manageuser.dao.TblDetailUserDao;
import manageuser.entities.TblDetailUserJapan;

/**
 * Class thực thi interface TblDetailUserDao đê thực hiện các thao tác CSDL với
 * đối tượng TblDetailUser
 * 
 * @author kien vu
 *
 */
public class TblDetailUserDaoImpl extends BaseDaoImpl implements TblDetailUserDao {

	private static final String INSERT_DETAIL_USER = "INSERT INTO tbl_detail_user_japan(user_id,code_level,start_date,end_date,total) VALUES(?,?,?,?,?);";

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
				// Tạo lệnh truy vấn thêm chi tiết User vào trong CSDL
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
		} catch (SQLException e) {
			e.getMessage();
			throw e;
		}
		return false;
	}

}
