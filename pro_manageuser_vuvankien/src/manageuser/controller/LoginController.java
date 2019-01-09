/**
 * Copyright (C) 2018 Luvina Academy
 * ListUserController.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;
import manageuser.validates.UserValidate;

/**
 * Class thực hiện chức năng hiển thị màn hình đăng nhập
 * 
 * @author kien vu
 *
 */
public class LoginController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Tạo đối tượng UserValidate
			UserValidate userValidate = new UserValidate();
			// Lấy giá trị trường loginId ở màn hình đăng nhập
			String username = request.getParameter("loginId").trim();
			// Lấy giá trị trường passwors ở màn hình đăng nhập
			String password = request.getParameter("password").trim();
			// Thực hiện check 2 dữ liệu
			ArrayList<String> message = userValidate.validateLogin(username, password);
			// Nếu không có lỗi
			if (message.isEmpty()) {
				HttpSession session = request.getSession(true);
				session.setAttribute("usernameLogin", username);
				// Chuyển đến trang ADM002.jsp
				response.sendRedirect(Constant.LIST_USER_URL);
				// Nếu có lỗi
			} else {
				// Quay về trang login và giữ nguyên giá trị trường username
				request.setAttribute("username", username);
				request.setAttribute("message", message);
				request.getRequestDispatcher(Constant.VIEW_ADM001).forward(request, response);
			}
			// Nếu có lỗi
		} catch (Exception e) {
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Chuyển về màn hình đăng nhập ADM001
			request.getRequestDispatcher(Constant.VIEW_ADM001).forward(request, response);
		} catch (Exception e) {
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}
}
