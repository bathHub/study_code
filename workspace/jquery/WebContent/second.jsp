<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
  function displayNodeInfo(){
	  var x = document.getElementById("test");
	  alert(x.innerText);
	  alert("nodeName:"+x.nodeName+",nodeValue:"+x.nodeValue+",nodeType:"+x.nodeType);
	 }
  
  function displayNodeInfo1(){
	  var x= document.getElementsByTagName("li");
	  alert("x的内容是："+x.innerText);
	 alert("x的长度为："+x.length);
  }

  
</script>
<title>Insert title here</title>
</head>
<body>
   <input type="button" value="测试" onclick="displayNodeInfo1();"/><br>
   <div id="test">Test</div>
   <ul>
   	<li>叶问</li>
   	<li>风诚勿扰</li>
   	<li>我来啦</li>
   <li>哈哈哈</li>
   </ul>
</body>
</html>