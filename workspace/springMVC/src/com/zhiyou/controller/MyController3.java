package com.zhiyou.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zhiyou.entity.User;

@Controller
public class MyController3 {
	                      @RequestMapping("/zhiyou")
	                 public void zhiyou(){
	                	System.out.println("�ҵĿ����������β���");
	               }
	                      
	                      @RequestMapping("/test1")
	                      public ModelAndView test1(){
	                    	  ModelAndView mav = new ModelAndView();
	                    	  mav.addObject("msg1","����test1");
	                    	  mav.setViewName("WEB-INF/jsp/test1.jsp");
	                    	  return mav;
	                    	}
	                      
	                      @RequestMapping("/test2")
	                      public ModelAndView test2(){
	                    	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                    	  
	                    	  User user = new User();
	                    	  user.setId(1);
	                    	  user.setAge(2);
	                    	  user.setName("������");
	                    	  user.setBirthday(new Date());
	                    	  User user2 = new User();
	                    	  user2.setId(1);
	                    	  user2.setAge(2);
	                    	  user2.setName("֣����");
	                    	  
	                    	  user2.setBirthday(new Date());
	                    	  
	                    	  List<User> list = new ArrayList<User>();
	                    	  list.add(user);
	                    	  list.add(user2);
	                    	  ModelAndView mav = new ModelAndView();
	                    	  mav.addObject("list", list);
	                    	  mav.setViewName("WEB-INF/jsp/test2.jsp");
	                    	  return mav;
	                    	  
	                    	  
	                    	  
	                      }
	                      
	

}
