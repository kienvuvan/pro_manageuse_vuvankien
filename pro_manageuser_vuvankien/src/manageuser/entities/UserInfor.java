/**
 * Copyright (C) 2018 Luvina Academy
 * UserInfor.java Dec 13, 2018, Vu Van Kien
 */
package manageuser.entities;

import java.sql.Date;

/**
 * @author kien vu
 *
 */
public class UserInfor {
	private int userId;
	private String fullName;
	private Date birthday;
	private String groupName;
	private String email;
	private String tel;
	private String nameLevel;
	private Date endDate;
	private int totalScore;

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the nameLevel
	 */
	public String getNameLevel() {
		return nameLevel;
	}

	/**
	 * @param nameLevel
	 *            the nameLevel to set
	 */
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDateLevelJapan
	 *            the endDateLevelJapan to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the totalScore
	 */
	public int getTotalScore() {
		return totalScore;
	}

	/**
	 * @param totalScore
	 *            the totalScore to set
	 */
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

}
