/**
 * Copyright (C) 2019 Luvina Academy
 * SuccessController.java Jan 3, 2019, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * Class thực hiện chức năng chuyển sang màn hình ADM006
 * 
 * @author kien vu
 *
 */
public class SuccessController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Lấy kiểu thông báo
			String success = request.getParameter("success");
			// Nếu thông báo là thêm User thành công
			if (Constant.ADD_USER_SUCCESS.equals(success)) {
				// Gán giá trị thông báo lên request
				request.setAttribute("message", MessageProperties.getData("MSG001"));
			// Nếu thông báo là sửa thông tin User thành công
			}else if(Constant.EDIT_USER_SUCCESS.equals(success)){
				// Gán giá trị thông báo lên request
				request.setAttribute("message", MessageProperties.getData("MSG002"));
			// Nếu thông báo là xóa thông tin User thành công
			}else if(Constant.DELETE_USER_SUCCESS.equals(success)){
				// Gán giá trị thông báo lên request
				request.setAttribute("message", MessageProperties.getData("MSG003"));
			// Nếu thông báo là thay đổi mật khẩu User thành công
			}else if(Constant.CHANGE_PASSWORD_SUCCESS.equals(success)){
				// Gán giá trị thông báo lên request
				request.setAttribute("message", MessageProperties.getData("MSG002"));
			}
			// Thực hiện lấy đường dấn đến màn hình hiển thị thông báo ADM006
			RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher(Constant.VIEW_ADM006);
			// Chuyển đến trang xử lý lỗi
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}
}
