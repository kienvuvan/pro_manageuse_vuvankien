<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style><%@include file="/views/css/style.css"%></style>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->	
		<div>			
			<div>
			<table>
			<tr>
			<td width = "80%"><img src="views/images/logo-manager-user.gif" alt="Luvina" /></td>
			</tr>
			</table>
			</div>
		</div>

<!-- End vung header -->	

<!-- Begin vung input-->	
	<form action="ADM002" method="post" name="inputform">
	<table  class="tbl_input"   border="0" width="80%"  cellpadding="0" cellspacing="0" >	
		<tr>
			<td align="center" colspan="2">
				<div style="height:50px"></div>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<font color = "red">${errorMessage}</font>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<div style="height:70px"></div>
			</td>
		</tr>
	</table>
	</form>
<!-- End vung input -->

<!-- Begin vung footer -->
<div class = "lbl_footer">
	<br><br><br><br>
			Copyright ©　2010　ルビナソフトウエア株式会社. All rights reserved.
</div>
<!-- End vung footer -->
</body>

</html>