/**
 * Copyright (C) 2018 Luvina Academy
 * AddUserConfirmController.java Dec 26, 2018, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.UserValidate;

/**
 * Class thực hiện chức năng xác nhận để thêm người dùng vào CSDL
 * 
 * @author kien vu
 *
 */
public class AddUserConfirmController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Khởi tạo session
			HttpSession session = request.getSession();
			// Lấy ra giá trị keySession
			String keySession = Common.formatString(request.getParameter("session"), "");
			UserInfor userInfor;
			// Lấy ra đối tượng UserInfor trên session
			userInfor = (UserInfor) session.getAttribute(keySession);
			// Nếu đối tượng không có trên session
			if (userInfor == null) {
				// Khởi tạo đối tượng
				userInfor = new UserInfor();
			}
			// Khởi tạo đối tượng MstGroupLogicImpl
			MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
			// Lấy ra giá trị tên nhóm từ giá trị groupId
			String groupName = mstGroupLogicImpl.getGroupNameById(userInfor.getGroupId());
			// Khởi tạo đối tượng MstJapanLogicImpl
			MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
			// Lấy ra giá trị tên trình độ tiếng Nhật từ codLevel
			String nameLevel = mstJapanLogicImpl.getNameLevelById(userInfor.getCodeLevel());
			// Set giá trị groupName cho userInfor
			userInfor.setNameLevel(nameLevel);
			// Set giá trị nameLevel cho userInfor
			userInfor.setGroupName(groupName);

			// Set kiểu hiển thị màn hình ADM004.jsp lên request
			request.setAttribute("typeShow", Constant.TYPE_ADD_USER);
			// Set keySession lên request
			request.setAttribute("keySession", keySession);
			// Set userInfor lên request
			request.setAttribute("userInfor", userInfor);
			// Chuyển đến màn hình ADM004
			request.getRequestDispatcher(Constant.VIEW_ADM004).forward(request, response);
			// Nếu có lỗi xảy ra chuyển đến trang SysstemError
		} catch (Exception e) {
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Khởi tạo session
			HttpSession session = request.getSession();
			// Lấy ra giá trị keySession
			String keySession = request.getParameter("session");
			// Lấy giá trị userInfor từ session
			UserInfor userInfor = (UserInfor) session.getAttribute(keySession);
			// Khởi tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Tạo đối tượng UserValidate để kiểm tra các trường dữ liệu
			UserValidate userValidate = new UserValidate();
			// Nếu userInfor không lỗi thì tiến hành thêm vào CSDL
			if (userValidate.validateUserInfor(userInfor).isEmpty()) {
				// Nếu thêm thành công thì chuyển đến màn hình ADM006 với kiểu
				// thông báo là thành công
				if (tblUserLogicImpl.creatUser(userInfor)) {
					response.sendRedirect(Constant.MESSAGE + "?success=" + Constant.ADD_USER_SUCCESS);
				}
			}
			// Xóa thông tin UserInfor trên session
			session.removeAttribute(keySession);
		} catch (Exception e) {
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}

}
