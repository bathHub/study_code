package com.sxxy.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sxxy.dao.LoginDAO;
import com.sxxy.dao.impl.LoginDAOImpl;
import com.sxxy.po.CustomerCareInfo;
import com.sxxy.po.CustomerInfo;
import com.sxxy.po.LinkRecordInfo;
import com.sxxy.po.NoticeInfo;
import com.sxxy.po.UserInfo;
import com.sxxy.service.LoginService;
import com.sxxy.service.WorkService;
import com.sxxy.service.impl.LoginServiceImpl;
import com.sxxy.service.impl.WorkServiceImpl;

public class LoginCheckServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginCheckServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String userNum=	request.getParameter("userNum");
		String userPw= request.getParameter("userPw");
		
		LoginService service=new LoginServiceImpl();
		UserInfo userInfo=new UserInfo();
		
		 userInfo.setUserNum(userNum);

		 List<UserInfo> list =service.query(userInfo);
		 list.add(userInfo);
		 		 
		 UserInfo  userInfo2 =service.getAllList(userNum);
		
		  request.getSession().setAttribute("userInfo2", userInfo2);
		  
		  
		  
		
		if(userPw.equals(list.get(0).getUserPw())){
			request.getRequestDispatcher("/view/frame/main.jsp").forward(request, response);
		}else {
			response.sendRedirect("../login.jsp?error=yes");
		}
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
