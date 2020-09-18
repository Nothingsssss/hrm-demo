package com.hrm.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class NoticeServlet
 */
@WebServlet({"/selectNotice.action","/addNotice.action"})
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取到请求路径
		String uri = request.getRequestURI();
		// 截取请求名
		String action = uri.substring(uri.lastIndexOf("/") + 1);
		// 需要用到的数据
		if("selectNotice.action".equals(action)) {
			request.getRequestDispatcher("/WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
		}else if("addNotice.action".equals(action)) {
			request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
		}
	}

}
