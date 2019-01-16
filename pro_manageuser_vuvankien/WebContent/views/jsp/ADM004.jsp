<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style><%@include file="/views/css/style.css"%></style>
<script type="text/javascript" src='<c:url value="/views/js/user.js"/>'></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<%@include file="/views/header/header.jsp"%>
	<!-- End vung header -->

	<!-- Begin vung input-->
	<form
		action="<c:choose>
					<c:when test="${typeShow == 'add_user'}">addUserConfirm.do?session=${keySession}
				    </c:when>
				    <c:otherwise>editUserConfirm.do?session=${keySession}
				    </c:otherwise>
				</c:choose>"
		method="post" name="inputform">
		<input type="hidden" name="typeShow" value="${typeShow}">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">
						情報確認<br> 入力された情報をＯＫボタンクリックでＤＢへ保存してください
					</div>
					<div style="padding-left: 100px;">&nbsp;</div>
				</th>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="1" width="70%" class="tbl_input" cellpadding="4" cellspacing="0">
							<tr>
								<td class="lbl_left">アカウント名:</td>
								<td align="left">${fn:escapeXml(userInfor.loginName)}</td>
							</tr>
							<tr>
								<td class="lbl_left">グループ:</td>
								<td align="left">${fn:escapeXml(userInfor.groupName)}</td>
							</tr>
							<tr>
								<td class="lbl_left">氏名:</td>
								<td align="left">${fn:escapeXml(userInfor.fullName)}</td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left">${fn:escapeXml(userInfor.fullNameKana)}</td>
							</tr>
							<tr>
								<td class="lbl_left">生年月日:</td>
								<td align="left">${fn:replace(userInfor.birthday, '-','/')}</td>
							</tr>
							<tr>
								<td class="lbl_left">メールアドレス:</td>
								<td align="left">${fn:escapeXml(userInfor.email)}</td>
							</tr>
							<tr>
								<td class="lbl_left">電話番号:</td>
								<td align="left">${fn:escapeXml(userInfor.tel)}</td>
							</tr>
							<tr>
								<th align="left" colspan="2">
								<a href="#" onclick="showOrHideLevelJapan()">日本語能力</a>
							</th>
							</tr>
						</table>
					</div>
					<div style="padding-left: 100px;" id="japan">
						<table border="1" width="70%" class="tbl_input" cellpadding="4" cellspacing="0">
							<tr>
								<td class="lbl_left">資格:</td>
								<td align="left">${fn:escapeXml(userInfor.nameLevel)}</td>
							</tr>
							<tr>
								<td class="lbl_left">資格交付日:</td>
								<td align="left"><c:if test="${userInfor.nameLevel != ''}">${fn:replace(userInfor.startDate, '-','/')}</c:if></td>
							</tr>
							<tr>
								<td class="lbl_left">失効日:</td>
								<td align="left"><c:if test="${userInfor.nameLevel != ''}">${fn:replace(userInfor.endDate, '-','/')}</c:if></td>
							</tr>
							<tr>
								<td class="lbl_left">点数:</td>
								<td align="left"><c:choose>
										<c:when
											test="${userInfor.totalScore > 0 && userInfor.nameLevel != ''}">${fn:escapeXml(userInfor.totalScore)}
									    </c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose></td>
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
					<td><input class="btn" type="submit" value="OK" /></td>
					<td>
						<a href="
							<c:choose>
									<c:when test="${typeShow == 'add_user'}">addUserValidate.do?typeShow=back_adm003&session=${keySession}</c:when>
					    		<c:otherwise>editUserValidate.do?typeShow=back_adm003&userId=${userInfor.userId}&session=${keySession}
					    		</c:otherwise>
							</c:choose>">
							<input class="btn" type="button" value="戻る" />
						</a>
					</td>
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