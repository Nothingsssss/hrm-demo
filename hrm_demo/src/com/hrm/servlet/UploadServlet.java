package com.hrm.servlet;

import com.hrm.bean.Document;
import com.hrm.bean.Users;
import com.hrm.service.DocumentDaoServise;
import com.hrm.service.Impl.DocumentDaoServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet({"/documentaddsave.action"})
public class UploadServlet extends HttpServlet {
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
		if ("documentaddsave.action".equals(action)) {
			//上传操作
			//获取到上传组件对象,并取得是否为二进制提交方式
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart) {
				//创建一个FileItemFactory工厂对象
				FileItemFactory factory = new DiskFileItemFactory();
				//通过ServletFileUpload,去构建工厂用于保存数据
				ServletFileUpload upload = new ServletFileUpload(factory);
				//判断注册数据接收是否成功
				boolean flag = false;
				try {
					List<FileItem> list = upload.parseRequest(request);
					if(list!=null) {
						//循环集合,将每一项内容获取出来
						for (FileItem fi : list) {
							//判断每一项数据中是否为普通数据   isformField判断  true表示普通数据
							if(fi.isFormField()) {
								if("title".equals(fi.getFieldName())){
									d.setTitle(fi.getString("utf-8"));
								}
								if("remark".equals(fi.getFieldName())){
									d.setRemark(fi.getString("utf-8"));
								}
							}else {
								//获取上传的文件
								//获取到上传文件需要存放的真实路径
								String path = request.getServletContext().getRealPath("/upload");
								System.out.println(path);
								File file = new File(path);
								//判断路径是否存在,不存在则创建
								if(!file.exists()){
									file.mkdirs();
								}
								//获取到文件名
								String fileName = fi.getName();
								int index = fileName.lastIndexOf(".");
								d.setFilename(fileName.substring(0, index));
								//文件类型
								String type = fileName.substring(index+1);
								d.setFiletype(type);
								if(!"txt".equals(type) && !"jpg".equals(type)&& !"png".equals(type)) {
									request.setAttribute("message", "文件类型不正确！");
									request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
									return;
								}
								//文件大小
								long size = file.length();
								String filebytes = (size/1024)+"KB";
								d.setFilebytes(filebytes);
								d.setUrl(path);
								HttpSession session = request.getSession(false);
								Users user = (Users) session.getAttribute("user_session");
								if(user == null) {
									response.sendRedirect("index.jsp");
								}
								d.setUser_id(user);
								//在upload文件夹下创建一个新的文件
								fileName = fileName.substring(0, index)+fileName.substring(index);
								File newFile = new File(file, fileName);
								fi.write(newFile);
							}
						}
						flag = true;
					}else{
						HttpSession session = request.getSession();
						session.setAttribute("message", "数据传输异常");
					}
					if (flag) {
						// 将数据存入数据库
						if (dds.add(d)) {
							request.setAttribute("message", "上传成功！");
							request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request,
									response);
							}
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

}
