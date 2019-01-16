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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Lấy ra userId từ request
			int userId = Common.convertNumberInteger(request.getParameter("userId"));
			// Khởi tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Nếu người dùng tồn tại trong CSDL
			if (tblUserLogicImpl.checkExistedUser(userId)) {
				// Nếu xóa thông tin người dùng thành công
				if (tblUserLogicImpl.deleteUser(userId)) {
					// Khởi tạo Session
					HttpSession session = request.getSession();
					// Set giá trị trạng thái chuyển sang màn hình ADM006 là OK
					session.setAttribute("from", Constant.ACCEPT);
					// Thêm kiểu thực hiện thành công lên session
					session.setAttribute("success", Constant.SUCCESS);
					// Gọi đến url để chuyển tới màn hình thông báo ADM006.jsp
					response.sendRedirect(Constant.SUCCESS_URL + "?type=" + Constant.DELETE_USER_SUCCESS);
					// Nếu xóa không thành công
				} else {
					// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ
					// thống đang lỗi
					response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
				}
				// Nếu người dùng không tồn tại
			} else {
				// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ
				// thống đang lỗi
				response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.NOT_EXISTED_USER);
			}
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println("DeleteUserConfirmController : doGet - " + e.getMessage());
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}

}
