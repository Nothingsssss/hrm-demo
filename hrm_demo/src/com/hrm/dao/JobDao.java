package com.hrm.dao;

import com.hrm.bean.Job;
import com.hrm.bean.PageBean;

import java.util.List;

public interface JobDao {
	boolean add(Job job);
	boolean delete(int id);
	boolean update(Job job);
	PageBean<Job> findLike(int pageNow, Job job);//ajax模糊分页查询
	Job findById(int id);
	List<Job> findAll();//查询所有信息

}
