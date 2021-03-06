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
import javax.servlet.http.HttpSession;

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
			// Lấy ra đối tượng session
			HttpSession session = request.getSession();
			// Lấy giá trị trạng thái chấp nhận màn hình đi tới
			String from = (String) session.getAttribute("from");
			// Xóa session chứa giá trị màn hình đi tới
			session.removeAttribute("from");
			// Nếu màn hình đi tới là màn hình ADM004 hoặc từ màn hình ADM005
			// với chức năng xóa hoặc từ màn hình ChangePass
			if (Constant.ACCEPT.equals(from)) {
				// Lấy ra kiểu thông báo thành công trên session
				String success = (String) session.getAttribute("success");
				// Nếu có tồn tại biến thực hiện thành công
				if (success != null) {
					// Lấy kiểu thông báo
					String type = request.getParameter("type");
					// Nếu thông báo là thêm User thành công
					if (Constant.ADD_USER_SUCCESS.equals(type)) {
						// Gán giá trị thông báo lên request
						request.setAttribute("message", MessageProperties.getData("MSG001"));
						// Nếu thông báo là sửa thông tin User thành công
					} else if (Constant.EDIT_USER_SUCCESS.equals(type)) {
						// Gán giá trị thông báo lên request
						request.setAttribute("message", MessageProperties.getData("MSG002"));
						// Nếu thông báo là xóa thông tin User thành công
					} else if (Constant.DELETE_USER_SUCCESS.equals(type)) {
						// Gán giá trị thông báo lên request
						request.setAttribute("message", MessageProperties.getData("MSG003"));
						// Nếu thông báo là thay đổi mật khẩu User thành công
					} else if (Constant.CHANGE_PASSWORD_SUCCESS.equals(type)) {
						// Gán giá trị thông báo lên request
						request.setAttribute("message", MessageProperties.getData("MSG002"));
					}
					// Xóa kiểu thực hiện hiện thành công trên session trước khi
					// chuyển sang màn hình ADM006.jsp
					session.removeAttribute("success");
					// Thực hiện lấy đường dấn đến màn hình hiển thị thông báo
					// ADM006
					RequestDispatcher requestDispatcher = this.getServletContext()
							.getRequestDispatcher(Constant.VIEW_ADM006);
					// Chuyển đến trang xử lý lỗi
					requestDispatcher.forward(request, response);
				} else {
					// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ
					// thống đang lỗi
					response.sendRedirect(Constant.ERROR_URL);
				}
				// Ngược lại, nếu không đi từ ADM004
			} else {
				// Chuyển đến màn hình ADM002
				response.sendRedirect(Constant.LIST_USER_URL);
			}
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : "
					+ new Object(){}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL);
		}
	}
}
