package com.hrm.dao;

import com.hrm.bean.Employee;
import com.hrm.bean.PageBean;

import java.util.List;

public interface EmployeeDao {
	boolean add(Employee e);
	boolean delete(int id);
	boolean update(Employee e);
	PageBean<Employee> findLike(int pageNow, Employee e);
	List<Employee> findAll();//查询所有信息
	Employee findById(int id);
	boolean findCard(String card);

}
