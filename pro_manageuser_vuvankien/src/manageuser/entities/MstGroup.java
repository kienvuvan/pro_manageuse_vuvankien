/**
 * Copyright (C) 2018 Luvina Academy
 * Group.java 12/11/2018, Vu Van Kien
 */
package manageuser.entities;

/**
 * Class đối tượng Group
 * 
 * @author kien vu
 *
 */
public class MstGroup {

	private int groupId;
	private String groupName;

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
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

}
