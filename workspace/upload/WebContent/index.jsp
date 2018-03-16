<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="<%=request.getContextPath() %>/user/reg" method="post" enctype="mutipart/form-data">
       用户名：<input type="text" name="username"><br>
  上传头像：<input type="file" name="image"><br> 
       提交:<input type="submit" value="提交"><br>
</form>
</body>
</html>