<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>json</title>

</head>
<body>
<script type="text/javascript" src="scripts/jquery-1.3.2.min.js" ></script>

     <img alt="哈哈，图片加载失败" src="images/tu1.jpg"><br>
     
     <hr>
       
       
     <a href="<%=request.getContextPath() %>/user/register">点我</a>
     
   <script type="text/javascript">
      $(function(){
    	  
    	  
    	  $.ajax({
    		   type: "POST",
    		   url: "test1",
    		   dataType:"json",
    		   
    		   success: function(data){
    		     alert( "Data Saved: " + data );
    		   })
    		});
    	  
    	  
    	  
    	  
    
      
      
      
     </script>
     
     
</body>
</html>