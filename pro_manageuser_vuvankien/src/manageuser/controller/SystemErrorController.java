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

import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * Class thực hiện chức năng chuyển đến màn hình System_Error
 * 
 * @author kien vu
 *
 */
public class SystemErrorController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Phương thức thực hiện chuyển đến trang System_Error.jsp
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Lấy giá trị kiểu lỗi
			String typeError = request.getParameter("typeError");
			// Biến kiểu lỗi
			String errorMessage = "";
			// Nếu lỗi là user không tồn tại
			if(Constant.NOT_EXISTED_USER.equals(typeError)){
				// Lấy giá trị câu thông báo lỗi user không tồn tại trong file messageerror.properties
				errorMessage = MessageErrorProperties.getData("ER013");
			// Ngược lại nếu lỗi là lỗi hệ thống
			}else{
				// Lấy giá trị câu thông báo lỗi hệ thống đang lỗi trong file messageerror.properties
				errorMessage = MessageErrorProperties.getData("ER015");
			}
			request.setAttribute("errorMessage", errorMessage);
			// Thực hiện lấy đường dấn đến màn hình hiển thị lỗi
			RequestDispatcher requestDispatcher = this.getServletContext()
					.getRequestDispatcher(Constant.VIEW_SYSTEM_ERROR);
			// Chuyển đến trang xử lý lỗi
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}

}
