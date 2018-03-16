<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table border="1px" width="50%" align="center" cellpadding="0"
		cellspacing="0">
		<caption>
			<h2>喜欢的香港明星</h2>
		</caption>
		<tbody>
			<tr bgcolor="gray">
				<td>id</td>
				<td>名称</td>
				<td>年龄</td>
				<td>生日</td>
			</tr>



		</tbody>


		<c:forEach items="${list}" var="role">
			<tr>
				<td><c:out value="${role.id}" /></td>
				<td><c:out value="${role.name}" /></td>
				<td><c:out value="${role.age}" /></td>
				<td><fmt:formatDate value="${role.birthday}"
						pattern="YYYY年MM月dd日hh时mm分ss秒" /></td>

			</tr>
		</c:forEach>
	</table>
</body>
</html>