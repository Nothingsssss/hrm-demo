package com.hrm.servlet;

import com.hrm.bean.Document;
import com.hrm.bean.PageBean;
import com.hrm.service.DocumentDaoServise;
import com.hrm.service.Impl.DocumentDaoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@WebServlet({"/documentlist.action","/documentadd.action","/updateDocument.action","/documentquery.action","/DownDocument.action","/removeDocument.action"})
public class DocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DocumentDaoServise dds = new DocumentDaoServiceImpl();


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取到请求路径
		String uri = request.getRequestURI();
		// 截取请求名
		String action = uri.substring(uri.lastIndexOf("/") + 1);
		// 需要用到的数据
		Document d = new Document();
		PageBean<Document> pb = null;
		int pageNow = 1;
		int check = 1;
		if("documentlist.action".equals(action)) {
			//展示界面
			if(request.getParameter("pageNow") !=null) {
				pageNow = Integer.parseInt(request.getParameter("pageNow"));
			}
			pb = dds.findLike(pageNow, d);
			request.setAttribute("pb", pb);
			request.setAttribute("check", check);
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
		}else if("documentadd.action".equals(action)) {
			//上传界面
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
		}else if ("updateDocument.action".equals(action)) {
			//修改界面
			int id = Integer.parseInt(request.getParameter("id"));
			Document document = dds.findById(id);
			request.setAttribute("document", document);
			request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
		}else if ("documentquery.action".equals(action)) {
			//分页查询界面
			String title = request.getParameter("title");
			if(title!="") {
				d.setTitle(title);
			}
			if(request.getParameter("pageNow") !=null) {
				pageNow = Integer.parseInt(request.getParameter("pageNow"));
			}
			check = 2;
			pb = dds.findLike(pageNow, d);
			request.setAttribute("pb", pb);
			request.setAttribute("check", check);
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
			
		}else if("DownDocument.action".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id"));
			d = dds.findById(id);
			String name = d.getFilename();
			String type = d.getFiletype();
			String url =  d.getUrl();
			String fileName = name+"."+type;
			//文件输入流读取文件内容
			System.out.println(url+fileName);
			InputStream in = new FileInputStream(url+fileName);
			//设置浏览器下载模式
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			//创建一个输出流
			ServletOutputStream out = response.getOutputStream();
			//从输出流中将内容输出到客户端
//			IOUtils.copy(in, out);
			int len = 0;
			byte[] by = new byte[1024];
			while((len=in.read(by))>0){
				out.write(by, 0, len);
			}
			in.close();
			out.flush();
			out.close();
			
			
		}else if("removeDocument.action".equals(action)) {
			//删除文件
			String ids = request.getParameter("ids");
			String[] strs = ids.split(",");
			boolean flag = false;
			for (String id : strs) {
				flag = dds.delete(Integer.parseInt(id));
			}
			if(flag) {
				request.getRequestDispatcher("/documentlist.action").forward(request, response);
			}
		}
	}

}
