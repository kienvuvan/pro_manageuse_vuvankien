/**
 * Copyright (C) 2018 Luvina Academy
 * Constant.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.utils;

/**
 * Class chức các hằng số của chương trình
 * 
 * @author kien vu
 *
 */
public class Constant {
	// Đường dẫn vào màn hình Login
	public static final String LOGIN_URL = "login.do";
	// Đường dẫn chạy đến link hiển thị màn hình ADM002
	public static final String LIST_USER_URL = "listUser.do";
	// Đường dẫn hiển thị màn hình ADM003
	public static final String ADD_USER_INPUT = "addUserInput.do";
	// Đường dẫn hiển thị màn hình ADM004 với chức năng thêm User
	public static final String ADD_USER_CONFIRMS = "addUserConfirm.do";
	// Đường dẫn hiển thị màn hình ADM004 với chức năng cập nhật User
	public static final String EDIT_USER_CONFIRMS = "editUserConfirm.do";
	// Đường dẫn hiển thị màn hình thông báo ADM006
	public static final String SUCCESS_URL = "success.do";
	// Đường dẫn hiển thị màn hình System_Error
	public static final String ERROR_URL = "error";

	// Đường dẫn tương đối đến màn hình ADM001.jsp
	public static final String VIEW_ADM001 = "/views/jsp/ADM001.jsp";
	// Đường dẫn tương đối đến màn hình ADM002.jsp
	public static final String VIEW_ADM002 = "/views/jsp/ADM002.jsp";
	// Đường dẫn tương đối đến màn hình ADM003.jsp
	public static final String VIEW_ADM003 = "/views/jsp/ADM003.jsp";
	// Đường dẫn tương đối đến màn hình ADM004.jsp
	public static final String VIEW_ADM004 = "/views/jsp/ADM004.jsp";
	// Đường dẫn tương đối đến màn hình ADM005.jsp
	public static final String VIEW_ADM005 = "/views/jsp/ADM005.jsp";
	// Đường dẫn tương đối đến màn hình ADM006.jsp
	public static final String VIEW_ADM006 = "/views/jsp/ADM006.jsp";
	// Đường dẫn tương đối đến màn hình ChangePass.jsp
	public static final String CHANGE_PASS = "/views/jsp/ChangePass.jsp";
	// Đường dẫn tương đối đến màn hình System_Error.jsp
	public static final String VIEW_SYSTEM_ERROR = "/views/jsp/System_Error.jsp";

	// Tên màn hình ADM003
	public static final String ADM003 = "ADM003";

	// Tên các cột sort
	public static final String FULL_NAME_COLUMN = "tbl_user.full_name";
	public static final String CODE_LEVEL_COLUMN = "tbl_detail_user_japan.code_level";
	public static final String NAME_LEVEL_COLUMN = "mst_japan.name_level";
	public static final String END_DATE_COLUMN = "tbl_detail_user_japan.end_date";

	// Giá trị sort
	public static final String SORTASC = "ASC";
	public static final String SORTDESC = "DESC";

	// Các kiểu hiển thị màn hình ADM002
	public static final String TYPE_LOGIN_OR_TOP = "login_top";
	public static final String TYPE_SORT = "sort";
	public static final String TYPE_PAGING = "show_paning";
	public static final String TYPE_SEARCH = "search";
	public static final String TYPE_TOP = "top";
	public static final String TYPE_BACK = "back";

	// Các kiểu hiển thị màn hình ADM003
	public static final String TYPE_ADD_USER = "add_user";
	public static final String TYPE_VALIDATE_USER = "validate_user";
	public static final String TYPE_EDIT_USER = "edit_user";
	public static final String TYPE_BACK_ADM003 = "back_adm003";

	// Các kiểu cột sắp xếp
	public static final String TYPE_SORT_FULL_NAME = "full_name";
	public static final String TYPE_SORT_CODE_LEVEL = "code_level";
	public static final String TYPE_SORT_END_DATE = "end_date";

	// Giá trị 0
	public static final int NUMBER_ZERO = 0;
	// Giá trị chuỗi rỗng
	public static final String STRING_EMPTY = "";
	// Giá trị trang hiện tại mặc định
	public static final int PAGE_CURRENT_DEFAULT = 1;
	// Chuỗi để sinh random chuỗi
	public static final String STRING_RANDOM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	// Thời gian timeout
	public static final int TIME_OUT = 5 * 60;

	// Giá trị độ dài chuỗi salt khi tạo
	public static final int SALT_LENGHT = 30;

	// Năm bắt đầu
	public static final int FROM_YEAR = 1980;

	// Định dạng ngày tháng năm
	public static final String DATE_FORMAT = "yyyy-M-d";

	// Độ dài nhỏ nhất của tài khoản
	public static final int MIN_LOGIN_NAME_LENGTH = 6;
	// Độ dài lớn nhất của tài khoản
	public static final int MAX_LOGIN_NAME_LENGTH = 15;
	// Độ dài nhỏ nhất của tel
	public static final int MIN_TEL_LENGTH = 5;
	// Độ dài lớn nhất của tel
	public static final int MAX_TEL_LENGTH = 14;
	// Độ dài nhỏ nhất của password
	public static final int MIN_PASSWORD_LENGTH = 8;
	// Độ dài lớn nhất của password
	public static final int MAX_PASSWORD_LENGTH = 15;
	// Độ dài nhỏ nhất của totalScore
	public static final int MIN_TOTAL_SCORE = 1;
	// Độ dài lớn nhất của totalScore
	public static final int MAX_TOTAL_SCORE = 3;
	// Độ dài lớn nhất của chuỗi
	public static final int MAX_LENGTH = 255;
	// Giá trị không chọn codeLevel
	public static final String CODE_LEVEL_DEFAULT_VALUE = "0";
	// Giá trị khi so sánh ngày lớn hơn
	public static final int AFTER_DAY = 1;

	// Giá trị trả về khi thao tác với CSDL bị lỗi
	public static final int ERROR_EXCUTE_DATABASE = -1;
	// Giá trị userId trả về khi email không tồn tại
	public static final int ID_NOT_EXISTED_EMAIL = -1;
	// Giá trị userId trả về khi loginName không tồn tại
	public static final int ID_NOT_EXISTED_LOGIN_NAME = -1;
	// Giá trị rule của admin
	public static final int RULE_ADMIN = 0;
	// Giá trị rule của user
	public static final int RULE_USER = 1;

	// Thông báo thêm User thành công
	public static final String ADD_USER_SUCCESS = "add_success";
	// Thông báo sửa thông tin User thành công
	public static final String EDIT_USER_SUCCESS = "edit_success";
	// Thông báo xóa thông tin User thành công
	public static final String DELETE_USER_SUCCESS = "delete_success";
	// Thông báo thay đổi mật khẩu User thành công
	public static final String CHANGE_PASSWORD_SUCCESS = "change_password_success";

	// Thực hiện thành công
	public static final String SUCCESS = "success";
	// Lỗi hệ thống
	public static final String SYSTEM_ERROR = "system_error";
	// Lỗi user không tồn tại
	public static final String NOT_EXISTED_USER = "not_existed_user";
	// Giá trị trạng thái chuyển sang màn hình khác là OK
	public static final String ACCEPT = "ACCEPT";

	// Giá trị userId trong trường hợp thêm người dùng
	public static final int ID_ADD_USER = 0;
	// Giá trị codeLevel khi người dùng không có trình độ
	public static final String CODE_LEVEL_DEFAULT = "0";
	// Giá trị groupId nếu người dùng không chọn nhóm
	public static final int GROUP_ID_DEFAULT = 0;
}
