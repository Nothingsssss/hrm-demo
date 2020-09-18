package com.hrm.service.Impl;

import com.hrm.bean.Dept;
import com.hrm.bean.PageBean;
import com.hrm.dao.DeptDao;
import com.hrm.dao.impl.DeptDaoImpl;
import com.hrm.service.DeptDaoServise;

import java.util.List;

public class DeptDaoServiceImpl implements DeptDaoServise {
	
	DeptDao dd = new DeptDaoImpl();
	@Override
	public boolean addDept(Dept dept) {
		return dd.addDept(dept);
	}

	@Override
	public boolean deleteDept(int id) {
		return dd.deleteDept(id);
	}

	@Override
	public boolean updateDept(Dept dept) {
		return dd.updateDept(dept);
	}

	@Override
	public PageBean<Dept> findLike(int pageNow, Dept dept) {
		return dd.findLike(pageNow, dept);
	}

	@Override
	public Dept findById(int id) {
		return dd.findById(id);
	}

	@Override
	public List<Dept> findAll() {
		// TODO Auto-generated method stub
		return dd.findAll();
	}

}
