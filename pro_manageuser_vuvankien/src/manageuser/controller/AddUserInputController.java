/**
 * Copyright (C) 2018 Luvina Academy
 * AddUserController.java Dec 20, 2018, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.MstGroup;
import manageuser.entities.MstJapan;
import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.UserValidate;

/**
 * Class thực hiện chức năng chuyển đến màn hinh ADM003 và thực hiện chức năng
 * validate User để chuyển sang màn hình ADM004
 * 
 * @author kien vu
 *
 */
public class AddUserInputController extends HttpServlet {

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
			// Set giá trị các trường selectBox
			setDataLogicADM003(request, response);
			// Lấy ra đối tượng UserInfor
			UserInfor userInfor = getDefaultValue(request);
			// Nếu đối tượng UserInfor tồn tại
			if (userInfor != null) {
				// Format định dạng các thuộc tính ngày của UserInfor
				userInfor = Common.formatDateUserInfor(userInfor);
				// Set đối tượng UserInfor lên request
				request.setAttribute("userInfor", userInfor);
				// Set kiểu hiển thị màn hình ADM003.jsp là add_user
				request.setAttribute("typeShow", Constant.TYPE_ADD_USER);
				// Chuyển đến màn hình ADM003.jsp
				request.getRequestDispatcher(Constant.VIEW_ADM003).forward(request, response);
				// Ngược lại nếu không tồn tại (khi timeout session)
			} else {
				// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ
				// thống
				// đang lỗi
				response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
			}
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
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
			// Lấy ra đối tượng UserInfor
			UserInfor userInfor = getDefaultValue(request);
			// Tạo đối tượng UserValidate để kiểm tra các trường dữ liệu
			UserValidate userValidate = new UserValidate();
			// Kiểm tra để xem trả về danh sách lỗi
			ArrayList<String> messages = userValidate.validateUserInfor(userInfor);
			// Nếu có lỗi
			if (!messages.isEmpty()) {
				// Set giá trị các trường selectBox
				setDataLogicADM003(request, response);
				// Set giá trị password về chuỗi rỗng
				userInfor.setPassword("");
				// Set giá trị passwordAgain về chuỗi rỗng
				userInfor.setPasswordAgain("");
				// Set giá trị UserInfor lên request
				request.setAttribute("userInfor", userInfor);
				// Set kiểu hiển thị màn hình ADM003.jsp là add_user
				request.setAttribute("typeShow", Constant.TYPE_ADD_USER);
				// Set thông báo lỗi lên request
				request.setAttribute("messages", messages);
				// Quay về màn hình ADM003
				request.getRequestDispatcher(Constant.VIEW_ADM003).forward(request, response);
				// Nếu không có lỗi
			} else {
				// Khởi tạo keySession
				String keySession = Common.getKeySession(userInfor.getLoginName());
				// Get đối tượng Session
				HttpSession session = request.getSession();
				// Set giá trị màn hình chuyển sang là từ ADM003
				session.setAttribute("from", Constant.ADM003);
				// Set giá trị UserInfor lên session
				session.setAttribute(keySession, userInfor);
				// Chuyển cho controller AddUserConfirmController xử lý tiếp
				response.sendRedirect(Constant.ADD_USER_CONFIRMS + "?session=" + keySession);
			}
			// Nếu có lỗi
		} catch (Exception e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + 
			new Object() {}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ thống
			// đang lỗi
			response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.SYSTEM_ERROR);
		}
	}

	/**
	 * Phương thức set các giá trị cho các selectbox
	 * 
	 * @param request
	 * @param response
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void setDataLogicADM003(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		try {
			// Khởi tạo đối tượng MstGroupLogicImpl
			MstGroupLogic msGroupLogicImpl = new MstGroupLogicImpl();
			// Khởi tạo đối tượng MstJapanLogicImpl
			MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
			// Lấy ra danh sách MstGroup
			ArrayList<MstGroup> listMstGroup = msGroupLogicImpl.getAllGroups();
			// Lấy ra danh sách MstJapan
			ArrayList<MstJapan> listMstJapan = mstJapanLogicImpl.getAllMstJapn();
			Calendar calendar = Calendar.getInstance();
			// Tạo danh sách năm
			ArrayList<Integer> listYear = Common.getListYear(Constant.FROM_YEAR, calendar.get(Calendar.YEAR));
			ArrayList<Integer> listYearEndDate = Common.getListYear(Constant.FROM_YEAR,
					calendar.get(Calendar.YEAR) + 1);
			// Tạo danh sách tháng
			ArrayList<Integer> listMonth = Common.getListMonth();
			// Tạo danh sách ngày
			ArrayList<Integer> listDay = Common.getListDay();

			// Truyền danh sách MstGroup, MstJapan, danh sách năm, tháng, ngày
			// lên request
			request.setAttribute("listMstGroup", listMstGroup);
			request.setAttribute("listMstJapan", listMstJapan);
			request.setAttribute("listYear", listYear);
			request.setAttribute("listYearEndDate", listYearEndDate);
			request.setAttribute("listMonth", listMonth);
			request.setAttribute("listDay", listDay);
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : "
					+ new Object(){}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
	}

	/**
	 * Phương thức set các giá trị của đối tượng UserInfor lấy từ request
	 * 
	 * @param request
	 * @return UserInfor đối tượng chứa các thông tin lấy từ request
	 */
	private UserInfor getDefaultValue(HttpServletRequest request) {
		// Khởi tạo đối tượng UserInfor
		UserInfor userInfor = new UserInfor();
		// Lấy giá trị kiểu hiển thị màn hình ADM003
		String typeShow = Common.formatString(request.getParameter("typeShow"), Constant.TYPE_ADD_USER);
		// Nếu kiểu hiển thị từ màn hình ADM002 sang ADM003 hoặc trường hợp
		// kiểm tra User lỗi quay lại màn hinh ADM003
		if (Constant.TYPE_ADD_USER.equals(typeShow) || Constant.TYPE_VALIDATE_USER.equals(typeShow)) {
			// Lấy giá trị trường loginName từ request
			String loginName = Common.formatString(request.getParameter("loginName"), "");
			// Lấy giá trị trường groupId từ request
			int groupId = Common.convertNumberInteger(request.getParameter("groupId"));
			// Lấy giá trị trường fullName từ request
			String fullName = Common.formatString(request.getParameter("fullName"), "");
			// Lấy giá trị trường fullNameKana từ request
			String fullNameKana = Common.formatString(request.getParameter("fullNameKana"), "");

			// Lấy giá trị ngày tháng năm sinh từ request
			String yearBirth = Common.formatString(request.getParameter("yearBirth"), "");
			String monthBirth = Common.formatString(request.getParameter("monthBirth"), "");
			String dayBirth = Common.formatString(request.getParameter("dayBirth"), "");
			String birthday = yearBirth + "-" + monthBirth + "-" + dayBirth;

			// Lấy giá trị email từ request
			String email = Common.formatString(request.getParameter("email"), "");
			// Lấy giá trị tel từ request
			String tel = Common.formatString(request.getParameter("tel"), "");
			// Lấy giá trị password từ request
			String password = Common.formatString(request.getParameter("password"), "");
			// Lấy giá trị passwordAgain từ request
			String passwordAgain = Common.formatString(request.getParameter("passwordAgain"), "");

			// Lấy giá trị codeLevel từ request
			String codeLevel = Common.formatString(request.getParameter("codeLevel"),
					Constant.CODE_LEVEL_DEFAULT_VALUE);

			// Lấy giá trị ngày tháng năm bắt đầu trình độ tiếng nhật từ
			// request
			String yearStart = Common.formatString(request.getParameter("yearStart"), "");
			String monthStart = Common.formatString(request.getParameter("monthStart"), "");
			String dayStart = Common.formatString(request.getParameter("dayStart"), "");
			String dateStart = yearStart + "-" + monthStart + "-" + dayStart;

			// Lấy giá trị ngày tháng năm trình độ tiếng nhật hết hạn từ
			// request
			String yearEnd = Common.formatString(request.getParameter("yearEnd"), "");
			String monthEnd = Common.formatString(request.getParameter("monthEnd"), "");
			String dayEnd = Common.formatString(request.getParameter("dayEnd"), "");
			String dateEnd = yearEnd + "-" + monthEnd + "-" + dayEnd;

			// Lấy giá trị tổng điểm từ request
			String totalScore = Common.formatString(request.getParameter("totalScore"), "");

			// Set các thuộc tính cho đối tượng UserInfor
			// Set giá trị loginName cho đối tượng UserInfor
			userInfor.setLoginName(loginName);
			// Set giá trị groupId cho đối tượng UserInfor
			userInfor.setGroupId(groupId);
			// Set giá trị fullName cho đối tượng UserInfor
			userInfor.setFullName(fullName);
			// Set giá trị fullNameKana cho đối tượng UserInfor
			userInfor.setFullNameKana(fullNameKana);
			// Set giá trị birthday cho đối tượng UserInfor
			userInfor.setBirthday(birthday);
			// Set giá trị email cho đối tượng UserInfor
			userInfor.setEmail(email);
			// Set giá trị tel cho đối tượng UserInfor
			userInfor.setTel(tel);
			// Set giá trị password cho đối tượng UserInfor
			userInfor.setPassword(password);
			// Set giá trị passwordAgain cho đối tượng UserInfor
			userInfor.setPasswordAgain(passwordAgain);
			// Set giá trị codeLevel cho đối tượng UserInfor
			userInfor.setCodeLevel(codeLevel);
			// Set giá trị startDate cho đối tượng UserInfor
			userInfor.setStartDate(dateStart);
			// Set giá trị endDate cho đối tượng UserInfor
			userInfor.setEndDate(dateEnd);
			// Set giá trị totalScore cho đối tượng UserInfor
			userInfor.setTotalScore(totalScore);
		} else if (Constant.TYPE_BACK_ADM003.equals(typeShow)) {
			// Khởi tạo đối tượng session
			HttpSession session = request.getSession();
			// Khởi tạo, gán giá trị cho sessionKey lấy giá trị từ request
			String sessionKey = request.getParameter("session");
			// Gán giá trị cho đối tượng userInfo
			userInfor = (UserInfor) session.getAttribute(sessionKey);
			// Set giá trị password về chuỗi rỗng
			userInfor.setPassword("");
			// Set giá trị passwordAgain về chuỗi rỗng
			userInfor.setPasswordAgain("");
			// Xóa đối tượng UserInfor trên session
			session.removeAttribute(sessionKey);
		}
		// Trả về đối tượng UserInfor
		return userInfor;
	}
}
