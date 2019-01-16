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
	<!-- Begin vung input-->
	<form action="changePass.do" method="post" name="inputform">
		<input type="hidden" name="userId" value="${userId}">
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
								<td class="lbl_left"><font color="red">*</font> パスワード:</td>
								<td align="left"><input class="txBox" type="password"
									name="password" value="${fn:escapeXml(password)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">パスワード（確認）:</td>
								<td align="left"><input class="txBox" type="password"
									name="passwordAgain" value="${fn:escapeXml(passwordAgain)}" size="30"
									onfocus="this.style.borderColor='#0066ff';"
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
					<td><a href="viewDetailUser.do?userId=${userId}"><input class="btn"
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