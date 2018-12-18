<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style><%@include file="/views/css/style.css"%></style>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<%@include file="/views/header/header.jsp" %>
	<!-- End vung header -->

	<!-- Begin vung input-->
	<form action="ADM003" method="post" name="inputform">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">情報確認</div>
					<div style="padding-left: 100px;">&nbsp;</div>
				</th>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="1" width="70%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left">アカウント名:</td>
								<td align="left">ntmhuong</td>
							</tr>
							<tr>
								<td class="lbl_left">グループ:</td>
								<td align="left">Nhóm 1</td>
							</tr>
							<tr>
								<td class="lbl_left">氏名:</td>
								<td align="left">Nguyễn Thị Mai Hương</td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left">名カナ</td>
							</tr>
							<tr>
								<td class="lbl_left">生年月日:</td>
								<td align="left">1983/07/08</td>
							</tr>
							<tr>
								<td class="lbl_left">メールアドレス:</td>
								<td align="left">ntmhuong@luvina.net</td>
							</tr>
							<tr>
								<td class="lbl_left">電話番号:</td>
								<td align="left">0914326386</td>
							</tr>
							<tr>
								<th colspan="2"><a href="#">日本語能力</a></th>
							</tr>
							<tr>
								<td class="lbl_left">資格:</td>
								<td align="left">Trình độ tiếng nhật cấp 1</td>
							</tr>
							<tr>
								<td class="lbl_left">資格交付日:</td>
								<td align="left">2010/07/08</td>
							</tr>
							<tr>
								<td class="lbl_left">失効日:</td>
								<td align="left">2011/07/08</td>
							</tr>
							<tr>
								<td class="lbl_left">点数:</td>
								<td align="left">290</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
		<div style="padding-left: 100px;">&nbsp;</div>
		<!-- Begin vung button -->
		<div style="padding-left: 100px;">
			<table border="0" cellpadding="4" cellspacing="0" width="300px">
				<tr>
					<th width="200px" align="center">&nbsp;</th>
					<td><input class="btn" type="submit" value="編集" /></td>
					<td><input class="btn" type="button" value="削除" /></td>
					<td><input class="btn" type="button" value="戻る" /></td>
				</tr>
			</table>
		</div>
		<!-- End vung button -->
	</form>
	<!-- End vung input -->

	<!-- Begin vung footer -->
		<%@include file="/views/footer/footer.jsp" %>
	<!-- End vung footer -->
</body>

</html>