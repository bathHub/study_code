<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title>save</title>
</head>
<body>
	<form action="role_${role.id==null?'save':'edit'}.action" method="post">
		<input type="hidden" name="id" value="${role.id }">
		岗位名称：<input type="text" name="name" value="${role.name }"/><br>
		岗位描述：<textarea rows="5" cols="20" name="description">${role.description }</textarea><br>
		<input type="submit" value="${role.id==null?'新增':'修改'}">
		<input type="reset" value="重置">
	</form>

</body>
</html> --%>

<html>
<head>
	<title>岗位设置</title>
	<base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script language="javascript" src="script/jquery.js"></script>
    <script language="javascript" src="script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="style/blue/pageCommon.css" />
    <script type="text/javascript"> 
    </script>
</head>
<body> 

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="style/images/title_arrow.gif"/> 岗位设置
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
    <form action="role_${role.id==null?'save':'edit'}.action" method="post">
        <input type="hidden" name="id" value="${role.id }">
        <div class="ItemBlock_Title1"><!-- 信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="style/blue/images/item_point.gif" /> 岗位信息 </DIV>  -->
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td width="100">岗位名称</td>
                        <td>
                        <input type="text" name="name" value="${role.name }" class="InputStyle"/>*
                        </td>
                    </tr>
                    <tr>
                        <td>岗位说明</td>
                        <td>
                        <textarea rows="5" cols="20" name="description" class="TextareaStyle">${role.description }</textarea>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div id="InputDetailBar">
        	<%-- <input type="submit" value="${role.id==null?'新增':'修改'}"> --%>
            <input type="image" src="style/images/save.png"/>
            <a href="javascript:history.go(-1);"><img src="style/images/goBack.png"/></a>
        </div>
    </form>
</div>

</body>
</html>


