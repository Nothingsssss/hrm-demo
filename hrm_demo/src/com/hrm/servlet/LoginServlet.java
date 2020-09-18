package com.hrm.servlet;

import com.hrm.bean.Users;
import com.hrm.service.Impl.UsersDaoServiseImpl;
import com.hrm.service.UsersDaoServise;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({ "/loginForm.action", "/login.action" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UsersDaoServise uds = new UsersDaoServiseImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取到请求路径
		String uri = request.getRequestURI();
		//截取请求名
		String action = uri.substring(uri.lastIndexOf("/")+1);
		if(action.equals("login.action")){
			//获取用户的操作
			String loginname = request.getParameter("loginname");
			String password = request.getParameter("password");
			Users user = uds.login(loginname, password);
			if(user!=null){
				HttpSession session = request.getSession();
				session.setAttribute("user_session", user);
				//跳转到主界面
				request.getRequestDispatcher("/main.action").forward(request, response);
			}else{
				request.setAttribute("message", "用户名或密码错误");
				request.getRequestDispatcher("/loginForm.action").forward(request, response);
			}
		}else{
			//请求登录界面
			request.getRequestDispatcher("/WEB-INF/jsp/loginForm.jsp").forward(request, response);
		}
	}

}
