/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserValidate.java Dec 12, 2018, Vu Van Kien
 */
package manageuser.validates;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * Class chứa các phương thức validate liên quan đến đối tượng User
 * 
 * @author kien vu
 *
 */
public class UserValidate {

	/**
	 * Phương thức kiểm tra thông tin tài khoản mật khẩu người dùng nhập
	 * 
	 * @param username
	 *            Tên tài khoản người dùng nhập
	 * @param password
	 *            Mật khẩu người dùng nhập
	 * @return ArrayList<String> Mảng thông báo lỗi
	 * @throws NoSuchAlgorithmException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<String> validateLogin(String username, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		ArrayList<String> message = new ArrayList<>();
		try {
			// Nếu tên tài khoản trống
			if (Common.isEmpty(username)) {
				// Thêm thông báo lỗi tài khoản trống
				message.add(MessageErrorProperties.getData("ER001_USERNAME"));
			}
			// Nếu mật khẩu trống
			if (Common.isEmpty(password)) {
				// Thêm thông báo lỗi mật khẩu trống
				message.add(MessageErrorProperties.getData("ER001_PASSWORD"));
			}
			// Nếu tên tài khoản và mật khẩu đều đã nhập
			if (message.isEmpty()) {
				TblUserLogic tblUserLogic = new TblUserLogicImpl();
				// Nếu thông tin tài khoản mật khẩu không chính xác
				if (!tblUserLogic.checkExitsAccount(username, password)) {
					// Thêm thông báo lỗi tài khoản hoặc mật khẩu không chính
					// xác
					message.add(MessageErrorProperties.getData("ER016"));
				}
			}
		} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		return message;
	}

	/**
	 * Phương thức kiểm tra định dạng các trường của đối tượng UserInfor khi
	 * thêm mới
	 * 
	 * @param userInfor
	 *            đối tượng UserInfor cần kiểm tra
	 * @return ArrayList<String> danh sách các lỗi
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<String> validateUserInfor(UserInfor userInfor) throws ClassNotFoundException, SQLException {
		// Tạo danh sách chứa các thông báo lỗi
		ArrayList<String> message = new ArrayList<>();
		try {
			// Tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Lấy giá trị userId
			int userId = userInfor.getUserId();
			// Check loginName
			// Lấy giá trị loginName
			String loginName = userInfor.getLoginName();
			// Nếu loginName rỗng
			if (Common.isEmpty(loginName)) {
				// Thêm thông báo lỗi tài khoản trống
				message.add(MessageErrorProperties.getData("ER001_USERNAME"));
				// Nếu độ dài không nằm trong khoảng từ 6-15 ký tự
			} else if (!Common.checkLengthLimit(Constant.MIN_LOGIN_NAME_LENGTH, Constant.MAX_LOGIN_NAME_LENGTH,
					loginName)) {
				// Thêm thông báo lỗi về độ dài
				message.add(MessageErrorProperties.getData("ER007_USERNAME"));
			} else if (!Common.checkFormatLoginName(loginName)) {
				// Thêm thông báo lỗi tài khoản sai định dạng
				message.add(MessageErrorProperties.getData("ER019_USERNAME"));
				// Nếu tài khoản đã tồn tại trong trường hợp thêm
			} else if (userId == Constant.ID_ADD_USER
					&& tblUserLogicImpl.checkExitsUsername(loginName) != Constant.ID_NOT_EXISTED_LOGIN_NAME) {
				// Thêm thông báo lỗi tài khoản đã tồn tại
				message.add(MessageErrorProperties.getData("ER003_USERNAME"));
			}

			MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
			// Check group
			// Lấy ra giá trị của groupId
			int groupId = userInfor.getGroupId();
			// Nếu chưa chọn nhóm
			if (groupId <= Constant.GROUP_ID_DEFAULT) {
				// Thêm thông báo chưa chọn nhóm
				message.add(MessageErrorProperties.getData("ER002_GROUP_ID"));
				// Nếu nhóm không tồn tại
			} else if (Common.isEmpty(mstGroupLogicImpl.getGroupNameById(groupId))) {
				// Thêm thông báo nhóm không tồn tại
				message.add(MessageErrorProperties.getData("ER004_GROUP_ID"));
			}
			// Check fullName
			String fullName = userInfor.getFullName();
			if (Common.isEmpty(fullName)) {
				// Thêm thông báo lỗi tên trống
				message.add(MessageErrorProperties.getData("ER001_FULLNAME"));
			} else if (!Common.checkMaxLength(Constant.MAX_LENGTH, fullName)) {
				// Thêm thông báo lỗi độ dài
				message.add(MessageErrorProperties.getData("ER006_FULLNAME"));
			}

			// Check fullNameKana
			String fullNameKana = userInfor.getFullNameKana();
			// Nếu fullNameKana có nhập thì kiểm tra
			if (!Common.isEmpty(fullNameKana)) {
				// Kiểm tra độ dài
				// Nếu độ dài lớn hơn độ dài lớn nhất cho phép
				if (!Common.checkMaxLength(Constant.MAX_LENGTH, fullNameKana)) {
					// Thêm thông báo lỗi về độ dài
					message.add(MessageErrorProperties.getData("ER006_FULLNAME_KANA"));
					// Nếu chuỗi không phải ký tự kana
				} else if (!Common.checkKana(fullNameKana)) {
					// Thêm thông báo lỗi về ký tự Kana
					message.add(MessageErrorProperties.getData("ER009_FULLNAME_KANA"));
				}
			}

			// Check birthday
			// Lấy ra giá trị của trường birthday
			String birthday = userInfor.getBirthday();
			// Nếu ngày sinh không đúng định dạng
			if (!birthday.equals(Common.formatDateString(birthday))) {
				// Thêm thông báo lỗi định dạng ngày
				message.add(MessageErrorProperties.getData("ER011_BIRTH_DAY"));
			}

			// Check mail
			// Lấy ra giá trị email
			String email = userInfor.getEmail();
			// Nếu email để trống
			if (Common.isEmpty(email)) {
				// Thêm thông báo lỗi email trống
				message.add(MessageErrorProperties.getData("ER001_EMAIL"));
				// Nếu độ dài vượt quá 255 ký tự
			} else if (!Common.checkMaxLength(Constant.MAX_LENGTH, email)) {
				// Thêm thông báo lỗi về độ dài
				message.add(MessageErrorProperties.getData("ER006_EMAIL"));
				// Nếu định dạng email sai
			} else if (!Common.checkFormatEmail(email)) {
				// Thêm thông báo lỗi sai định dạng email
				message.add(MessageErrorProperties.getData("ER005_EMAIL"));
				// Nếu email tồn tại trong CSDL
			} else if (tblUserLogicImpl.checkExitsEmail(email, userId)) {
				// Thêm thông báo lỗi email đã tồn tại
				message.add(MessageErrorProperties.getData("ER003_EMAIL"));
			}

			// Check phone
			// Lấy giá trị phone
			String tel = userInfor.getTel();
			// Nếu tel để trống
			if (Common.isEmpty(tel)) {
				// Thêm thông báo lỗi tel trống
				message.add(MessageErrorProperties.getData("ER001_TEL"));
				// Nếu độ dài không nằm trong khoảng từ 5-14
			} else if (!Common.checkLengthLimit(Constant.MIN_TEL_LENGTH, Constant.MAX_TEL_LENGTH, tel)) {
				// Thêm thông báo lỗi về độ dài
				message.add(MessageErrorProperties.getData("ER007_TEL"));
				// Nếu chuỗi không phải là số điện thoại
			} else if (!Common.checkFormatTel(tel)) {
				// Thêm thông báo lỗi chuỗi không phải số điện thoại
				message.add(MessageErrorProperties.getData("ER005_TEL"));
			}

			// Nếu là trường hợp addUser thì mới check trường password và
			// passwordAgain
			if (userId == Constant.ID_ADD_USER) {
				// Check password
				// Lấy giá trị password
				String password = userInfor.getPassword();
				// Lấy giá trị passwordAgain
				String passwordAgain = userInfor.getPasswordAgain();
				// Lấy ra mảng lỗi kiểm tra trường password và passwordAgain
				ArrayList<String> messageCheckPasses = validatePassword(password, passwordAgain);
				// Duyệt mảng lỗi check pass
				for (String messageCheckPass : messageCheckPasses) {
					// Thêm vào mảng lỗi
					message.add(messageCheckPass);
				}
			}

			// Tạo đối tượng MstJapanLogicImpl
			MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
			// Check trình độ tiếng Nhật
			// Lấy giá trị codeLevel
			String codeLevel = userInfor.getCodeLevel();
			// Nếu không chọn trình độ tiếng Nhật
			if (!Constant.CODE_LEVEL_DEFAULT_VALUE.equals(codeLevel)) {

				// Nếu trình độ tiếng Nhật không tồn tại
				if (Common.isEmpty(mstJapanLogicImpl.getNameLevelById(codeLevel))) {
					// Thêm lỗi trình độ tiếng Nhật không tồn tại
					message.add(MessageErrorProperties.getData("ER004_CODE_LEVEL"));
				}

				// Check ngày cấp chứng chỉ
				// Lấy giá trị ngày cấp chứng chỉ
				String startDate = userInfor.getStartDate();
				// Nếu ngày cấp chứng chỉ không đúng định dạng
				if (!startDate.equals(Common.formatDateString(startDate))) {
					// Thêm thông báo lỗi định dạng ngày
					message.add(MessageErrorProperties.getData("ER011_START_DATE"));
				}

				// Check ngày hết hạn
				// Lấy giá trị ngày hết hạn
				String endDate = userInfor.getEndDate();
				// Nếu ngày cấp chứng chỉ không đúng định dạng
				if (!endDate.equals(Common.formatDateString(endDate))) {
					// Thêm thông báo lỗi định dạng ngày
					message.add(MessageErrorProperties.getData("ER011_END_DATE"));
				} else {
					Date rangeDate = Common.formatDate(startDate);
					Date expirationDate = Common.formatDate(endDate);
					// Nếu ngày hết hạn không sau ngày cấp
					if (expirationDate.compareTo(rangeDate) != Constant.AFTER_DAY) {
						// Thêm lỗi ngày hết hạn phải sau ngày cấp
						message.add(MessageErrorProperties.getData("ER012_END_DATE"));
					}
				}

				// Check tổng điểm
				String totalScore = userInfor.getTotalScore();
				// Nếu không nhập điểm
				if (Common.isEmpty(totalScore)) {
					// Thêm thông báo lỗi không nhập điểm tiếng Nhật
					message.add(MessageErrorProperties.getData("ER001_TOTAL_SCORE"));
					// Nếu độ dài không nằm trong khoảng từ 1-3
				} else if (!Common.checkLengthLimit(Constant.MIN_TOTAL_SCORE, Constant.MAX_TOTAL_SCORE, totalScore)) {
					// Thêm thông báo lỗi về độ dài
					message.add(MessageErrorProperties.getData("ER007_SCORE"));
					// Nếu điểm không phải là số HalfSize
				} else if (!Common.checkNumberHalfSize(totalScore)) {
					// Thêm lỗi không phải số HalfSize
					message.add(MessageErrorProperties.getData("ER018_TOTAL_SCORE"));
				}
			}
			// Nếu có lỗi
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		return message;
	}

	/**
	 * Phương thức kiểm tra tính hợp lệ các trường password của đối tượng
	 * UserInfor
	 * 
	 * @param password
	 *            giá trị trường mật khẩu
	 * @param passwordAgain
	 *            giá trị trường mật khẩu nhập lại
	 * @return ArrayList<String> danh sách các lỗi
	 */
	public ArrayList<String> validatePassword(String password, String passwordAgain) {
		// Tạo danh sách chứa các thông báo lỗi
		ArrayList<String> message = new ArrayList<>();
		// Check password
		// Nếu password trống
		if (Common.isEmpty(password)) {
			// Thêm thông báo lỗi password trống
			message.add(MessageErrorProperties.getData("ER001_PASSWORD"));
			// Nếu độ dài không nằm trong khoảng từ 8-15
		} else if (!Common.checkLengthLimit(Constant.MIN_PASSWORD_LENGTH, Constant.MAX_PASSWORD_LENGTH, password)) {
			// Thêm thông báo lỗi về độ dài
			message.add(MessageErrorProperties.getData("ER007_PASSWORD"));
			// Nếu password không đúng định dạng halfSize
		} else if (!Common.checkHalfSize(password)) {
			// Thêm thông báo lỗi định dạng
			message.add(MessageErrorProperties.getData("ER008_PASSWORD"));
		// Nếu password nhập đúng thì bắt đầu kiểm tra passwordAgain
		// Nếu passwordAgain không trùng password
		} else if (!password.equals(passwordAgain)) {
			// Thêm thông báo lỗi mật khẩu xác nhận không đúng
			message.add(MessageErrorProperties.getData("ER017"));
		}
		return message;
	}

	/**
	 * Phương thức kiểm tra đối tượng UserInfor trước khi cập nhật vào CSDL
	 * trong trường hợp sửa
	 * 
	 * @param userInfor
	 *            đối tượng UserInfor cần kiểm tra
	 * @return true nếu đối tượng thỏa mãn và ngược lại nếu loginName tồn
	 *         tại(trong trường hợp thêm) hoặc loginName không đúng với tài
	 *         khoản (Trường hợp sửa) hoặc nhóm không tồn tại hoặc email tồn tại
	 *         hoặc trình độ tiếng Nhật không tồn tại
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean validateConfirmUserInfor(UserInfor userInfor) throws ClassNotFoundException, SQLException {
		try {
			// Tạo đối tượng TblUserLogicImpl
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			// Tạo đối tượng MstGroupLogicImpl
			MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
			// Tạo đối tượng MstJapanLogicImpl
			MstJapanLogic mstJapanLogicImpl = new MstJapanLogicImpl();
			// Get giá trị userId
			int userId = userInfor.getUserId();
			// Nếu nhóm không tồn tại trongCSDL hoặc email không tồn tại trong
			// CSDL hoặc trình độ tiếng Nhật không tồn tại
			if (Common.isEmpty(mstGroupLogicImpl.getGroupNameById(userInfor.getGroupId()))
					|| !tblUserLogicImpl.checkExitsEmail(userInfor.getEmail(), userId)
					|| Common.isEmpty(mstJapanLogicImpl.getNameLevelById(userInfor.getCodeLevel()))) {
				// Nếu là trường hợp thêm người dùng
				if (userId == Constant.ID_ADD_USER) {
					// Nếu tài khoản tồn tại trong CSDL
					if (tblUserLogicImpl
							.checkExitsUsername(userInfor.getLoginName()) != Constant.ID_NOT_EXISTED_LOGIN_NAME) {
						// Trả về false
						return false;
					}
					// Nếu là trường hợp sửa thông tin chi tiết
				} else {
					// Nếu userId của loginName không đúng với userId của người
					// đó
					if (tblUserLogicImpl.checkExitsUsername(userInfor.getLoginName()) != userId) {
						// Trả về false
						return false;
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// In ra lỗi
			System.out.println(this.getClass().getSimpleName() + " : " + new Object() {
			}.getClass().getEnclosingMethod().getName() + " - " + e.getMessage());
			// Ném ra 1 lỗi
			throw e;
		}
		// Trả về true
		return true;
	}
}
