package com.hrm.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({ "/main.action", "/left.action", "/right.action", "/top.action" })
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取到请求路径
		String uri = request.getRequestURI();
		// 截取请求名
		String action = uri.substring(uri.lastIndexOf("/") + 1);
		if("main.action".equals(action)){
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
		}else if("left.action".equals(action)){
			request.getRequestDispatcher("/WEB-INF/jsp/left.jsp").forward(request, response);
		}else if("right.action".equals(action)){
			request.getRequestDispatcher("/WEB-INF/jsp/right.jsp").forward(request, response);
		}else if("top.action".equals(action)){
			request.getRequestDispatcher("/WEB-INF/jsp/top.jsp").forward(request, response);
		}
	}

}
