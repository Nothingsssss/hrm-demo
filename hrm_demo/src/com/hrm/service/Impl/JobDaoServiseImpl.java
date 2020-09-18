package com.hrm.service.Impl;

import com.hrm.bean.Job;
import com.hrm.bean.PageBean;
import com.hrm.dao.JobDao;
import com.hrm.dao.impl.JobDaoImpl;
import com.hrm.service.JobDaoServise;

import java.util.List;

public class JobDaoServiseImpl implements JobDaoServise {

	JobDao jd = new JobDaoImpl();
	@Override
	public boolean add(Job job) {
		return jd.add(job);
	}

	@Override
	public boolean delete(int id) {
		return jd.delete(id);
	}

	@Override
	public boolean update(Job job) {
		return jd.update(job);
	}

	@Override
	public PageBean<Job> findLike(int pageNow, Job job) {
		return jd.findLike(pageNow, job);
	}

	@Override
	public Job findById(int id) {
		return jd.findById(id);
	}

	@Override
	public List<Job> findAll() {
		// TODO Auto-generated method stub
		return jd.findAll();
	}

}
