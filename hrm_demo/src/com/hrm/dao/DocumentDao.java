package com.hrm.dao;

import com.hrm.bean.Document;
import com.hrm.bean.PageBean;

import java.util.List;

public interface DocumentDao {
	boolean add(Document d);
	boolean delete(int id);
	boolean update(Document d);
	PageBean<Document> findLike(int pageNow, Document d);
	List<Document> findAll();//查询所有信息
	Document findById(int id);

}
