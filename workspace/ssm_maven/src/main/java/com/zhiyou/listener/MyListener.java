package com.zhiyou.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener{
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("≥ı ºªØ¡À......");
		ServletContext sc=sce.getServletContext();
		String url=sc.getContextPath();
		sc.setAttribute("url", url);
	}
}
