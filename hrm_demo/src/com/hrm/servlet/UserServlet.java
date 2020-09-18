package com.hrm.servlet;

import com.hrm.bean.PageBean;
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
import java.io.PrintWriter;
import java.util.Map;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet({"/userlist.action","/viewUser.action","/useradd.action","/useraddsave.action","/queryUser.action","/useredit.action","/userdel.action"})
public class UserServlet extends HttpServlet {
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
		//需要用到的数据
		PrintWriter out = response.getWriter();
		Users user = new Users();
		int pageNow = 1;
		int check = 1;
		PageBean<Users> pb = null;
		if("userlist.action".equals(action)) {
			//展示界面
			if(request.getParameter("pageNow") !=null) {
				pageNow = Integer.parseInt(request.getParameter("pageNow"));
			}
			pb = uds.findLike(pageNow,user);
			request.setAttribute("message", request.getAttribute("message"));
			request.setAttribute("pb", pb);
			request.setAttribute("check", check);
			request.getRequestDispatcher("/WEB-INF/jsp/user/userlist.jsp").forward(request, response);
		}else if("viewUser.action".equals(action)) {
			//用户编辑页面
			HttpSession session = request .getSession(false);
			Users user1 = (Users) session.getAttribute("user_session");
			if(!"超级管理员".equals(user1.getUsername())) {
				request.setAttribute("message", "未拥有权限！");
				request.getRequestDispatcher("userlist.action").forward(request, response);
				return;
			}
			int uid = Integer.parseInt(request.getParameter("uid"));
			user = uds.findById(uid);
			request.setAttribute("uid", uid);
			request.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp").forward(request, response);
		}else if("useradd.action".equals(action)) {
			//新增用户页面
			request.getRequestDispatcher("/WEB-INF/jsp/user/useradd.jsp").forward(request, response);
		}else if("useraddsave.action".equals(action)) {
			//用户添加页面
			user.setUsername(request.getParameter("username"));
			user.setStatus(Integer.parseInt(request.getParameter("status")));
			user.setLoginname(request.getParameter("loginname"));
			user.setPassword(request.getParameter("password"));
			
			if(uds.addUser(user)) {
				request.setAttribute("message", "添加成功！");
				request.getRequestDispatcher("/WEB-INF/jsp/user/useradd.jsp").forward(request, response);
			}
		}else if("queryUser.action".equals(action)) {
			//分页查询页面
			if(request.getParameter("loginname")!="") {
				String loginname = request.getParameter("loginname");
				user.setLoginname(loginname);
			}
			if(request.getParameter("username")!="") {
				String username = request.getParameter("username");
				user.setUsername(username);
			}
			if(request.getParameter("status")!="") {
				user.setStatus(Integer.parseInt(request.getParameter("status")));
			}
			if(request.getParameter("pageNow") !=null) {
				pageNow = Integer.parseInt(request.getParameter("pageNow"));
//				System.out.println(pageNow);
			}
			check = 2;
			pb = uds.findLike(pageNow,user);
//			System.out.println(pb);
			request.setAttribute("pb", pb);
			request.setAttribute("check", check);
			request.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/jsp/user/userlist.jsp").forward(request, response);
		}else if("useredit.action".equals(action)) {
			//修改界面
			user.setUid(Integer.parseInt(request.getParameter("uid")));
			user.setUsername(request.getParameter("username"));
			user.setStatus(Integer.parseInt(request.getParameter("status")));
			user.setLoginname(request.getParameter("loginname"));
			user.setPassword(request.getParameter("password"));
			if(uds.update(user)) {
				request.setAttribute("", "修改成功！");
				request.getRequestDispatcher("userlist.action").forward(request, response);
			}
		}else if("userdel.action".equals(action)) {
			//删除员工
			HttpSession session = request .getSession(false);
			Users user1 = (Users) session.getAttribute("user_session");
			if(!"超级管理员".equals(user1.getUsername())) {
				request.setAttribute("message", "未拥有权限！");
				request.getRequestDispatcher("userlist.action").forward(request, response);
				return;
			}
			Map<String, String[]>  uid = request.getParameterMap();
			String[] ids = uid.get("userIds");
			boolean flag = false;
			for(String id:ids) {
				flag = uds.delete(Integer.parseInt(id));
			}
			if(flag) {
				request.getRequestDispatcher("userlist.action").forward(request, response);
			}
			
		}
		out.close();
	}

}
