<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function isThree(num) {

		return num == 3;

	}

	function myFunc1() {
		var st = "string";
		alert(typeof st)

	}

	function myFunc() {
		 myvar = 120;
		 alert(myvar.toString(2))
		if (isThree(myvar)) {
			alert("the num is 3 and isThree return" + isThree(myvar))

		} else {

			alert("the num is not 3 and isThree return" + isThree(myvar))
		}
	}

	function myFunc3() {
		alert(myvar)
	}
	function  myFunc4(){
		var str="这是第一行。\n这是第二行。";
		alert(str)
		
		
	}
	//alert("this is a test");//显示一条提示信息
	function myFunc2() {
		myFunc();
		myFunc1();
		myFunc3();
		myFunc4();
	}
	var cnDays = ["星期一","星期二","星期三","星期四","星期五","星期六","星期日"];
	for(index in cnDays){
		
		
		document.writeln("先在显示的是："+cnDays[index]+"<br/>")
	}
	
	var arr= [[1,2],[3,4,5],[6,7,8,9]];
	document.writeln(arr[1][2])
	function helloWorld(){
		
		alert("这是测试！");
	} 
	
	helloWorld();
</script>
<title>Insert title here</title>
</head>
<body>
	<div>
		<input type="button" value="测试"
			onclick="alert('just a test');return false;">
			 <a href="#" onclick="myFunc2()">click</a>
			 
			 <a href="second.jsp">点击跳转</a>
	</div>
</body>
</html>