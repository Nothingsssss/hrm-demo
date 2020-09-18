package com.hrm.service.Impl;

import com.hrm.bean.Document;
import com.hrm.bean.PageBean;
import com.hrm.dao.DocumentDao;
import com.hrm.dao.impl.DocumentDaoImpl;
import com.hrm.service.DocumentDaoServise;

import java.util.List;

public class DocumentDaoServiceImpl implements DocumentDaoServise{

	DocumentDao dd = new DocumentDaoImpl();
	@Override
	public PageBean<Document> findLike(int pageNow, Document d) {
		return dd.findLike(pageNow, d);
	}

	@Override
	public boolean add(Document d) {
		return dd.add(d);
	}

	@Override
	public List<Document> findAll() {
		// TODO Auto-generated method stub
		return dd.findAll();
	}

	@Override
	public Document findById(int id) {
		// TODO Auto-generated method stub
		return dd.findById(id);
	}

	@Override
	public boolean delete(int id) {
		return dd.delete(id);
	}

	@Override
	public boolean update(Document d) {
		return dd.update(d);
	}

}
