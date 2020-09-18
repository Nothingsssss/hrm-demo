package com.hrm.dao;

import com.hrm.bean.Dept;
import com.hrm.bean.PageBean;

import java.util.List;

public interface DeptDao {
	boolean addDept(Dept dept);
	boolean deleteDept(int id);
	boolean updateDept(Dept dept);
	PageBean<Dept> findLike(int pageNow, Dept dept);//ajax模糊分页查询
	Dept findById(int id);
	List<Dept> findAll();//查询所有信息
}
