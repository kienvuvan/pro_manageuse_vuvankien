/**
 * Copyright (C) 2018 Luvina Academy
 * MstGroupLogic.java Dec 13, 2018, Vu Van Kien
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.MstGroup;

/**
 * @author kien vu
 *
 */
public interface MstGroupLogic {

	/**
	 * Phương thức thực hiện xử lý lấy toàn bộ thông tin Group
	 * 
	 * @throws ClassNotFoundException,SQLException
	 */
	public ArrayList<MstGroup> getAllGroups() throws ClassNotFoundException, SQLException, Exception;
}
