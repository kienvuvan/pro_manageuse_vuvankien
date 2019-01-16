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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
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
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println("LogoutController : doGet - " + e.getMessage());
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}
}
