/**
 * Copyright (C) 2018 Luvina Academy
 * LogoutController.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;

/**
 * Class thực hiện chức năng logout
 * 
 * @author kien vu
 *
 */
public class LogoutController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức thực hiện xóa session và chuyển về trang đăng nhập hệ thống
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			// Xóa hết session
			session.invalidate();
			// Chuyển về màn hình login
			response.sendRedirect(Constant.LOGIN_URL);
		} catch (Exception e) {
			e.getMessage();
			// Chuyển đến màn hình lỗi System_Error.jsp
			response.sendRedirect(Constant.ERROR_URL);
		}
	}
}
