package com.zhiyou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhiyou.entity.User;
import com.zhiyou.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/reg.action")
	public String reg(User user) throws Exception{
		userService.insertUser(user);
		return "/WEB-INF/jsp/success.jsp";
	}
	
	@RequestMapping("/regUI.action")
	public String regUI() throws Exception{
		return "/WEB-INF/jsp/regUI.jsp";
	}

}
