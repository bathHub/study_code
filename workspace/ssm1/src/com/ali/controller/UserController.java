package com.ali.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ali.entity.User;
import com.ali.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;

@RequestMapping("/reg") 
	public String reg(User user) throws Exception{
		
		 System.out.println(user.getName()+":"+user.getPassword());
		 
		 user.setName(user.getName());
		 user.setPassword(user.getPassword());
		 user.setBirthday(user.getBirthday());
		 userServiceImpl.insertUser(user);
		return "/WEB-INF/jsp/success.jsp";
	}
	
@RequestMapping("/login") 
public String login(User user){
	
	
	return "/WEB-INF/jsp/reg.jsp";
}

}
