package com.ali.listener;

import java.lang.reflect.Executable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class UserListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		String url = servletContext.getContextPath();
		servletContext.setAttribute("url",url);
		System.out.println("≥ı ºª∞¡À°£°£°£");
	}
	
     
}
