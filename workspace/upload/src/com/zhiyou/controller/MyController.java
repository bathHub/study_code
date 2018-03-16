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
			//��ȡ������
	    	  InputStream is= image.getInputStream();
	    	  //��ȡ�������ʵ·��
	    	  String realpath=this.getPath("/images", request);
	    	  //����˵��ѡ����ʲô�ļ���ôeclipse�ͻ�����������������ȡ���ļ��ĳ�ʼ����
	    	  String name = image.getOriginalFilename();
	    	  String finalName=this.getFileName(name);
	    	  
	    	  //���� �Ѿ���ȡ��δ���ļ������ֺ�·�� ��ȡ���������
	    	  OutputStream os= new FileOutputStream(new File(realpath, finalName));
	    	  System.out.println(realpath+"#####"+finalName);
	    	  IOUtils.copy(is, os);
	    	  os.close();
	    	  is.close();
	  	  return "/WEB-INF/jsp/success.jsp";
	 
	      }
	      
	      /*ָ��������ȡ�ļ���·��*/
	      public String getPath(String pathName,HttpServletRequest request){
	    	  //ʹ������,��ȡ�ļ�����ʵ·��
			           String filePath = request.getRealPath(pathName);
			           //����һ��file���󣬽��ļ���ַ��װ��ȥ
			           File file = new File(filePath);
			           //�������ļ��ڸ�·���²�����
			           if (!file.exists()) {
			        	//��ô��һֱ����ȥ
						file.mkdirs();
					}
			           
	    	         return filePath;
	    	
	      }
	      //��δ���ϴ�����ļ�ȡ��
	      public String getFileName(String name){
	    	    String extension = FilenameUtils.getExtension(name);
	    	    //ʹ��uuid������ĸ��ļ�ȡǰ������֡���ƴ����ԭ���ļ��ĺ�׺���������ļ��ĸ�ʽ��
	    	          String finalName =  UUID.randomUUID().toString()+"."+extension;
	    	          return finalName;
	      }
	      
	      
	      
}
