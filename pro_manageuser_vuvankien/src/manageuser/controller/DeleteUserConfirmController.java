/**
 * Copyright (C) 2019 Luvina Academy
 * DeleteUserConfirmController.java Jan 9, 2019, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class thực hiện chức năng xóa thông tin người dùng
 * 
 * @author kien vu
 *
 */
public class DeleteUserConfirmController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Khởi tạo Session
			HttpSession session = request.getSession();
			// Lấy ra giá trị keySession
			String keySession = Common.formatString(request.getParameter("session"), "");
			// Lấy ra userId từ session
			int userId = Common.convertNumberInteger(session.getAttribute(keySession)+"");
			// Khởi tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Nếu người dùng tồn tại trong CSDL
			if (tblUserLogicImpl.checkExistedUser(userId)) {
				// Nếu xóa thông tin người dùng thành công
				if (tblUserLogicImpl.deleteUser(userId)) {
					// Xóa userId trên session
					session.removeAttribute(keySession);
					// Gọi đến url để chuyển tới màn hình thông báo ADM006.jsp
					response.sendRedirect(Constant.MESSAGE + "?success=" + Constant.DELETE_USER_SUCCESS);
				}
				// Nếu người dùng không tồn tại
			} else {
				// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ
				// thống đang lỗi
				response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.NOT_EXISTED_USER);
			}
		} catch (Exception e) {
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}

}
