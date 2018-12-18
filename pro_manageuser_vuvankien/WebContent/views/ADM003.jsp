<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style><%@include file="/views/css/style.css"%></style>
<script type="text/javascript" src="../js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->
	<%@include file="/views/header/header.jsp" %>
	<!-- End vung header -->

	<!-- Begin vung input-->
	<form action="ADM004" method="post" name="inputform">
		<table class="tbl_input" border="0" width="75%" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="left">
					<div style="padding-left: 100px;">会員情報編集</div>
				</th>
			</tr>
			<tr>
				<td class="errMsg">
					<div style="padding-left: 120px">&nbsp;</div>
				</td>
			</tr>
			<tr>
				<td align="left">
					<div style="padding-left: 100px;">
						<table border="0" width="100%" class="tbl_input" cellpadding="4"
							cellspacing="0">
							<tr>
								<td class="lbl_left"><font color="red">*</font> アカウント名:</td>
								<td align="left"><input class="txBox" type="text" name="id"
									value="" size="15" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> グループ:</td>
								<td align="left"><select name="group_id">
										<option value="0">選択してください</option>
										<option value="0">Nhóm 1</option>
										<option value="0">Nhóm 2</option>
								</select> <span>&nbsp;&nbsp;&nbsp;</span></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="name" value="" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">カタカナ氏名:</td>
								<td align="left"><input class="txBox" type="text"
									name="name" value="" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> 生年月日:</td>
								<td align="left"><select>
										<option value="2000">2000</option>
										<option value="2001">2001</option>
										<option value="2002">2002</option>
										<option value="2003">2003</option>
										<option value="2004">2004</option>
										<option value="2005">2005</option>
										<option value="2006">2006</option>
										<option value="2007">2007</option>
										<option value="2008">2008</option>
										<option value="2009">2009</option>
										<option value="2010" selected="selected">2010</option>
										<option value="2011">2011</option>
								</select>年 <select>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12" selected="selected">12</option>
								</select>月 <select>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
										<option value="24">24</option>
										<option value="25">25</option>
										<option value="26">26</option>
										<option value="27">27</option>
										<option value="28">28</option>
										<option value="29">29</option>
										<option value="30">30</option>
										<option value="31" selected="selected">31</option>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> メールアドレス:</td>
								<td align="left"><input class="txBox" type="text"
									name="email" value="" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font>電話番号:</td>
								<td align="left"><input class="txBox" type="text"
									name="tel" value="" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left"><font color="red">*</font> パスワード:</td>
								<td align="left"><input class="txBox" type="password"
									name="email" value="" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<td class="lbl_left">パスワード（確認）:</td>
								<td align="left"><input class="txBox" type="password"
									name="email" value="" size="30"
									onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" /></td>
							</tr>
							<tr>
								<th align="left" colspan="2"><a href="#">日本語能力</a></th>
							</tr>
							<tr>
								<td class="lbl_left">資格:</td>
								<td align="left"><select name="kyu_id">
										<option value="0">選択してください</option>
										<option value="0">N1</option>
										<option value="0">N2</option>
										<option value="0">N3</option>
										<option value="0">N4</option>
										<option value="0">N5</option>
								</select></td>
							</tr>
							<tr>
								<td class="lbl_left">資格交付日:</td>
								<td align="left"><select>
										<option value="2000">2000</option>
										<option value="2001">2001</option>
										<option value="2002">2002</option>
										<option value="2003">2003</option>
										<option value="2004">2004</option>
										<option value="2005">2005</option>
										<option value="2006">2006</option>
										<option value="2007">2007</option>
										<option value="2008">2008</option>
										<option value="2009">2009</option>
										<option value="2010" selected="selected">2010</option>
										<option value="2011">2011</option>
								</select>年 <select>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12" selected="selected">12</option>
								</select>月 <select>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
										<option value="24">24</option>
										<option value="25">25</option>
										<option value="26">26</option>
										<option value="27">27</option>
										<option value="28">28</option>
										<option value="29">29</option>
										<option value="30">30</option>
										<option value="31" selected="selected">31</option>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left">失効日:</td>
								<td align="left"><select>
										<option value="2000">2000</option>
										<option value="2001">2001</option>
										<option value="2002">2002</option>
										<option value="2003">2003</option>
										<option value="2004">2004</option>
										<option value="2005">2005</option>
										<option value="2006">2006</option>
										<option value="2007">2007</option>
										<option value="2008">2008</option>
										<option value="2009">2009</option>
										<option value="2010" selected="selected">2010</option>
										<option value="2011">2011</option>
								</select>年 <select>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12" selected="selected">12</option>
								</select>月 <select>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
										<option value="24">24</option>
										<option value="25">25</option>
										<option value="26">26</option>
										<option value="27">27</option>
										<option value="28">28</option>
										<option value="29">29</option>
										<option value="30">30</option>
										<option value="31" selected="selected">31</option>
								</select>日</td>
							</tr>
							<tr>
								<td class="lbl_left">点数:</td>
								<td align="left"><input class="txBox" type="text"
									name="total" value="" size="5"
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