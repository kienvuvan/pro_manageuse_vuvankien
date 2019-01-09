<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style><%@include file="/views/css/style.css"%></style>
<script type="text/javascript" src="<c:url value="/views/js/user.js"/>"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<%@include file="/views/header/header.jsp"%>
	<!-- End vung header -->
	<c:set var="dateBirth" value="${fn:split(userInfor.birthday, '-')}" />
	<c:set var="startDate" value="${fn:split(userInfor.startDate, '-')}" />
	<c:set var="endDate" value="${fn:split(userInfor.endDate, '-')}" />
	<!-- Begin vung input-->
	<form
		action="<c:choose>
					<c:when test="${typeShow == 'add_user'}">addUserValidate.do
				    </c:when>
				    <c:otherwise>editUserValidate.do
				    </c:otherwise>
				</c:choose>"
		method="post" name="inputform">
		<input type="hidden" name="typeShow" value="validate_user" >
		<input type="hidden" name="userId" value="${userInfor.userId}">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">会員情報編集</div>
				</th>
			</tr>
			<tr>
				<td class="errMsg"><c:forEach items="${messages}" var="message">
						<div style="padding-left: 120px">${message}<br>
						</div>
					</c:forEach></td>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left"><font color="red">*</font> アカウント名:</td>
								<td align="left"><input class="txBox" type="text"
									name="loginName" value="${fn:escapeXml(userInfor.loginName)}"
									size="15" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';"></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> グループ:</td>
								<td align="left"><select name="groupId">
										<option value="0">選択してください</option>
										<c:forEach items="${listMstGroup}" var="group">
											<option value="${fn:escapeXml(group.groupId)}"
												<c:if test="${userInfor.groupId == group.groupId}">selected</c:if>>
												${fn:escapeXml(group.groupName)}</option>
										</c:forEach>
								</select> <span>&nbsp;&nbsp;&nbsp;</span></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullName" value="${fn:escapeXml(userInfor.fullName)}"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="fullNameKana"
									value="${fn:escapeXml(userInfor.fullNameKana)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 生年月日:</td>
								<td align="left"><select name="yearBirth">
										<c:forEach items="${listYear}" var="year">
											<option value="${year}"
												<c:if test="${dateBirth[0] == year}">selected</c:if>>
												${year}</option>
										</c:forEach>
								</select>年 <select name="monthBirth">
										<c:forEach items="${listMonth}" var="month">
											<option value="${month}"
												<c:if test="${dateBirth[1] == month}">selected</c:if>>
												${month}</option>
										</c:forEach>
								</select>月 <select name="dayBirth">
										<c:forEach items="${listDay}" var="day">
											<option value="${day}"
												<c:if test="${dateBirth[2] == day}">selected</c:if>>
												${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> メールアドレス:</td>
								<td align="left"><input class="txBox" type="text"
									name="email" value="${fn:escapeXml(userInfor.email)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font>電話番号:</td>
								<td align="left"><input class="txBox" type="text"
									name="tel" value="${fn:escapeXml(userInfor.tel)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<c:if test="${typeShow == 'add_user'}">
								<tr>
									<td class="lbl_left"><font color="red">*</font> パスワード:</td>
									<td align="left"><input class="txBox" type="password"
										name="password" value="${fn:escapeXml(userInfor.password)}"
										size="30" onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
								</tr>
								<tr>
									<td class="lbl_left">パスワード（確認）:</td>
									<td align="left"><input class="txBox" type="password"
										name="passwordAgain"
										value="${fn:escapeXml(userInfor.passwordAgain)}" size="30"
										onfocus="this.style.borderColor='#0066ff';"
										onblur="this.style.borderColor='#aaaaaa';" /></td>
								</tr>
							</c:if>
							<tr>
								<th align="left" colspan="2"><a href="#"
									onclick="showOrHideLevelJapan()">日本語能力</a></th>
							</tr>
							<tr class="japan">
								<td class="lbl_left">資格:</td>
								<td align="left"><select name="codeLevel">
										<option value="0">選択してください</option>
										<c:forEach items="${listMstJapan}" var="mstJapan">
											<option value="${fn:escapeXml(mstJapan.codeLevel)}"
												<c:if test="${userInfor.codeLevel == mstJapan.codeLevel}">selected</c:if>>
												${fn:escapeXml(mstJapan.nameLevel)}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr class="japan">
								<td class="lbl_left">資格交付日:</td>
								<td align="left"><select name="yearStart">
										<c:forEach items="${listYear}" var="year">
											<option value="${year}"
												<c:if test="${startDate[0] == year}">selected</c:if>>
												${year}</option>
										</c:forEach>
								</select>年 <select name="monthStart">
										<c:forEach items="${listMonth}" var="month">
											<option value="${month}"
												<c:if test="${startDate[1] == month}">selected</c:if>>
												${month}</option>
										</c:forEach>
								</select>月 <select name="dayStart">
										<c:forEach items="${listDay}" var="day">
											<option value="${day}"
												<c:if test="${startDate[2] == day}">selected</c:if>>
												${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr class="japan">
								<td class="lbl_left">失効日:</td>
								<td align="left"><select name="yearEnd">
										<c:forEach items="${listYearEndDate}" var="year">
											<option value="${year}"
												<c:if test="${endDate[0] == year}">selected</c:if>>
												${year}</option>
										</c:forEach>
								</select>年 <select name="monthEnd">
										<c:forEach items="${listMonth}" var="month">
											<option value="${month}"
												<c:if test="${endDate[1] == month}">selected</c:if>>
												${month}</option>
										</c:forEach>
								</select>月 <select name="dayEnd">
										<c:forEach items="${listDay}" var="day">
											<option value="${day}"
												<c:if test="${endDate[2] == day}">selected</c:if>>
												${day}</option>
										</c:forEach>
								</select>日</td>
							</tr>
							<tr class="japan">
								<td class="lbl_left">点数:</td>
								<td align="left"><input class="txBox" type="text"
									name="totalScore"
									value="<c:if test="${userInfor.totalScore > 0}">${fn:escapeXml(userInfor.totalScore)}</c:if>"
									size="5" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 45px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input class="btn" type="submit" value="確認" /></td>
					<td><a href="listUser.do?typeShow=back"><input class="btn"
							type="button" value="戻る" /></a></td>
				</tr>
			</table>
		</div>
		<!-- End vung button -->
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
	<%@include file="/views/footer/footer.jsp"%>
	<!-- End vung footer -->
</body>
</html>