package com.hrm.servlet;

import com.hrm.bean.Dept;
import com.hrm.bean.PageBean;
import com.hrm.service.DeptDaoServise;
import com.hrm.service.Impl.DeptDaoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet({"/deptlist.action","/addDept.action","/queryDept.action","/addDeptview.action","/deptdel.action","/viewDept.action","/saveOrUpdate.action"})
public class DeptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DeptDaoServise dds = new DeptDaoServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取到请求路径
		String uri = request.getRequestURI();
		//截取请求名
		String action = uri.substring(uri.lastIndexOf("/")+1);
		//需要用到的数据
		Dept dept = new Dept();
		PageBean<Dept> pb = null;
		int pageNow = 1;
		int check = 1;
		if("deptlist.action".equals(action)) {
			//部门展示界面
			String pagen = request.getParameter("pageNow");
			if(pagen != null) {
				pageNow = Integer.parseInt(pagen);
			}
			pb = dds.findLike(pageNow, dept);
			request.setAttribute("pb", pb);
			request.setAttribute("check", check);
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
		}else if("queryDept.action".equals(action)) {
			//部门分页查询界面
			String name = request.getParameter("name");
			if( name != "") {
				dept.setDname(name);
			}
			if(request.getParameter("pageNow") !=null) {
				pageNow = Integer.parseInt(request.getParameter("pageNow"));
				System.out.println("p:"+pageNow);
			}
			check = 2;
			pb = dds.findLike(pageNow, dept);
			request.setAttribute("pb", pb);
			request.setAttribute("check", check);
			request.setAttribute("dept", dept);
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
		}else if("addDept.action".equals(action)) {
			//部门添加信息界面
			dept.setDname(request.getParameter("dname"));
			dept.setRemark(request.getParameter("remark"));
			dept.setState(0);
			if(dds.addDept(dept)) {
				request.setAttribute("message", "添加成功！");
				request.getRequestDispatcher("/WEB-INF/jsp/dept/deptadd.jsp").forward(request, response);
			}
		}else if("addDeptview.action".equals(action)) {
			//部门添加界面
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptadd.jsp").forward(request, response);
		}else if("deptdel.action".equals(action)){
			//部门删除界面
			Map<String, String[]>  did = request.getParameterMap();
			String[] dids = did.get("deptIds");
			boolean flag = false;
			for(String id:dids) {
				flag = dds.deleteDept(Integer.parseInt(id));
			}
			if(flag) {
				request.getRequestDispatcher("deptlist.action").forward(request, response);
			}
		}else if("viewDept.action".equals(action)) {
			int did = Integer.parseInt(request.getParameter("did"));
			dept = dds.findById(did);
			request.setAttribute("dept",dept);
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
		}else if("saveOrUpdate.action".equals(action)) {
			//部门修改
			dept.setDid(Integer.parseInt(request.getParameter("id")));
			dept.setDname(request.getParameter("name"));
			dept.setRemark(request.getParameter("remark"));
			if(dds.updateDept(dept)) {
				request.setAttribute("message", "修改成功！");
				request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
			}
		}

	}

}
