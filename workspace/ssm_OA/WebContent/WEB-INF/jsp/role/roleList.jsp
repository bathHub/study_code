<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <script language="javascript" src="script/jquery.js"></script>
    <script language="javascript" src="${url}/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="${url}/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="${url}/style/blue/pageCommon.css" />
    <script type="text/javascript">
    </script>
<base href="<%=basePath%>">
<title>Insert title here</title>
</head>
<body>
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${url}/style/images/title_arrow.gif"/> 岗位管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>
<div id="MainArea">
<table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr align="center" valign="middle" id="TableTitle">
            	<td width="200px">岗位名称</td>
                <td width="300px">岗位说明</td>
                <td>相关操作</td>
            </tr>
        </thead>

		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="roleList">
			 <c:forEach items="${roleList}" var="role">
        
             <tr>
             
				<td><c:out value="${role.name}" /></td>
				<td><c:out value="${role.description}"/></td>
				<td><a href="role/roleDeleteById?id=${role.id}">删除</a>&nbsp;&nbsp;
				    <a href="role/updateRoleUI?id=${role.id }&num=update">修改</a></td>
			</tr>
         </c:forEach>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <a href="role/addRoleUI?num=add"><img src="${url}/style/images/createNew.png" /></a>
        </div>
    </div>
</div>
</body>
</html>

     