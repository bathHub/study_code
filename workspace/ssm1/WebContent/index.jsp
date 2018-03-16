<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开始界面</title>
<link href="${url}/script/css/base/ui.all.css" rel="stylesheet" />
<link href="${url}/script/demos.css" rel="stylesheet" />
<script type="text/javascript" src=" ${url}/script/js/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="${url}/script/js/ui.datepicker.js"></script>
<script type="text/javascript">
var flag = false;
$(function(){
	$(".dat").datepicker();
})
function checkPwd(pwd2){
	var pwd1 = $("#pwd1").val();
	if(pwd1!=pwd2){
		flag = false;
		$("#s1").html("密码输入不一致");
	}else{
		flag =true;
		$("#s1").html("");
	}
}
function check(){
	var name = $("#name1").val();
	var pwd1 = $("#pwd1").val();
	var pwd2 = $("#pwd2").val();
	var birthday1 = $("#birthday1").val();
	
	//非空判断
	if(name==null||name==""){
		return false;
	}
	if(pwd1==null||pwd1==""){
		return false;
	}
	if(pwd2==null||pwd2==""){
		return false;
	}
	if(birthday1==null||birthday1==""){
		return false;
	}
	return true;
	
}


</script>
</head>
<body>
      ${url}
     <form action="<%=request.getContextPath() %>/user/reg" method="post" onsubmit="return check()">
         用户名：<input type="text" name="name" id="name1"/><br>
             密码：<input type="password" name="password" id="pwd1"><br>
     确认密码：<input type="password" onchange="checkPwd(this.value)" id="pwd2"><span id="s1"></span><br>
      出生日期：<input  type="text" class="dat" name="birthday" id="birthday1"><br>
     
     <input type="submit" value="注册"><br><input type="reset" value="清空">
     </form>
	<form action="#">
     <input type="submit" value="登陆">
     </form>
</body>
</html>