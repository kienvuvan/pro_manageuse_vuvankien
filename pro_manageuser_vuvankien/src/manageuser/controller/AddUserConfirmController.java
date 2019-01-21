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
			// Khởi tạo session
			HttpSession session = request.getSession();
			// Lấy giá trị màn hình đi tới ADM004
			String from = (String) session.getAttribute("from");
			// Xóa session chứa giá trị màn hình đi tới ADM004
			session.removeAttribute("from");
			// Lấy ra giá trị keySession
			String keySession = Common.formatString(request.getParameter("session"), "");
			// Nếu màn hình đi tới là màn hình ADM003
			if (Constant.ADM003.equals(from)) {
				UserInfor userInfor;
				// Lấy ra đối tượng UserInfor trên session
				userInfor = (UserInfor) session.getAttribute(keySession);
				// Nếu đối tượng tồn tại trên session
				if (userInfor != null) {
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
					// Nếu người dùng không tồn tại trên session (trường hợp nếu session time out)
				} else {
					// Chuyển đến màn hình lỗi System_Error.jsp với thông báo
					// người dùng không tồn tại
					response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.NOT_EXISTED_USER);
				}
				// Ngược lại, nếu không đi từ ADM003
			} else {
				// Xóa đối tượng UserInfor trên session tránh trường hợp người
				// dùng chưa xác nhận thêm mà xóa tab (làm nhiều lần khiến trên
				// session chưa nhiều dữ liệu)
				session.removeAttribute(keySession);
				// Chuyển đến màn hình ADM002
				response.sendRedirect(Constant.LIST_USER_URL);
			}
		} catch (Exception e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL);
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
			// Khởi tạo session
			HttpSession session = request.getSession();
			// Lấy ra giá trị keySession
			String keySession = request.getParameter("session");
			// Lấy giá trị userInfor từ session
			UserInfor userInfor = (UserInfor) session.getAttribute(keySession);
			// Xóa thông tin UserInfor trên session
			session.removeAttribute(keySession);
			// Khởi tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Tạo đối tượng UserValidate để kiểm tra các trường dữ liệu
			UserValidate userValidate = new UserValidate();
			// Nếu userInfor không lỗi thì tiến hành thêm vào CSDL
			if (userValidate.validateConfirmUserInfor(userInfor)) {
				// Nếu thêm người dùng thành công
				if (tblUserLogicImpl.creatUser(userInfor)) {
					// Set giá trị trạng thái chuyển sang màn hình ADM006 là OK
					session.setAttribute("from", Constant.ACCEPT);
					// Thêm kiểu thực hiện thành công lên session
					session.setAttribute("success", Constant.SUCCESS);
					// Chuyển đến màn hình ADM006 với kiểu thông báo là thành
					// công
					response.sendRedirect("success.do" + "?type=" + Constant.ADD_USER_SUCCESS);
				}
			}
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL);
		}
	}

}
