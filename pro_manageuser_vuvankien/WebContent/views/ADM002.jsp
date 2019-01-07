<%@page import="manageuser.utils.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
<!-- Begin vung dieu kien tim kiem -->
<form action="listUser.do" method="get" name="mainform">
<input type="hidden" value="search" name="typeShow">
	<table  class="tbl_input" border="0" width="90%"  cellpadding="0" cellspacing="0" >		
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				会員名称で会員を検索します。検索条件無しの場合は全て表示されます。 
			</td>
		</tr>
		<tr>
			<td width="100%">
				<table class="tbl_input" cellpadding="4" cellspacing="0" >
					<tr>
						<td class="lbl_left">氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="fullname" value="${fn:escapeXml(fullname)}"
							size="20" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="lbl_left">グループ:</td>
						<td align="left" width="80px">
							<select name="groupId">
								<option value="0">全て</option>
								<c:forEach items="${listAllGroups}" var="group">
									<option value="${fn:escapeXml(group.groupId)}" <c:if test="${groupId == group.groupId}">selected</c:if>>
										${fn:escapeXml(group.groupName)}
									</option>
								</c:forEach>
							</select>						
						</td>
						<td align="left">
							<input class="btn" type="submit" value="検索" />
							<a href="addUserInput.do"><input class="btn" type="button" value="新規追加"/></a>							
						</td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	<!-- End vung dieu kien tim kiem -->
</form>
	<!-- Begin vung hien thi danh sach user -->
	<table class="tbl_list" border="1" cellpadding="4" cellspacing="0" width="80%">
		
		<tr class="tr2">
			<th align="center" width="20px">
				ID
			</th>
			<th align="left">
				氏名 
				<a href = "listUser.do?typeShow=sort&typeSort=full_name&sortByFullName=${sortByFullName}">
				<c:choose>
					<c:when test="${sortByFullName == 'ASC'}">▲▽
				    </c:when>
				    <c:otherwise>△▼
				    </c:otherwise>
				</c:choose>
				</a>
			</th>
			<th align="left">
				生年月日
			</th>
			<th align="left">
				グループ
			</th>
			<th align="left">
				メールアドレス
			</th>
			<th align="left" width="70px">
				電話番号
			</th>
			<th align="left">
				日本語能力  <a href = "listUser.do?typeShow=sort&typeSort=code_level&sortByCodeLevel=${sortByCodeLevel}">
					<c:choose>
						<c:when test="${sortByCodeLevel == 'ASC'}">▲▽
					    </c:when>
					    <c:otherwise>△▼
					    </c:otherwise>
					</c:choose>
				</a>
			</th>
			<th align="left">
				失効日 <a href = "listUser.do?typeShow=sort&typeSort=end_date&sortByEndDate=${sortByEndDate}">
					<c:choose>
						<c:when test="${sortByEndDate == 'ASC'}">▲▽
					    </c:when>
					    <c:otherwise>△▼
					    </c:otherwise>
					</c:choose>
				</a>
			</th>
			<th align="left">
				点数
			</th>
		</tr>
		<c:forEach items="${listUserInfor}" var="userInfor">
			<tr>
				<td align="right">
				<a href="ADM005">${fn:escapeXml(userInfor.userId)}</a>
			</td>
			<td>
				${fn:escapeXml(userInfor.fullName)}
			</td>
			<td align="center">
				${fn:escapeXml(userInfor.birthday)}
			</td>
			<td>
				${fn:escapeXml(userInfor.groupName)}
			</td>
			<td>
				${fn:escapeXml(userInfor.email)}
			</td>
			<td>
				${fn:escapeXml(userInfor.tel)}
			</td>
			<td>
				${fn:escapeXml(userInfor.nameLevel)}
			</td>
			<td align="center">
				${fn:escapeXml(userInfor.endDate)}
			</td>
			<td align="right">
				<c:choose>
					<c:when test="${userInfor.totalScore == '0'}">
				    </c:when>
				    <c:otherwise>
				      ${fn:escapeXml(userInfor.totalScore)}
				    </c:otherwise>
				</c:choose>
			</td>
			</tr>	
		</c:forEach>
		<c:if test="${listEmpty != null}">
			<tr align="center">
				<td colspan="9"><c:out value="${listEmpty}"/></td>
			</tr>
		</c:if>
	</table>
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->
	<table>
		<tr>
			<td class = "lbl_paging">
				<c:forEach items="${listPaging}" var="paging" varStatus="status">
					<c:if test="${paging > listPaging.size() && status.index == 0}"><a href = "listUser.do?typeShow=show_paning&page=${paging-sizePaging}"><c:out value="<<"/></a></c:if>
					<c:choose>
						<c:when test="${paging == pagingCurrent}">
							${fn:escapeXml(paging)} &nbsp;
					    </c:when>
					    <c:otherwise>
					      	<a href = "listUser.do?typeShow=show_paning&page=${paging}">${fn:escapeXml(paging)}</a> &nbsp;
					    </c:otherwise>
					</c:choose>
					<c:if test="${paging < totalUser/limit && status.index == listPaging.size()-1}"><a href = "listUser.do?typeShow=show_paning&page=${paging+1}"><c:out value=">>"/></a></c:if>
				</c:forEach>
			</td>
		</tr>
	</table>
	<!-- End vung paging -->

	<!-- Begin vung footer -->
		<%@include file="/views/footer/footer.jsp" %>
	<!-- End vung footer -->
<%response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");%>
</body>
</html>