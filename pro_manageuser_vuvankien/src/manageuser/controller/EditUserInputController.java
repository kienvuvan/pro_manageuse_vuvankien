/**
 * Copyright (C) 2019 Luvina Academy
 * EditUserInputController.java Jan 8, 2019, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.UserValidate;

/**
 * Class thực hiện chức năng liên quan đến sửa thông tin người dùng
 * 
 * @author kien vu
 *
 */
public class EditUserInputController extends HttpServlet {

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
			// Lấy ra giá trị userId từ request và chuyển giá trị nhận được về
			// dạng số
			int userId = Common.convertNumberInteger(request.getParameter("userId"));
			// Tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Kiểm tra nếu User tồn tại
			if (tblUserLogicImpl.checkExistedUser(userId)) {
				// Set các giá trị selectBox cho màn hình ADM003.jsp
				setDataLogicADM003(request, response);
				// Lấy ra đối tượng UserInfor
				UserInfor userInfor = getDefaultValue(request);
				// Truyền đối tượng UserInfor và kiểu hiển thị màn hình
				// ADM003.jsp lên request
				request.setAttribute("userInfor", userInfor);
				// Set kiểu hiển thị màn hình ADM003.jsp là edit_user
				request.setAttribute("typeShow", Constant.TYPE_EDIT_USER);
				// Chuyển đến màn hình ADM003.jsp
				request.getRequestDispatcher(Constant.VIEW_ADM003).forward(request, response);
			} else {
				// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ
				// thống đang lỗi
				response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.NOT_EXISTED_USER);
			}
		} catch (Exception e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : "
					+ new Object(){}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
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
			// Tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Lấy giá trị userId từ form của màn hình ADM003.jsp
			int userId = Common.convertNumberInteger(request.getParameter("userId"));
			// Kiểm tra nếu User đó tồn tại trong CSDL
			if (tblUserLogicImpl.checkExistedUser(userId)) {
				// Lấy ra đối tượng UserInfor
				UserInfor userInfor = getDefaultValue(request);
				// Tạo đối tượng UserValidate
				UserValidate userValidate = new UserValidate();
				// Kiểm tra để xem trả về danh sách lỗi
				ArrayList<String> messages = userValidate.validateUserInfor(userInfor);
				// Nếu có lỗi
				if (!messages.isEmpty()) {
					// Set giá trị các trường selectBox
					setDataLogicADM003(request, response);
					userInfor = getDefaultValue(request);
					// Set giá trị UserInfor lên request
					request.setAttribute("userInfor", userInfor);
					// Set kiểu hiển thị màn hình ADM003.jsp là add_user
					request.setAttribute("typeShow", Constant.TYPE_EDIT_USER);
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
					// Chuyển cho controller EditUserConfirmController xử lý
					// tiếp
					response.sendRedirect(Constant.EDIT_USER_CONFIRMS + "?session=" + keySession);
				}
			} else {
				// Chuyển đến màn hình lỗi System_Error.jsp với thông báo hệ
				// thống đang lỗi
				response.sendRedirect(Constant.ERROR_URL + "?typeError=" + Constant.NOT_EXISTED_USER);
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
	 * Phương thức lấy ra đối tượng UserInfor
	 * 
	 * @param request
	 * @return UserInfor đối tượng chứa các thông tin lấy từ request
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private UserInfor getDefaultValue(HttpServletRequest request) throws ClassNotFoundException, SQLException {
		// Khởi tạo đối tượng UserInfor
		UserInfor userInfor = new UserInfor();
		try {
			// Lấy giá trị kiểu hiển thị màn hình ADM003
			String typeShow = Common.formatString(request.getParameter("typeShow"), Constant.TYPE_EDIT_USER);
			// Nếu là trường quay lại từ màn hình ADM004.jsp
			if (Constant.TYPE_BACK_ADM003.equals(typeShow)) {
				// Khởi tạo đối tượng session
				HttpSession session = request.getSession();
				// Khởi tạo, gán giá trị cho sessionKey lấy giá trị từ request
				String sessionKey = request.getParameter("session");
				// Gán giá trị cho đối tượng userInfo
				userInfor = (UserInfor) session.getAttribute(sessionKey);
				// Xóa đối tượng UserInfor trên session
				session.removeAttribute(sessionKey);
			} else {
				// Lấy giá trị userId
				int userId = Common.convertNumberInteger(request.getParameter("userId"));
				// Nếu kiểu hiển thị từ màn hình ADM002 sang ADM003
				if (Constant.TYPE_EDIT_USER.equals(typeShow)) {
					// Tạo đối tượng TblUserLogicImpl
					TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
					// Lấy ra thông tin UserInfor
					userInfor = tblUserLogicImpl.getUserInforById(userId);
					// Format định dạng các thuộc tính ngày của UserInfor
					userInfor = Common.formatDateUserInfor(userInfor);
					// Trường hợp kiểm tra User lỗi quay lại màn hinh ADM003
				} else if (Constant.TYPE_VALIDATE_USER.equals(typeShow)) {
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
					String birthDay = yearBirth + "-" + monthBirth + "-" + dayBirth;

					// Lấy giá trị email từ request
					String email = Common.formatString(request.getParameter("email"), "");
					// Lấy giá trị tel từ request
					String tel = Common.formatString(request.getParameter("tel"), "");

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
					userInfor.setUserId(userId);
					userInfor.setLoginName(loginName);
					userInfor.setGroupId(groupId);
					userInfor.setFullName(fullName);
					userInfor.setFullNameKana(fullNameKana);
					userInfor.setBirthday(birthDay);
					userInfor.setEmail(email);
					userInfor.setTel(tel);
					userInfor.setCodeLevel(codeLevel);
					userInfor.setStartDate(dateStart);
					userInfor.setEndDate(dateEnd);
					userInfor.setTotalScore(totalScore);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : "
					+ new Object(){}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		// Trả về đối tượng UserInfor
		return userInfor;
	}

}
