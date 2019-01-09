/**
 * Copyright (C) 2019 Luvina Academy
 * ViewDetailUserController.java Jan 7, 2019, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class dùng để lấy thông tin chi tiết người dùng và chuyển cho màn hình
 * ADM005.jsp
 * 
 * @author kien vu
 *
 */
public class ViewDetailUserController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Lấy giá trị userId cần hiển thị từ request
			int userId = Common.convertNumberInteger(request.getParameter("userId"));
			// Tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Kiểm tra nếu user đó tồn tại trong CSDL
			if (tblUserLogicImpl.checkExistedUser(userId)) {
				// Lấy thông tin người dùng
				UserInfor userInfor = tblUserLogicImpl.getUserInforById(userId);
				// Set đối tượng UserInfor lên request
				request.setAttribute("userInfor", userInfor);
				// Thực hiện lấy đường dấn đến màn hình hiển thị thông tin người dùng
				RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher(Constant.VIEW_ADM005);
				// Chuyển đến trang xử lý lỗi
				requestDispatcher.forward(request, response);
			// Nếu user đó không tồn tại hoặc là admin
			}else{
				// Chuyển đến màn hình lỗi System_Error.jsp với thông báo user không tồn tại
				response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.NOT_EXISTED_USER);
			}
		} catch (Exception e) {
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}

}
