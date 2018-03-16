package com.zhiyou.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/user")
public class MyController {
	      @RequestMapping("/reg")
        public String upload(String username,MultipartFile image,Model model,HttpServletRequest request) throws IOException{
			//获取输入流
	    	  InputStream is= image.getInputStream();
	    	  //获取保存的真实路径
	    	  String realpath=this.getPath("/images", request);
	    	  //就是说，选中了什么文件那么eclipse就会调用这个函数，来获取该文件的初始名称
	    	  String name = image.getOriginalFilename();
	    	  String finalName=this.getFileName(name);
	    	  
	    	  //根据 已经获取的未来文件的名字和路径 获取输出流对象
	    	  OutputStream os= new FileOutputStream(new File(realpath, finalName));
	    	  System.out.println(realpath+"#####"+finalName);
	    	  IOUtils.copy(is, os);
	    	  os.close();
	    	  is.close();
	  	  return "/WEB-INF/jsp/success.jsp";
	 
	      }
	      
	      /*指定方法获取文件的路径*/
	      public String getPath(String pathName,HttpServletRequest request){
	    	  //使用请求,获取文件的真实路径
			           String filePath = request.getRealPath(pathName);
			           //创建一个file对象，将文件地址包装进去
			           File file = new File(filePath);
			           //如果这个文件在该路径下不存在
			           if (!file.exists()) {
			        	//那么就一直查下去
						file.mkdirs();
					}
			           
	    	         return filePath;
	    	
	      }
	      //给未来上传后的文件取名
	      public String getFileName(String name){
	    	    String extension = FilenameUtils.getExtension(name);
	    	    //使用uuid来随机的给文件取前面的名字。再拼接上原来文件的后缀名（代表文件的格式）
	    	          String finalName =  UUID.randomUUID().toString()+"."+extension;
	    	          return finalName;
	      }
	      
	      
	      
}
