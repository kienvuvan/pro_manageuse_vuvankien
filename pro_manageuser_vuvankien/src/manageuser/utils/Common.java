/**
 * Copyright (C) 2018 Luvina Academy
 * Common.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import manageuser.entities.TblDetailUserJapan;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;

/**
 * Class chứa các phương thức dùng chung trong projectF
 * 
 * @author kien vu
 *
 */
public class Common {

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);

	/**
	 * Phương thức mã hóa các ký tự đặc biệt trong HTML
	 * 
	 * @param input
	 *            Chuỗi nhập vào
	 * @return Trả về chuỗi sau khi mã hóa
	 */
	public static String enCodeHtml(String input) {
		return input.replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("\'", "&#x27;")
				.replaceAll("/", "&#x2F;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	/**
	 * Phương thức tạo thêm 1 chuỗi ngẫu nhiên để mã hóa kèm thêm với chuỗi cần
	 * mã hóa
	 * 
	 * @param length
	 *            Độ dài chuỗi sinh ngẫu nhiên ra
	 * @return Chuỗi sinh ngẫu nhiên
	 */
	public static String creatSalt(int length) {
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder stringBuilder = new StringBuilder(length);
		String stringRandom = Constant.STRING_RANDOM;
		// Duyệt vòng for để tạo chuỗi ngẫu nhiên có độ dài length
		for (int i = 0; i < length; i++) {
			stringBuilder.append(stringRandom.charAt(secureRandom.nextInt(stringRandom.length())));
		}
		return stringBuilder.toString();
	}

	/**
	 * Phương thức mã hóa chuỗi đầu vào theo chuẩn SHA-1
	 * 
	 * @param input
	 *            Chuỗi cần mã hóa
	 * @param salt
	 *            Chuỗi thêm vào để mã hóa
	 * @return Chuỗi sau khi mã hóa
	 * @throws NoSuchAlgorithmException
	 *             Lỗi khi không thực hiện mã hóa theo chuẩn SHA-1
	 */
	public static String encrypt(String input, String salt) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = null;
		try {
			// Tạo đối tượng mã hóa theo chuẩn SHA-1
			messageDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.getMessage();
			throw e;
		}
		// Trả về chuỗi sau khi mã hóa
		return Base64.getEncoder().encodeToString(messageDigest.digest((salt + input).getBytes()));
	}

	/**
	 * Phương thức kiểm tra xem đã có tài khoản admin đăng nhập hay chưa?
	 * 
	 * @param session
	 *            HttpSession
	 * @return true nếu admin đã đăng nhập và ngược lại
	 * @throws ClassNotFoundException,
	 *             SQLException
	 */
	public static boolean checkLogin(HttpSession session) throws ClassNotFoundException, SQLException {
		try {
			// Lấy ra giá trị tên đăng nhập từ session
			String usernameLogin = (String) session.getAttribute("usernameLogin");
			// Tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Nếu trên session đã tồn tại và tài khoản đó đúng trong CSDL thì trả về true
			if (usernameLogin != null && tblUserLogic.getUserByLogIn(usernameLogin).getSalt() != null) {
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return false;
	}

	/**
	 * Phương thức format định dạng chuỗi đầu vào
	 * 
	 * @param input
	 *            Chuỗi cần format định dạng
	 * @param defaultValue
	 *            giá trị mặc định trả về
	 * @return String trả về chuỗi mặc định, nếu null trả về chuỗi mặc định
	 */
	public static String formatString(String input, String defaultValue) {
		return input == null ? defaultValue : input.trim();
	}

	/**
	 * Phương thức lấy danh sách hiển thị paging
	 * 
	 * @param totalRecords
	 *            int Tổng số bản ghi
	 * @param limit
	 *            int số lượng hiển thị bản ghi tối đa trên 1 trang
	 * @param currentPage
	 *            int trang hiện tại
	 * @return danh sách các paging cần hiển thị
	 */
	public static ArrayList<Integer> getListPaging(int totalRecords, int limit, int currentPage) {
		ArrayList<Integer> listPaning = new ArrayList<>();
		// Tính tổng số paging
		int totalPaning = (totalRecords % limit == 0) ? totalRecords / limit : totalRecords / limit + 1;
		if (totalPaning > 1) {
			// Lấy giá trị kích thước paging trên 1 trang
			int sizePaning = Integer.valueOf(ConfigProperties.getData("SIZE_PANNING"));
			// Tính toán giá trị paging đầu tiên khi hiển thị
			int numberPaningStart = currentPage % sizePaning == 0 ? currentPage - sizePaning + 1
					: currentPage - currentPage % sizePaning + 1;
			// Tính toán giá trị paging cuối cùng khi hiển thị
			int numberPaningEnd = numberPaningStart + sizePaning - 1;
			if (numberPaningEnd > totalPaning) {
				numberPaningEnd = totalPaning;
			}
			// Duyệt từ giá trị đầu đến cuối và thêm vào danh sách listPaging
			for (int i = numberPaningStart; i <= numberPaningEnd; i++) {
				listPaning.add(i);
			}
		}
		// Thêm vào danh sách
		return listPaning;
	}

	/**
	 * Phương thức đổi kiểu sắp xếp ngược lại
	 * 
	 * @param typeSort
	 *            Kiểu sắp xếp hiện tại
	 * @return String kiểu sắp xếp ngược lại. ASC thành DESC hoặc ngược lại
	 */
	public static String changeSort(String typeSort) {
		return Constant.SORTASC.equals(typeSort) ? Constant.SORTDESC : Constant.SORTASC;
	}

	/**
	 * Phương thức replace ký tự willCard trong mysql
	 * 
	 * @param input
	 *            Chuỗi đầu vào
	 * @return String Chuỗi sau khi xử lý
	 */
	public static String replaceWillCard(String input) {
		String result = "";
		// Nếu chuỗi không rỗng
		if (input != null) {
			// Replace kí tự willCard
			result = input.replace("_", "\\_").replace("%", "\\%");
		}
		// Trả về chuỗi sau khi replace
		return result;
	}

	/**
	 * Phương thức kiểm tra định dạng trang có phải là số 0
	 * 
	 * @param page
	 *            Chuỗi định dạng cần kiểm tra
	 * @return giá trị int của số trang. Nếu page không phải số thì trả về 0
	 */
	public static int convertNumberInteger(String input) {
		int result = -1;
		// Nếu chuỗi null thì trả về mặc định 0
		if (input == null) {
			result = 0;
		// Nếu chuỗi có định dạng số nguyên thì tiến hành chuyển về dạng số
		} else if (input.matches("\\d+")) {
			result = Integer.parseInt(input);
		}
		// Trả về giá trị kiểu số của chuỗi
		return result;
	}

	/**
	 * Phương thức kiểm tra số trang có nằm trong khoảng số paning đang có hay
	 * không
	 * 
	 * @param page
	 *            trang cần hiển thị
	 * @param limit
	 *            số lượng giới hạn bản ghi trên trang
	 * @param totalRecord
	 *            tổng số bản ghi tìm kiếm được
	 * @return true nếu trang cần hiển thị tồn tại và ngược lại
	 */
	public static boolean checkNumberPage(int page, int limit, int totalRecord) {
		// Tính tổng số paging từ tổng số bản ghi và số lượng giới hạn trong 1 paging
		int totalPaning = (totalRecord % limit == 0) ? totalRecord / limit : totalRecord / limit + 1;
		// Nếu page tồn tại 
		if (page > 0 && page <= totalPaning) {
			return true;
		}
		return false;
	}

	/**
	 * Phương thức lấy ra danh sách năm
	 * 
	 * @param fromYear
	 *            năm bắt đầu
	 * @param toYear
	 *            năm kết thúc
	 * @return danh sách năm
	 */
	public static ArrayList<Integer> getListYear(int fromYear, int toYear) {
		// Tạo danh sách kiểu Integer để lưu trữ năm
		ArrayList<Integer> listYear = new ArrayList<>();
		// Duyệt từ giá trị năm bắt đầu đến năm kết thúc
		for (int index = fromYear; index <= toYear; index++) {
			// Thêm giá trị năm vào danh sách
			listYear.add(index);
		}
		// Trả về danh sách năm
		return listYear;
	}

	/**
	 * Phương thức lấy ra danh sách các tháng trong năm
	 * 
	 * @return danh sách tháng từ 1-12
	 */
	public static ArrayList<Integer> getListMonth() {
		// Tạo danh sách kiểu Integer để lưu trữ tháng
		ArrayList<Integer> listMonth = new ArrayList<>();
		// Duyệt từ 1 đến 12 và thêm giá trị vào danh sách
		for (int index = 1; index <= 12; index++) {
			listMonth.add(index);
		}
		// Trả về danh sách tháng
		return listMonth;
	}

	/**
	 * Phương thức lấy ra danh sách các ngày trong tháng
	 * 
	 * @return danh sách tháng từ 1-31
	 */
	public static ArrayList<Integer> getListDay() {
		// Tạo danh sách kiểu Integer để lưu trữ ngày
		ArrayList<Integer> listDay = new ArrayList<>();
		// Duyệt từ 1 đến 31 và thêm giá trị vào danh sách
		for (int index = 1; index <= 31; index++) {
			listDay.add(index);
		}
		// Trả về danh sách ngày
		return listDay;
	}

	/**
	 * Phương thức chuyển chuỗi về định dạng ngày kiểu String
	 * 
	 * @param dateInput
	 *            Chuỗi định dạng cần chuyển về đúng định dạng
	 * @return String trả về đúng định dạng chuỗi đó nếu chuỗi đúng định dạng
	 *         cho trước và sẽ được chuyển về ngày hiện tai nếu định dạng ngày
	 *         sai
	 */
	public static String formatDateString(String dateString) {
		// Chuyển chuỗi về định dạng kiểu Date
		Date date = formatDate(dateString);
		// Chuyển về kiểu string với format định trước
		return simpleDateFormat.format(date);
	}

	/**
	 * Phương thức chuyển chuỗi về định dạng kiểu Date
	 * 
	 * @param dateString
	 *            chuỗi ngày tháng năm cần chuyển về kiểu Date
	 * @return giá trị Date của chuỗi sau khi chuyển (nếu chuỗi đúng định dạng
	 *         thì trả về giá trị, ngược lại trả về giá trị ngày tháng năm hiện
	 *         tại)
	 */
	public static Date formatDate(String dateString) {
		// Tạo đối tượng Calendar
		Calendar calendar = Calendar.getInstance();
		// Tạo đối tượng Date có giá trị là ngày hiện tại
		Date dateNow = new Date(calendar.getTimeInMillis());
		// Tạo đối tượng date cần để trả về
		Date date;
		try {
			// Set giá trị của chuỗi cần chuyển cho calendar
			calendar.setTime(simpleDateFormat.parse(dateString));
			// Gán date bằng giá trị chuyển đối được
			date = new Date(calendar.getTimeInMillis());
		// Nếu chuyển đổi có lỗi
		} catch (ParseException e) {
			// Gán date bằng giá trị ngày tháng năm hiện tại
			date = dateNow;
		}
		// Trả về giá trị date sau khi format
		return date;
	}

	/**
	 * Phương thức dùng để chuyển đổi chuỗi về kiểu date với giá trị sau đó 1 năm
	 * @param endDate chuỗi cần format
	 * @return trả về chuỗi định dạng kiểu ngày sau khi chuyển đổi với giá trị sau giá trị ban đầu 1 năm
	 */
	public static String formatEndDate(String endDate) {
		// Tạo đối tượng Calendar 
		Calendar calendar = Calendar.getInstance();
		// Thêm 1 năm
		calendar.add(Calendar.YEAR, 1);
		// Tạo đối tượng Date có giá trị là thời gian calendar vừa tạo
		Date dateAfterOneYear = new Date(calendar.getTimeInMillis());
		// Tạo đối tượng date cần để trả về
		Date date;
		try {
			// Set giá trị của chuỗi cần chuyển cho calendar
			calendar.setTime(simpleDateFormat.parse(endDate));
			// Gán date bằng giá trị chuyển đối được
			date = new Date(calendar.getTimeInMillis());
			// Nếu chuyển đổi có lỗi
		} catch (ParseException e) {
			// Gán date bằng giá trị dateAfterOneYear
			date = dateAfterOneYear;
		}
		// Trả về giá trị date sau khi format
		return simpleDateFormat.format(date);
	}

	/**
	 * Phương thức format định dạng ngày tháng của các trường ngày tháng của đối
	 * tượng UserInfor
	 * 
	 * @param userInfor
	 *            đối tượng UserInfor cần kiểm tra
	 * @return đối tượng UserInfor sau khi format
	 */
	public static UserInfor formatDateUserInfor(UserInfor userInfor) {
		// Gán giá trị ngày sinh
		userInfor.setBirthday(formatDateString(userInfor.getBirthday()));
		// Gán giá trị ngày cấp chứng chỉ trình độ tiếng Nhật
		userInfor.setStartDate(formatDateString(userInfor.getStartDate()));
		// Gán giá trị hết hạn trình độ
		userInfor.setEndDate(formatEndDate(userInfor.getEndDate()));
		return userInfor;
	}

	/**
	 * Phương thức kiểm tra 1 chuỗi có phải là chuỗi rỗng không?
	 * 
	 * @param input
	 *            chuỗi cần kiểm tra
	 * @return true nếu chuỗi là rỗng và ngược lại
	 */
	public static boolean isEmpty(String input) {
		// Nếu chuỗi rỗng
		if ("".equals(input)) {
			// Trả về tru
			return true;
		// Ngược lại, chuỗi không phải rỗng
		} else {
			// Trả về false
			return false;
		}
	}

	/**
	 * Phương thức kiểm tra độ dài 1 chuỗi có nằm trong khoảng từ minLength đến
	 * maxLength không
	 * 
	 * @param minLength
	 *            độ dài nhỏ nhất
	 * @param maxLength
	 *            độ dài lớn nhất
	 * @param input
	 *            chuỗi cần kiểm tra
	 * @return true nếu độ dài nằm trong khoảng từ minLength đến maxLength và
	 *         ngược lại
	 */
	public static boolean checkLengthLimit(int minLength, int maxLength, String input) {
		// Lấy giá trị độ dài của chuỗi
		int length = input.length();
		// Nếu chuỗi không có độ dài từ giá trị minLength đến maxLength
		if (length > maxLength || length < minLength) {
			// Trả về false
			return false;
		// Ngược lại, nếu chuỗi có độ dài phù hợp
		} else {
			// Trả về true
			return true;
		}
	}

	/**
	 * Phương thức kiểm tra độ dài 1 chuỗi có lớn hơn giá trị maxLength cho phép
	 * hay không?
	 * 
	 * @param maxLength
	 *            độ dài lớn nhất
	 * @param input
	 *            chuỗi cần kiểm tra
	 * @return true nếu chuỗi có độ dài nhỏ hơn maxLength và ngược lại
	 */
	public static boolean checkMaxLength(int maxLength, String input) {
		// Nếu chuỗi có độ dài lớn maxLength(độ dài cho phép)
		if (input.length() > maxLength) {
			// Trả về false
			return false;
		// Ngược lại, nếu chuỗi có độ dài thỏa mãn
		} else {
			// Trả về true
			return true;
		}
	}

	/**
	 * Check format định dạng trường login
	 * 
	 * @param loginName
	 *            chuỗi cần kiểm tra
	 * @return true nếu đúng định dạng và ngược lại
	 */
	public static boolean checkFormatLoginName(String loginName) {
		// Chuỗi so khớp loginName
		String regex = "[a-z,A-Z,_]\\w+";
		return loginName.matches(regex);
	}

	/**
	 * Phương thức kiểm tra 1 chuỗi có phải là 1 email hay không
	 * 
	 * @param email
	 *            chuỗi cần kiểm tra
	 * @return true nếu chuỗi có định dạng email và ngược lại
	 */
	public static boolean checkFormatEmail(String email) {
		// Chuỗi so khớp email
		String regex = "[_A-Za-z]([_A-Za-z0-9]+)*(\\.[_A-Za-z0-9]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
		return email.matches(regex);
	}

	/**
	 * Phương thức kiểm tra 1 chuỗi có phải là 1 số điện thoại hay không?
	 * 
	 * @param tel
	 *            chuỗi cần kiểm tra
	 * @return true nếu chuỗi có định dạng tel và ngược lại
	 */
	public static boolean checkFormatTel(String tel) {
		// Chuỗi so khớp số điện thoại
		String regex = "[0-9]{1,4}-[0-9]{1,4}-[0-9]{1,4}";
		return tel.matches(regex);
	}

	/**
	 * Kiểm tra ký tự kana
	 * 
	 * @param input
	 * @return true nếu là ký tự kana, false nếu không.
	 */
	public static boolean checkKana(String input) {
		// So khớp kí tự kana nếu đúng
		if (input.matches("[\u30a0-\u30ff]+")) {
			// Trả về true
			return true;
		// Nếu không phải kí tự kana trả về false
		} else {
			return false;
		}
	}

	/**
	 * Phương thức kiểm tra halfSize password
	 * 
	 * @param password
	 *            chuỗi password cần kiểm tra
	 * @return true nếu chuỗi đúng định dạng và ngược lại
	 */
	public static boolean checkHalfSize(String password) {
		String regex = "[a-zA-Z0-9]+";
		return password.matches(regex);
	}

	/**
	 * Phương thức kiểm tra chuỗi có phải là số HalfSize hay không ?
	 * 
	 * @param input
	 *            chuỗi cần kiểm tra
	 * @return true nếu chuỗi là số HalfSize và ngược lại
	 */
	public static boolean checkNumberHalfSize(String input) {
		String regex = "\\d+";
		return input.matches(regex);
	}

	/**
	 * Tạo ra 1 getKeySession random
	 * 
	 * @param input
	 *            đầu vào
	 * @return chuỗi đã random
	 */
	public static String getKeySession(String input) {
		return input + System.currentTimeMillis();
	}

	/**
	 * Phương thức tạo đối tượng TblUser từ đối tượng UserInfor
	 * 
	 * @param userInfor
	 *            đối tượng UserInfor
	 * @return đối tượng TblUser được tạo ra với các thông tin chính từ đối
	 *         tượng UserInfor
	 * @throws NoSuchAlgorithmException
	 */
	public static TblUser creatTblUserFromUserInfor(UserInfor userInfor) throws NoSuchAlgorithmException {
		// Tạo đối tượng TblUser
		TblUser tblUser = new TblUser();
		try {
			// Gán giá trị userId cho đối tượng TblUser
			tblUser.setUserId(userInfor.getUserId());
			// Gán giá trị groupId cho đối tượng TblUser
			tblUser.setGroupId(userInfor.getGroupId());
			// Gán giá trị loginName cho đối tượng TblUser
			tblUser.setLoginName(userInfor.getLoginName());
			// Tạo salt
			String salt = creatSalt(30);
			// Gán giá trị password
			tblUser.setPassword(encrypt(userInfor.getPassword(), salt));
			// Gán giá trị fullName cho đối tượng TblUser
			tblUser.setFullName(userInfor.getFullName());
			// Gán giá trị fullNameKana cho đối tượng TblUser
			tblUser.setFullNameKana(userInfor.getFullNameKana());
			// Gán giá trị tel cho đối tượng TblUser
			tblUser.setTel(userInfor.getTel());
			// Gán giá trị email cho đối tượng TblUser
			tblUser.setEmail(userInfor.getEmail());
			// Gán giá trị birthday cho đối tượng TblUser
			tblUser.setBirthday(formatDate(userInfor.getBirthday()));
			// Gán giá trị rule cho đối tượng TblUser
			tblUser.setRule(Constant.RULE_USER);
			// Gán giá trị salt cho đối tượng TblUser
			tblUser.setSalt(salt);
			// Nếu có lỗi tạo salt
		} catch (NoSuchAlgorithmException e) {
			e.getMessage();
			throw e;
		}
		// Trả về đối tượng TblUser
		return tblUser;
	}

	/**
	 * Phương thức tạo đối tượng TblDetailUserJapan từ đối tượng UserInfor
	 * 
	 * @param userInfor
	 *            đối tượng UserInfor
	 * @return đối tượng TblDetailUserJapan được tạo ra với các thông tin chính
	 *         từ đối tượng UserInfor
	 */
	public static TblDetailUserJapan creatTblDetailUserJapanFromUserInfor(UserInfor userInfor) {
		// Tạo đối tượng TblDetailUserJapan
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		// Gán giá trị userId cho đối tượng TblDetailUserJapan
		tblDetailUserJapan.setUserId(userInfor.getUserId());
		// Gán giá trị codeLevel cho đối tượng TblDetailUserJapan
		tblDetailUserJapan.setCodeLevel(userInfor.getCodeLevel());
		// Gán giá trị startDate cho đối tượng TblDetailUserJapan
		tblDetailUserJapan.setStartDate(formatDate(userInfor.getStartDate()));
		// Gán giá trị endDate cho đối tượng TblDetailUserJapan
		tblDetailUserJapan.setEndDate(formatDate(userInfor.getEndDate()));
		// Gán giá trị total cho đối tượng TblDetailUserJapan
		tblDetailUserJapan.setTotal(convertNumberInteger(userInfor.getTotalScore()));
		// Trả về đối tượng TblDetailUserJapan
		return tblDetailUserJapan;
	}
}
