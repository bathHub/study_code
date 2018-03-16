package com.zhiyou.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zhiyou.entity.User;

@Controller
@RequestMapping("/user")
public class MyController {
	       
	@RequestMapping("/test1")
	 public ModelAndView test1(@RequestParam("n")String name,
			 						@RequestParam(required=true)Integer age,
			 						@RequestParam(defaultValue="110")Double sal){
		 
		 System.out.println(name+":"+age+":"+sal);
		 
		 return null;
	 }
	
	
	   @RequestMapping("/register")
	 public void register(User user, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		       request.getRequestDispatcher("/WEB-INF/jsp/dis.jsp").forward(request, response);
		 
	 }
	   
         @RequestMapping("dis")
	   public ModelAndView dis(){
		   ModelAndView mav = new ModelAndView();
		   mav.setViewName("/WEB-INF/jsp/dis.jsp");
		   return mav;
	   
	   

         }}
