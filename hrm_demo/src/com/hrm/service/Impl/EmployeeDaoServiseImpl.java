package com.hrm.service.Impl;

import com.hrm.bean.Employee;
import com.hrm.bean.PageBean;
import com.hrm.dao.EmployeeDao;
import com.hrm.dao.impl.EmployeeDaoimpl;
import com.hrm.service.EmployeeDaoServise;

import java.util.List;

public class EmployeeDaoServiseImpl implements EmployeeDaoServise{

	EmployeeDao ed = new EmployeeDaoimpl();
	@Override
	public boolean add(Employee e) {
		return ed.add(e);
	}

	@Override
	public boolean delete(int id) {
		return ed.delete(id);
	}

	@Override
	public boolean update(Employee e) {
		return ed.update(e);
	}

	@Override
	public PageBean<Employee> findLike(int pageNow, Employee e) {
		return ed.findLike(pageNow, e);
	}

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return ed.findAll();
	}

	@Override
	public Employee findById(int id) {
		// TODO Auto-generated method stub
		return ed.findById(id);
	}

	@Override
	public boolean findCard(String card) {
		// TODO Auto-generated method stub
		return ed.findCard(card);
	}

}
