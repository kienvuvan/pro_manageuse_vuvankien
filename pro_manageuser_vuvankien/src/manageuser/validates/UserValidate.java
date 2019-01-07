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
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException, Exception {
		ArrayList<String> message = new ArrayList<>();
		try {
			// Nếu tên tài khoản trống
			if ("".equals(username)) {
				// Thêm thông báo lỗi tài khoản trống
				message.add(MessageErrorProperties.getData("ER001_USERNAME"));
			}
			// Nếu mật khẩu trống
			if ("".equals(password)) {
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
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		return message;
	}

	/**
	 * Phương thức kiểm tra định dạng các trường của đối tượng UserInfor
	 * 
	 * @param userInfor
	 *            đối tượng UserInfor cần kiểm tra
	 * @return mảng lỗi
	 */
	public ArrayList<String> validateUserInfor(UserInfor userInfor) throws ClassNotFoundException, SQLException {
		// Tạo danh sách chứa các thông báo lỗi
		ArrayList<String> message = new ArrayList<>();
		try {
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
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
				// Nếu tài khoản đã tồn tại
			} else if (tblUserLogicImpl.checkExitsUsername(loginName)) {
				// Thêm thông báo lỗi tài khoản đã tồn tại
				message.add(MessageErrorProperties.getData("ER003_USERNAME"));
			}

			MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
			// Check group
			// Lấy ra giá trị của groupId
			int groupId = userInfor.getGroupId();
			// Nếu chưa chọn nhóm
			if (groupId <= 0) {
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
			if(!Common.isEmpty(fullNameKana)){
				// Kiểm tra độ dài
				// Nếu độ dài lớn hơn độ dài lớn nhất cho phép
				if(!Common.checkMaxLength(Constant.MAX_LENGTH, fullNameKana)){
					// Thêm thông báo lỗi về độ dài
					message.add(MessageErrorProperties.getData("ER006_FULLNAME_KANA"));
				// Nếu chuỗi không phải ký tự kana
				}else if(!Common.checkKana(fullNameKana)){
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
				// Nếu email tồn tại
			} else if (tblUserLogicImpl.checkExitsEmail(email)) {
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

			// Check password
			// Lấy giá trị password
			String password = userInfor.getPassword();
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
			}

			// Check passwordAgain
			// Lấy giá trị passwordAgain
			String passwordAgain = userInfor.getPasswordAgain();
			// Nếu passwordAgain trống
			if (Common.isEmpty(passwordAgain)) {
				// Thêm thông báo lỗi passwordAgain trống
				message.add(MessageErrorProperties.getData("ER001_PASSWORD_AGAIN"));
				// Nếu passwordAgain không trùng với password
			} else if (!password.equals(passwordAgain)) {
				// Thêm thông báo lỗi mật khẩu xác nhận không đúng
				message.add(MessageErrorProperties.getData("ER017"));
			}

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
				// Nếu điểm không phải là số HalfSize
				} else if (!Common.checkNumberHalfSize(totalScore)) {
					// Thêm lỗi không phải số HalfSize
					message.add(MessageErrorProperties.getData("ER018_TOTAL_SCORE"));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		return message;
	}

}
