/**
 * Copyright (C) 2018 Luvina Academy
 * SystemErrorController.java Dec 12, 2018, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kien vu
 *
 */
public class SystemErrorController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Phương thức thực hiện chuyển đến trang System_Error.jsp
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Thực hiện lấy đường dấn đến màn hình hiển thị lỗi
		RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/views/System_Error.jsp");
		// Chuyển đến trang xử lý lỗi
		requestDispatcher.forward(request, response);
	}

}
