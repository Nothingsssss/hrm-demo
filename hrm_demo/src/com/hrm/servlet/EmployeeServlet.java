package com.hrm.servlet;

import com.hrm.bean.Dept;
import com.hrm.bean.Employee;
import com.hrm.bean.Job;
import com.hrm.bean.PageBean;
import com.hrm.service.DeptDaoServise;
import com.hrm.service.EmployeeDaoServise;
import com.hrm.service.Impl.DeptDaoServiceImpl;
import com.hrm.service.Impl.EmployeeDaoServiseImpl;
import com.hrm.service.Impl.JobDaoServiseImpl;
import com.hrm.service.JobDaoServise;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@WebServlet({"/employeelist.action","/employeeadd.action","/employeequery.action","/updateEmployee.action","/saveEmployee.action","/getcardid.action","/addEmployee.action","/employeedel.action"})
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    EmployeeDaoServise eds = new EmployeeDaoServiseImpl();  
    DeptDaoServise dds = new DeptDaoServiceImpl();
    JobDaoServise jds = new JobDaoServiseImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取到请求路径
		String uri = request.getRequestURI();
		// 截取请求名
		String action = uri.substring(uri.lastIndexOf("/") + 1);
		// 需要用到的数据
		Employee e = new Employee();
		List<Dept> deptList = dds.findAll();
		List<Job> jobList = jds.findAll();
		PageBean<Employee> pb = null;
		int pageNow = 1;
		int check = 1;
		if("employeelist.action".equals(action)) {
			//员工展示界面
			if(request.getParameter("pageNow") !=null) {
				pageNow = Integer.parseInt(request.getParameter("pageNow"));
			}
			pb = eds.findLike(pageNow, e);
			request.setAttribute("pb", pb);
			request.setAttribute("check", check);
			request.setAttribute("deptList", deptList);
			request.setAttribute("jobList", jobList);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
		}else if("employeequery.action".equals(action)) {
			//员工查询界面
			if(request.getParameter("job_id")!="") {
				int job_id = Integer.parseInt(request.getParameter("job_id"));
				Job job = new Job();
				job.setJid(job_id);
				e.setJ_id(job);
			}
			if(request.getParameter("name")!="") {
				e.setName(request.getParameter("name"));
			}
			if(request.getParameter("cardId")!="") {
				e.setCard_id(request.getParameter("cardId"));
			}
			if(request.getParameter("sex")!="") {
				e.setSex(Integer.parseInt(request.getParameter("sex")));
			}
			if(request.getParameter("phone")!="") {
				e.setPhone(request.getParameter("phone"));
			}
			if(request.getParameter("d_id")!="") {
				int d_id = Integer.parseInt(request.getParameter("d_id"));
				Dept dept = new Dept();
				dept.setDid(d_id);
				e.setD_id(dept);
			}
			if(request.getParameter("pageNow") !=null) {
				pageNow = Integer.parseInt(request.getParameter("pageNow"));
			}
			check = 2;
			pb = eds.findLike(pageNow, e);
			request.setAttribute("deptList", deptList);
			request.setAttribute("jobList", jobList);
			request.setAttribute("check", check);
			request.setAttribute("pb", pb);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
			
		}else if("updateEmployee.action".equals(action)) {
			//员工修改界面
			int eid = Integer.parseInt(request.getParameter("eid"));
			Employee employee = eds.findById(eid);
			request.setAttribute("deptList", deptList);
			request.setAttribute("jobList", jobList);
			request.setAttribute("employee", employee);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
		}else if("saveEmployee.action".equals(action)) {
			//员工修改业务处理
			e.setEid(Integer.parseInt(request.getParameter("eid")));
			Dept dept = new Dept();
			dept.setDid(Integer.parseInt(request.getParameter("dept_id")));
			e.setD_id(dept);
			Job job = new Job();
			job.setJid(Integer.parseInt(request.getParameter("job_id")));
			e.setJ_id(job);
			e.setName(request.getParameter("name"));
			e.setCard_id(request.getParameter("cardId"));
			e.setAddress(request.getParameter("address"));
			e.setPost_code(request.getParameter("postCode"));
			e.setTel(request.getParameter("tel"));
			e.setPhone(request.getParameter("phone"));
			e.setQq_num(request.getParameter("qqNum"));
			e.setEmail(request.getParameter("email"));
			e.setSex(Integer.parseInt(request.getParameter("sex")));
			e.setParty(request.getParameter("party"));
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			try {
				e.setBirthday(date.parse(request.getParameter("birthday")));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.setRace(request.getParameter("race"));
			e.setEducate(request.getParameter("education"));
			e.setSpeciality(request.getParameter("speciality"));
			e.setHobby(request.getParameter("hobby"));
			e.setRemark(request.getParameter("remark"));
			if(eds.update(e)) {
				request.setAttribute("message", "修改成功！");
				request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
			}
		}else if("employeeadd.action".equals(action)) {
			request.setAttribute("deptList", deptList);
			request.setAttribute("jobList", jobList);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeadd.jsp").forward(request, response);
		}else if("getcardid.action".equals(action)) {
			//Ajax验证身份证是否重复
			PrintWriter out = response.getWriter();
			String str = getJsonData(request);
			String cardId = str.substring(11, str.length()-2);
			if(eds.findCard(cardId)) {
				out.print(false);
			}else {
				out.print(true);
			}
			out.close();
		}else if("addEmployee.action".equals(action)) {
			//添加员工
			String cardId = request.getParameter("cardId");
			e.setCard_id(cardId);
			e.setName(request.getParameter("name"));
			
			Job job = new Job();
			job.setJid(Integer.parseInt(request.getParameter("job_id")));
			e.setJ_id(job);
			
			Dept dept = new Dept();
			dept.setDid(Integer.parseInt(request.getParameter("dept_id")));
			e.setD_id(dept);
			
			e.setAddress(request.getParameter("address"));
			e.setPost_code(request.getParameter("postCode"));
			e.setTel(request.getParameter("tel"));
			e.setPhone(request.getParameter("phone"));
			e.setQq_num(request.getParameter("qqNum"));
			e.setEmail(request.getParameter("email"));
			e.setSex(Integer.parseInt(request.getParameter("sex")));
			e.setParty(request.getParameter("party"));
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			try {
				e.setBirthday(date.parse(request.getParameter("birthday")));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.setRace(request.getParameter("race"));
			e.setEducate(request.getParameter("education"));
			e.setSpeciality(request.getParameter("speciality"));
			e.setHobby(request.getParameter("hobby"));
			e.setRemark(request.getParameter("remark"));
			boolean f = eds.add(e);
			if(f) {
				request.setAttribute("message", "添加成功！");
				request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeadd.jsp").forward(request, response);
			}
		}else if("employeedel.action".equals(action)) {
			//删除员工
			Map<String, String[]>  uid = request.getParameterMap();
			String[] ids = uid.get("userIds");
			boolean flag = false;
			for(String id:ids) {
				flag = eds.delete(Integer.parseInt(id));
			}
			if(flag) {
				request.getRequestDispatcher("employeelist.action").forward(request, response);
			}
		}
				
	}
	
	//获取到post传过来的页面数据
	public String getJsonData(HttpServletRequest request) {
		 //获取请求正文
		 StringBuffer josnBuffer = new StringBuffer();
		 try {
			BufferedReader br = request.getReader();
			String josn = null;
			while ((josn = br.readLine())!=null) {
			josnBuffer.append(josn);
		 	}
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 
		 return josnBuffer.toString();
	 }

}
