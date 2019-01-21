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
 * Class thực hiện chức năng hiển thị màn hình ADM002
 * 
 * @author kien vu
 *
 */
public class ListUserController extends HttpServlet {

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
			// Lấy giá trị hiển thị số paging
			int sizePaging = ConfigProperties.getData("SIZE_PANNING");
			// Danh sách paging
			ArrayList<Integer> listPaging = new ArrayList<>();
			// Danh sách User
			ArrayList<UserInfor> listUserInfor = new ArrayList<>();
			// Biến tổng số User
			int totalUser = Constant.NUMBER_ZERO;
			// Giá trị groupId mặc định
			int groupId = Constant.NUMBER_ZERO;
			// Giá trị tên tìm kiếm mặc định
			String fullname = Constant.STRING_EMPTY;
			// Giá trị trang mặc định = 1
			int pagingCurrent = Constant.PAGE_CURRENT_DEFAULT;
			// Vị trị lấy bản ghi mặc định = 0
			int offset = Constant.NUMBER_ZERO;
			// Giá trị cột sắp xếp ưu tiên mặc định là cột fullname
			String columnSort = Constant.FULL_NAME_COLUMN;
			// Giá trị sắp xếp theo tên mặc định là tăng
			String sortByFullName = Constant.SORTASC;
			// Giá trị sắp xếp theo trình độ là giảm
			String sortByCodeLevel = Constant.SORTASC;
			// Giá trị sắp xếp theo ngày hết hạn mặc định là giảm
			String sortByEndDate = Constant.SORTDESC;
			// Khởi tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Giá trị kiểu hiển thị màn hình ADM002
			String typeShow = Common.formatString((String) request.getParameter("typeShow"),
					Constant.TYPE_LOGIN_OR_TOP);
			// Trường hợp login + top
			if (Constant.TYPE_LOGIN_OR_TOP.equals(typeShow)) {
				// Xóa hết các biến lưu ở session trừ tên người dùng
				removeSessionSearch(session);
				// Trường hợp search
			} else if (Constant.TYPE_SEARCH.equals(typeShow)) {
				// Lấy giá trị fullname từ request và format định dạng
				fullname = Common.formatString(request.getParameter("fullname"), "");
				// Lấy giá trị groupId từ request và chuyển về kiểu số
				groupId = Common.convertNumberInteger(request.getParameter("groupId"));
				// Set các giá trị lên session
				session.setAttribute("fullname", fullname);
				session.setAttribute("groupId", groupId);
				// Trường hợp sort + paging + back
			} else {
				// Lấy giá trị fullname từ session
				fullname = Common.formatString((String) session.getAttribute("fullname"), "");
				// Lấy giá trị groupId từ session
				if (session.getAttribute("groupId") != null) {
					groupId = (int) session.getAttribute("groupId");
				}
				// Trường hợp sort
				if (Constant.TYPE_SORT.equals(typeShow)) {
					// Lấy giá trị cột sắp xếp ưu tiên
					String typeSort = request.getParameter("typeSort");
					// Giá trị kiểu sắp xếp cột fullname
					sortByFullName = Common.formatString(request.getParameter("sortByFullName"), Constant.SORTASC);
					// Giá trị kiểu sắp xếp cột codeLevel
					sortByCodeLevel = Common.formatString(request.getParameter("sortByCodeLevel"), Constant.SORTASC);
					// Giá trị kiểu sắp xếp cột endDate
					sortByEndDate = Common.formatString(request.getParameter("sortByEndDate"), Constant.SORTDESC);

					switch (typeSort) {
					// Sắp xếp theo tên
					case Constant.TYPE_SORT_FULL_NAME:
						// Cột sắp xếp ưu tiên là fullname
						columnSort = Constant.FULL_NAME_COLUMN;
						// Thay đổi giá trị sắp xếp hiện tại
						sortByFullName = Common.changeSort(sortByFullName);
						break;
					// Sắp xếp theo trình độ tiếng nhật
					case Constant.TYPE_SORT_CODE_LEVEL:
						// Cột sắp xếp ưu tiên là trình độ
						columnSort = Constant.CODE_LEVEL_COLUMN;
						// Thay đổi giá trị sắp xếp hiện tại
						sortByCodeLevel = Common.changeSort(sortByCodeLevel);
						break;
					// Sắp xếp theo ngày hết hạn
					case Constant.TYPE_SORT_END_DATE:
						// Cột sắp xếp ưu tiên là ngày hết hạn
						columnSort = Constant.END_DATE_COLUMN;
						// Thay đổi giá trị sắp xếp hiện tại
						sortByEndDate = Common.changeSort(sortByEndDate);
						break;
					}
					// Set các giá trị cột sắp xếp ưu tiên, kiểu sắp xếp tên,
					// trình độ, ngày hết hạn lên session
					session.setAttribute("columnSort", columnSort);
					session.setAttribute("sortByFullName", sortByFullName);
					session.setAttribute("sortByCodeLevel", sortByCodeLevel);
					session.setAttribute("sortByEndDate", sortByEndDate);
					// Trường hợp paging + back
				} else {
					// Lấy giá trị cột sắp xếp ưu tiên từ session
					columnSort = Common.formatString((String) session.getAttribute("columnSort"),
							Constant.FULL_NAME_COLUMN);
					// Lấy giá trị kiểu sắp xếp fullname từ session
					sortByFullName = Common.formatString((String) session.getAttribute("sortByFullName"),
							Constant.SORTASC);
					// Lấy giá trị kiểu sắp xếp codelevel từ session
					sortByCodeLevel = Common.formatString((String) session.getAttribute("sortByCodeLevel"),
							Constant.SORTASC);
					// Lấy giá trị kiểu sắp xếp enddate từ session
					sortByEndDate = Common.formatString((String) session.getAttribute("sortByEndDate"),
							Constant.SORTDESC);
					switch (typeShow) {
					// Trường hợp paging
					case Constant.TYPE_PAGING:
						// Lấy giá trị trang hiện tại từ request
						pagingCurrent = Common.convertNumberInteger(request.getParameter("page"));
						// Set giá trị trang hiện tại lên session
						session.setAttribute("pagingCurrent", pagingCurrent);
						break;
					// Trường hợp back từ ADM003 hoặc ADM005
					case Constant.TYPE_BACK:
						// Lấy giá trị trang hiện tại từ session
						if (session.getAttribute("pagingCurrent") != null) {
							pagingCurrent = (int) session.getAttribute("pagingCurrent");
						}
						break;
					}
					if (pagingCurrent > Constant.NUMBER_ZERO) {
						// Tính toán vị trí đầu tiên lấy bản ghi
						offset = (pagingCurrent - 1) * limit;
					}
				}
			}
			if (groupId >= Constant.GROUP_ID_DEFAULT) {
				// Lấy giá trị tổng bản ghi tìm kiếm được
				totalUser = tblUserLogic.getTotalUsers(groupId, fullname);
			}
			// Nếu số lượng bản ghi > 0
			if (totalUser > Constant.NUMBER_ZERO && Common.checkNumberPage(pagingCurrent, limit, totalUser)) {
				// Lấy danh sách phân trang
				listPaging = Common.getListPaging(totalUser, limit, pagingCurrent);
				// Lấy danh sách thông tin user
				listUserInfor = tblUserLogic.getListUsers(offset, limit, groupId, fullname, columnSort, sortByFullName,
						sortByCodeLevel, sortByEndDate);
				request.setAttribute("pagingCurrent", pagingCurrent);
				// Set giá trị bản ghi tối đa/page, kích thước paging, danh sách
				// paging, danh sách user, các kiểu sắp xếp lên request
				request.setAttribute("totalUser", totalUser);
				request.setAttribute("limit", limit);
				request.setAttribute("sizePaging", sizePaging);
				request.setAttribute("listPaging", listPaging);
				request.setAttribute("listUserInfor", listUserInfor);
				request.setAttribute("sortByFullName", sortByFullName);
				request.setAttribute("sortByCodeLevel", sortByCodeLevel);
				request.setAttribute("sortByEndDate", sortByEndDate);
				// Ngược lại, không có bản ghi nào được tìm thấy
			} else {
				// Gửi 1 thông báo rằng không có kết quả được tìm thấy
				request.setAttribute("listEmpty", MessageProperties.getData("LIST_EMPTY"));
			}
			request.getRequestDispatcher(Constant.VIEW_ADM002).forward(request, response);
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
	 * Phương thức remove các biến lưu trữ trên session trừ biến người dùng
	 * 
	 * @param session
	 */
	private void removeSessionSearch(HttpSession session) {
		// Xóa giá trị cột sắp xếp ưu tiên khỏi session
		session.removeAttribute("typeSort");
		// Xóa giá trị kiểu sắp xếp fullname khỏi session
		session.removeAttribute("sortByFullName");
		// Xóa giá trị kiểu sắp xếp codelevel khỏi session
		session.removeAttribute("sortByCodeLevel");
		// Xóa giá trị kiểu sắp xếp enddate khỏi session
		session.removeAttribute("sortByEndDate");
		// Xóa giá trị fullname khỏi session
		session.removeAttribute("fullname");
		// Xóa giá trị groupId khỏi session
		session.removeAttribute("groupId");
	}

}
