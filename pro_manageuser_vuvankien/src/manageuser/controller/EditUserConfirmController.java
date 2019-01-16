/**
 * Copyright (C) 2019 Luvina Academy
 * EditUserConfirmController.java Jan 8, 2019, Vu Van Kien
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
import manageuser.logics.TblDetailUserJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblDetailUserJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.UserValidate;

/**
 * @author kien vu
 *
 */
public class EditUserConfirmController extends HttpServlet {

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
				// Lấy ra đối tượng UserInfor trên session
				UserInfor userInfor = (UserInfor) session.getAttribute(keySession);
				// Nếu đối tượng UserInfor tồn tại trên session
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
					request.setAttribute("typeShow", Constant.TYPE_EDIT_USER);
					// Set keySession lên request
					request.setAttribute("keySession", keySession);
					// Set userInfor lên request
					request.setAttribute("userInfor", userInfor);
					// Chuyển đến màn hình ADM004
					request.getRequestDispatcher(Constant.VIEW_ADM004).forward(request, response);
					// Nếu không được chuyển từ màn hình ADM003.jsp sang thì
					// chuyển
					// đến trang SysstemError với lỗi người dùng không tồn tại
				} else {
					// Chuyển đến màn hình lỗi System_Error.jsp với thông báo
					// người
					// dùng không tồn tại
					response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.NOT_EXISTED_USER);
				}
				// Ngược lại, nếu không đi từ ADM003
			} else {
				// Xóa đối tượng UserInfor trên session
				session.removeAttribute(keySession);
				// Chuyển đến màn hình ADM002
				response.sendRedirect(Constant.LIST_USER_URL);
			}
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println("EditUserConfirmController : doGet - " + e.getMessage());
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
			// Lấy ra giá trị userId
			int userId = userInfor.getUserId();
			// Nếu User tồn tại trong CSDL
			if (tblUserLogicImpl.checkExistedUser(userId)) {
				// Tạo đối tượng UserValidate để kiểm tra các trường dữ liệu
				UserValidate userValidate = new UserValidate();
				// Nếu userInfor không lỗi thì tiến hành thêm vào CSDL
				if (userValidate.validateUserInfor(userInfor).isEmpty()) {
					// Khởi tạo đối tượng TblDetailUserJapanLogicImpl
					TblDetailUserJapanLogic tblDetailUserJapanLogicImpl = new TblDetailUserJapanLogicImpl();
					// Kiểm tra thông tin chi tiết của người dùng có tồn tại
					// trong CSDL hay không
					boolean existedDetailUserJapan = tblDetailUserJapanLogicImpl.checkExistedDetailUserJapan(userId);
					// Nếu cập nhật thông tin người dùng thành công
					if (tblUserLogicImpl.updateUser(userInfor, existedDetailUserJapan)) {
						// Set giá trị trạng thái chuyển sang màn hình ADM006 là
						// OK
						session.setAttribute("from", Constant.ACCEPT);
						// Thêm kiểu thực hiện thành công lên session
						session.setAttribute("success", Constant.SUCCESS);
						// Gọi đến url để chuyển tới màn hình thông báo
						// ADM006.jsp
						response.sendRedirect(Constant.SUCCESS_URL + "?type=" + Constant.EDIT_USER_SUCCESS);
					}
				}
				// Nếu người dùng không tồn tại trong CSDL
			} else {
				// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ
				// thống đang lỗi
				response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.NOT_EXISTED_USER);
			}
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println("EditUserConfirmController : doPost - " + e.getMessage());
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}
}
