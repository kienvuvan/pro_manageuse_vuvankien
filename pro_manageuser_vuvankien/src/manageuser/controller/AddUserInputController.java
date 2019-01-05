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
 * Class thực hiện chức năng chuyển đến màn hinh ADM003 và thực hiện chức năng validate User để chuyển sang màn hình ADM004
 * 
 * @author kien vu
 *
 */
public class AddUserInputController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Set giá trị các trường selectBox
			setDataLogicADM003(request, response);
			// Lấy ra đối tượng UserInfor
			UserInfor userInfor = getDefaultValue(request, response);
			// Truyền đối tượng UserInfor lên request
			userInfor = Common.formatDateUserInfor(userInfor);
			// Set đối tượng UserInfor lên request
			request.setAttribute("userInfor", userInfor);
			// Chuyển đến màn hình ADM003.jsp
			request.getRequestDispatcher(Constant.VIEW_ADM003).forward(request, response);
		} catch (Exception e) {
			// Chuyển đến màn hình lỗi System_Error.jsp
			response.sendRedirect(Constant.ERROR_URL);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			// Lấy ra đối tượng UserInfor
			UserInfor userInfor = getDefaultValue(request, response);
			// Tạo đối tượng UserValidate để kiểm tra các trường dữ liệu
			UserValidate userValidate = new UserValidate();
			// Kiểm tra để xem trả về danh sách lỗi
			ArrayList<String> messages = userValidate.validateUserInfor(userInfor);
			// Nếu có lỗi
			if (messages.size() > 0) {
				// Set giá trị các trường selectBox
				setDataLogicADM003(request, response);
				// Set giá trị UserInfor lên request
				request.setAttribute("userInfor", userInfor);
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
				// Set giá trị UserInfor lên session
				session.setAttribute(keySession, userInfor);
				// Chuyển đến màn hình ADM004.jsp
				response.sendRedirect(Constant.ADD_USER_CONFIRMS + "?session=" + keySession);
			}
		} catch (Exception e) {
			// Chuyển đến màn hình lỗi System_Error.jsp
			response.sendRedirect(Constant.ERROR_URL);
		}
	}

	/**
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
			e.getMessage();
			throw e;
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private UserInfor getDefaultValue(HttpServletRequest request, HttpServletResponse response) {
		// Khởi tạo đối tượng UserInfor
		UserInfor userInfor = new UserInfor();
		// Lấy giá trị kiểu hiển thị màn hình ADM003
		String typeShow = Common.formatString(request.getParameter("typeShow"), Constant.TYPE_ADD_OR_VALIDATE);
		// Nếu kiểu hiển thị từ màn hình ADM002 sang ADM003 hoặc trường hợp
		// kiểm
		// tra lỗi quay lại màn hinh ADM003
		if (Constant.TYPE_ADD_OR_VALIDATE.equals(typeShow)) {
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
			String dateBirth = yearBirth + "-" + monthBirth + "-" + dayBirth;

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
			userInfor.setLoginName(loginName);
			userInfor.setGroupId(groupId);
			userInfor.setFullName(fullName);
			userInfor.setFullNameKana(fullNameKana);
			userInfor.setBirthday(dateBirth);
			userInfor.setEmail(email);
			userInfor.setTel(tel);
			userInfor.setPassword(password);
			userInfor.setPasswordAgain(passwordAgain);
			userInfor.setCodeLevel(codeLevel);
			userInfor.setStartDate(dateStart);
			userInfor.setEndDate(dateEnd);
			userInfor.setTotalScore(totalScore);
		} else if (Constant.TYPE_BACK_ADM003.equals(typeShow)) {
			// Khởi tạo đối tượng session
			HttpSession session = request.getSession();
			// Khởi tạo, gán giá trị cho sessionKey lấy giá trị từ request
			String sessionKey = request.getParameter("session");
			// Gán giá trị cho đối tượng userInfo
			userInfor = (UserInfor) session.getAttribute(sessionKey);
		}
		// Trả về đối tượng UserInfor
		return userInfor;
	}
}
