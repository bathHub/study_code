package com.zhiyou.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhiyou.entity.User;

@Controller
@RequestMapping("/user")
public class UserController {
@RequestMapping("/test1")
@ResponseBody
public Object test1(){
	User user = new User();
	user.setId(1);
	user.setName("¹þ¹þ");
	user.setBirthday(new Date());
	user.setPassword("123");
	return user;
	
	
}
}
