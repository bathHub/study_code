<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<hr>
	<form action="<%=request.getContextPath() %>/collection/test1" >
     ids1:<input type="text" name="ids[0]"><br>
     ids2:<input type="text" name="ids[2]"><br>
     ids3:<input type="text" name="ids[1]"><br>
       <input type="submit" value="提交">
    </form>
     
  <hr>
   <form action="<%=request.getContextPath() %>/collection/test2" >
  users1:<input type="text" name="users[1].name"><br>
     users2:<input type="text" name="users[0].name"><br>
     users3:<input type="text" name="users[2].name"><br>
       <input type="submit" value="提交">
     </form>
  <hr>   
    <form action="<%=request.getContextPath() %>/collection/test3" >
    map1:<input type="text" name="map['k1']"><br>
     map2:<input type="text" name="map['k2']"><br>
     map3:<input type="text" name="map['k3']"><br>
      <input type="submit" value="提交">
     </form>
     <hr>
  
  <a href="<%=request.getContextPath() %>/collection/test4/10">restful风格</a>
  <hr>
   <form action="<%=request.getContextPath() %>/collection/test5" >
    		生日:<input name="birthday" value="text">
      		<input type="submit" value="提交">
     			</form>
  
</body>
</html>