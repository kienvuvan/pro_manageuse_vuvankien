/**
 * Copyright (C) 2018 Luvina Academy
 * ListUserController.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.MstGroup;
import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.ConfigProperties;
import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * @author kien vu
 *
 */
public class ListUserController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Get session
		HttpSession session = request.getSession(true);
		try {
			MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
			// Lấy toàn bộ danh sách group
			ArrayList<MstGroup> listAllGroups = mstGroupLogicImpl.getAllGroups();
			// Set giá trị listAllGroups lên request để truyền cho màn hình
			// ADM002.jsp
			request.setAttribute("listAllGroups", listAllGroups);
			// Lấy giá trị hiển thị tối đa số bản trên 1 page
			int limit = ConfigProperties.getData("SIZE_RECORD");
			// Lấy giá trị hiển thị số lượng page
			int sizePaging = ConfigProperties.getData("SIZE_PANNING");
			ArrayList<Integer> listPaging = new ArrayList<>();
			ArrayList<UserInfor> listUserInfor = new ArrayList<>();
			int totalUser = 0;
			// Lấy giá trị kiểu sắp xếp theo tên được truyền từ bên view
			// ADM002.jsp
			String typeSortName = (String) session.getAttribute("typeSortName");
			// Nếu không có giá trị thì gán mặc định là sắp xếp tăng
			if (typeSortName == null) {
				typeSortName = Constant.SORTASC;
			}
			// Lấy giá trị kiểu sắp xếp theo trình độ tiếng nhật được truyền
			// từ bên view ADM002.jsp
			String typeSortCodeLevel = (String) session.getAttribute("typeSortCodeLevel");
			// Nếu không có giá trị thì gán mặc định là sắp xếp giảm
			if (typeSortCodeLevel == null) {
				typeSortCodeLevel = Constant.SORTDESC;
			}
			// Lấy giá trị kiểu sắp xếp theo ngày hết hạn được truyền từ bên
			// view ADM002.jsp
			String typeSortEndDate = (String) session.getAttribute("typeSortEndDate");
			// Nếu không có giá trị thì gán mặc định là sắp xếp tăng
			if (typeSortEndDate == null) {
				typeSortEndDate = Constant.SORTDESC;
			}
			// Lấy giá trị kiểu hiển thị màn hình ADM002.jsp
			String typeShow = (String) request.getParameter("typeShow");
			// Nếu không có giá trị mặc định là kiểu đăng nhập hoặc tìm kiếm
			if (typeShow == null) {
				typeShow = Constant.TYPE_LOGIN_OR_SEARCH;
			}
			int groupId = 0;
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Lấy giá trị groupId từ selectBox
			String groupIdString = request.getParameter("groupId");
			// Lấy giá trị name từ text tìm kiếm
			String fullname = request.getParameter("fullname");
			groupId = 0;
			// Chuyển giá trị groupId sang giá trị số
			if (groupIdString != null) {
				groupId = Integer.parseInt(groupIdString);
			}
			// Giá trị trang hiện tại
			int paningCurrent = 1;
			// Giá trị cột sắp xếp ưu tiên
			String columnFirst = Constant.FULL_NAME_COLUMN;
			// Vị trị lấy kết quả mặc định từ 0
			int offset = 0;
			// Nếu kiểu vào để hiển thị màn hình ADM002.jsp là login hoặc
			// search
			if (Constant.TYPE_LOGIN_OR_SEARCH.equals(typeShow)) {
				// Giá trị sắp xếp ưu tiên là tên
				columnFirst = Constant.FULL_NAME_COLUMN;
			} else {
				// Lấy giá trị tìm kiếm tên
				fullname = (String) session.getAttribute("fullname");
				// Lấy giá trị tìm kiếm theo nhóm
				groupId = (int) session.getAttribute("groupId");
				// Nếu là trường hợp sắp xếp
				if (typeShow.startsWith("sort")) {
					switch (typeShow) {
					// Nếu sắp xếp theo tên
					case Constant.TYPE_SORT_FULL_NAME:
						// Đổi kiểu sắp xếp tên hiện tại thành ngược lại
						typeSortName = Common.changeSort(typeSortName);
						// Giá trị sắp xếp ưu tiên là tên
						columnFirst = Constant.FULL_NAME_COLUMN;
						break;
					// Nếu sắp xếp theo trình độ tiếng nhật
					case Constant.TYPE_SORT_CODE_LEVEL:
						// Đổi kiểu sắp xếp trình độ tiếng nhật hiện tại
						// thành ngược lại
						typeSortCodeLevel = Common.changeSort(typeSortCodeLevel);
						// Giá trị sắp xếp ưu tiên là trình độ tiếng nhật
						columnFirst = Constant.CODE_LEVEL_COLUMN;
						break;
					// Nếu sắp xếp theo ngày hết hạn
					case Constant.TYPE_SORT_END_DATE:
						// Đổi kiểu sắp xếp ngày hết hạn hiện tại thành
						// ngược lại
						typeSortEndDate = Common.changeSort(typeSortEndDate);
						// Giá trị sắp xếp ưu tiên là ngày hết hạn
						columnFirst = Constant.END_DATE_COLUMN;
						break;
					}
					// Nếu là trường hợp paging
				} else if (Constant.TYPE_PANING.equals(typeShow)) {
					// Lấy giá trị page cần chuyển
					String page = request.getParameter("page");
					// Giá trị trang cần chuyển
					paningCurrent = Integer.parseInt(page);
					// Vị trí đầu tiên cần lấy bản ghi
					offset = (paningCurrent - 1) * limit;
				}
			}
			// Lấy giá trị tổng bản ghi tìm kiếm được
			totalUser = tblUserLogic.getTotalUsers(groupId, fullname);
			// Nếu số lượng > 0
			if (totalUser > 0) {
				// Lấy danh sách phân trang
				listPaging = Common.getListPaging(totalUser, limit, paningCurrent);
				// Lấy danh sách thông tin user
				listUserInfor = tblUserLogic.getListUsers(offset, limit, groupId, fullname, columnFirst, typeSortName,
						typeSortCodeLevel, typeSortEndDate);
				// Set các biến giá trị sắp xếp ưu tiên, kiểu sắp xếp tên, kiểu
				// sắp xếp trình độ tiếng nhật, ngày hết hạn lên session
				session.setAttribute("columnFirst", columnFirst);
				session.setAttribute("typeSortName", typeSortName);
				session.setAttribute("typeSortCodeLevel", typeSortCodeLevel);
				session.setAttribute("typeSortEndDate", typeSortEndDate);

				// Set các biến giá trị tìm kiếm theo tên, theo group lên
				// session
				session.setAttribute("fullname", fullname);
				session.setAttribute("groupId", groupId);
				// Set các biến trang hiện tại, tổng bản ghi lên request
				request.setAttribute("paningCurrent", paningCurrent);
				request.setAttribute("totalUser", totalUser);
				// Set giá trị bản ghi tối đa/page, kích thước paging, danh sách
				// paging, danh sách user lên request
				request.setAttribute("limit", limit);
				request.setAttribute("sizePaging", sizePaging);
				request.setAttribute("listPaging", listPaging);
				request.setAttribute("listUserInfor", listUserInfor);
				// Ngược lại, không có bản ghi nào được tìm thấy
			} else {
				// Gửi 1 thông báo rằng không có kết quả được tìm thấy
				request.setAttribute("listEmpty", MessageProperties.getData("LIST_EMPTY"));
			}
			request.getRequestDispatcher(Constant.VIEW_ADM002).forward(request, response);
			// Nếu có lỗi
		} catch (Exception e) {
			e.getMessage();
			// Chuyển về màn hình lỗi
			response.sendRedirect(Constant.ERROR_URL);
		}
	}

}
