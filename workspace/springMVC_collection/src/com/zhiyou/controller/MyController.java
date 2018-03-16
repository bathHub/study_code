package com.zhiyou.controller;



import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zhiyou.entity.User;
import com.zhiyou.entity.UserInfo;

@Controller
 @RequestMapping("/collection")
public class MyController {
    
	
	@RequestMapping("/test1")
	public ModelAndView test1(Integer[] ids){
		
		return null;
	}
	
	@RequestMapping("/test2")
	public ModelAndView test2(UserInfo userInfo){
		
		
		return null;
	}
	@RequestMapping("/test3")
      public ModelAndView test3(UserInfo userInfo){
		
		
		return null;
	}
	@RequestMapping("/test4/{id}")
    public ModelAndView test4(@PathVariable("id")Integer id){
		
		
		return null;
	}
	@RequestMapping("/test5")
	public ModelAndView test5(Date birthday){
		return null;
	}
	
	
}
