package com.hrm.servlet;

import com.hrm.bean.Job;
import com.hrm.bean.PageBean;
import com.hrm.service.Impl.JobDaoServiseImpl;
import com.hrm.service.JobDaoServise;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@WebServlet({"/joblist.action","/addJob.action","/queryjob.action","/viewJob.action","/jobedit.action","/jobaddsave.action","/jobdel.action"})
public class JobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    JobDaoServise jds = new JobDaoServiseImpl();


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取到请求路径
		String uri = request.getRequestURI();
		//截取请求名
		String action = uri.substring(uri.lastIndexOf("/")+1);
		//需要用到的数据
		Job  job= new Job();
		PageBean<Job> pb = null;
		int pageNow = 1;
		int check = 1;
		if("joblist.action".equals(action)) {
			//展示界面
			if(request.getParameter("pageNow") !=null) {
				pageNow = Integer.parseInt(request.getParameter("pageNow"));
			}
			pb = jds.findLike(pageNow, job);
			request.setAttribute("pb", pb);
			request.setAttribute("check", check);
//			System.out.println(pb);
			request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
		}else if("queryjob.action".equals(action)) {
			//查询界面
			String name = request.getParameter("name");
			if(name!="") {
				job.setJname(name);
			}
			if(request.getParameter("pageNow") !=null) {
				pageNow = Integer.parseInt(request.getParameter("pageNow"));
			}
			check = 2;
			pb = jds.findLike(pageNow, job);
			request.setAttribute("pb", pb);
			request.setAttribute("check", check);
			request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
		}else if("viewJob.action".equals(action)) {
			//修改界面
			int jid = Integer.parseInt(request.getParameter("jid"));
			job = jds.findById(jid);
			request.setAttribute("job", job);
			request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
		}else if("jobedit.action".equals(action)) {
			//修改业务处理
			job.setJid(Integer.parseInt(request.getParameter("jid")));
			job.setJname(request.getParameter("jname"));
			job.setRemark(request.getParameter("remark"));
			if(jds.update(job)) {
				request.setAttribute("message", "修改成功！");
				request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
			}
		}else if("addJob.action".equals(action)) {
			//添加界面
			request.getRequestDispatcher("/WEB-INF/jsp/job/jobadd.jsp").forward(request, response);
		}else if("jobaddsave.action".equals(action)) {
			//添加业务处理
			job.setJname(request.getParameter("jname"));
			job.setRemark(request.getParameter("remark"));
			if(jds.add(job)) {
				request.setAttribute("message", "添加成功！");
				request.getRequestDispatcher("/WEB-INF/jsp/job/jobadd.jsp").forward(request, response);
			}
		}else if("jobdel.action".equals(action)) {
			//删除员工业务处理
			Map<String, String[]>  jid = request.getParameterMap();
			String[] jids = jid.get("jobIds");
			boolean flag = false;
			for(String id:jids) {
				flag = jds.delete(Integer.parseInt(id));
			}
			if(flag) {
				request.getRequestDispatcher("joblist.action").forward(request, response);
			}
		}
	}

}
