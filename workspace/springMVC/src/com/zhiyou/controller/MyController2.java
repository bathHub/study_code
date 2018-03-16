package com.zhiyou.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

public class MyController2 implements HttpRequestHandler{

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		          request.setAttribute("msg", "这是控制的第二种方法");
		          request.getRequestDispatcher("WEB-INF/jsp/con2.jsp").forward(request, response);
		            
		
	}

}
 