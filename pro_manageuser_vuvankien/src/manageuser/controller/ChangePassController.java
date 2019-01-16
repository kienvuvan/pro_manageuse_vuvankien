/**
 * Copyright (C) 2019 Luvina Academy
 * ChangePassInputController.java Jan 9, 2019, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.UserValidate;

/**
 * Class thực hiện chức năng thay đổi mật khẩu người dùng
 * 
 * @author kien vu
 *
 */
public class ChangePassController extends HttpServlet {

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
			int userId = Common.convertNumberInteger(request.getParameter("userId"));
			// Tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Kiểm tra nếu User tồn tại
			if (tblUserLogicImpl.checkExistedUser(userId)) {
				// Set userId lên request
				request.setAttribute("userId", userId);
				// Thực hiện lấy đường dấn đến màn hình thay đổi mật khẩu
				RequestDispatcher requestDispatcher = this.getServletContext()
						.getRequestDispatcher(Constant.CHANGE_PASS);
				// Chuyển đến trang ChangePass.jsp
				requestDispatcher.forward(request, response);
				// Nếu User không tồn tại
			} else {
				// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ
				// thống đang lỗi
				response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.NOT_EXISTED_USER);
			}
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println("ChangePassController : doGet - " + e.getMessage());
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Lấy giá trị userId từ request
			int userId = Common.convertNumberInteger(request.getParameter("userId"));
			// Tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Kiểm tra nếu User đó tồn tại trong CSDL
			if (tblUserLogicImpl.checkExistedUser(userId)) {
				// Lấy giá trị password từ request
				String password = Common.formatString(request.getParameter("password"), "");
				// Lấy giá trị passwordAgain từ request
				String passwordAgain = Common.formatString(request.getParameter("passwordAgain"), "");
				// Tạo đối tượng UserValidate để kiểm tra các trường dữ liệu
				UserValidate userValidate = new UserValidate();
				// Kiểm tra để xem trả về danh sách lỗi
				ArrayList<String> messages = userValidate.validatePassword(password, passwordAgain);
				// Nếu có lỗi
				if (!messages.isEmpty()) {
					// Set lỗi lên request
					request.setAttribute("messages", messages);
					// Set userId lên request
					request.setAttribute("userId", userId);
					// Quay về màn hình ADM003
					request.getRequestDispatcher(Constant.CHANGE_PASS).forward(request, response);
					// Nếu không có lỗi
				} else {
					// Nếu thay đổi mật khẩu thành công
					if (tblUserLogicImpl.changePassword(userId, password)) {
						// Khởi tạo session
						HttpSession session = request.getSession();
						// Set giá trị trạng thái chuyển sang màn hình ADM006 là
						// OK
						session.setAttribute("from", Constant.ACCEPT);
						// Thêm kiểu thực hiện thành công lên session
						session.setAttribute("success", Constant.SUCCESS);
						// Gọi đến url để chuyển tới màn hình thông báo
						// ADM006.jsp
						response.sendRedirect(Constant.SUCCESS_URL + "?type=" + Constant.CHANGE_PASSWORD_SUCCESS);
					}
				}
				// Nếu người dùng không tồn tại
			} else {
				// Chuyển đến màn hình lỗi System_Error.jsp với thông báo người
				// dùng không tồn tại
				response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.NOT_EXISTED_USER);
			}
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println("ChangePassController : doPost - " + e.getMessage());
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}

}
