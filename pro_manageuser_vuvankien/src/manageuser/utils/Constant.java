/**
 * Copyright (C) 2018 Luvina Academy
 * Constant.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.utils;

/**
 * @author kien vu
 *
 */
public class Constant {
	public static final String ROOT_URL = "http://localhost:8080/pro_manageuser_vuvankien";
	public static final String LOGIN_URL = ROOT_URL + "/login.do";
	public static final String LOGIN_URL1 = ROOT_URL + "/index.jsp";
	public static final String LIST_USER_URL = ROOT_URL + "/listUser.do";
	public static final String ERROR_URL = ROOT_URL + "/error.do";

	public static final String VIEW_ADM001 = "/views/ADM001.jsp";
	public static final String VIEW_ADM002 = "/views/ADM002.jsp";
	public static final String VIEW_ADM003 = "/views/ADM003.jsp";
	public static final String VIEW_ADM004 = "/views/ADM004.jsp";
	public static final String VIEW_ADM005 = "/views/ADM005.jsp";
	public static final String VIEW_ADM006 = "/views/ADM006.jsp";
	public static final String VIEW_SYSTEM_ERROR = "/views/System_Error.jsp";

	public static final String FULL_NAME_COLUMN = "tbl_user.full_name";
	public static final String CODE_LEVEL_COLUMN = "tbl_detail_user_japan.code_level";
	public static final String END_DATE_COLUMN = "tbl_detail_user_japan.end_date";

	public static final String SORTASC = "ASC";
	public static final String SORTDESC = "DESC";

	public static final String TYPE_LOGIN_OR_SEARCH = "login_search";
	public static final String TYPE_SORT_FULL_NAME = "sort_full_name";
	public static final String TYPE_SORT_CODE_LEVEL = "sort_code_level";
	public static final String TYPE_SORT_END_DATE = "sort_end_date";
	public static final String TYPE_PANING = "show_paning";

	public static final int PANING_FIRST = 1;

	public static final String STRING_RANDOM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static final String ALL = "全て";

	public static final int TIME_OUT = 5 * 60;
}
