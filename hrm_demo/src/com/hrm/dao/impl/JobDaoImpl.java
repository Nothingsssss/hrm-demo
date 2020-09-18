package com.hrm.dao.impl;

import com.hrm.bean.Job;
import com.hrm.bean.PageBean;
import com.hrm.dao.JobDao;
import com.hrm.util.Basedao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl extends Basedao<Job> implements JobDao {

	@Override
	public List<Job> findAll() {
		String sql = "select * from job";
		return query(sql);
	}
	
	@Override
	public boolean add(Job job) {
		String sql = "insert into job values(null,?,?)";
		List<Object> list = new ArrayList<>();
		list.add(job.getJname());
		list.add(job.getRemark());
		return update(sql, list);
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from job where id=?";
		List<Object> list = new ArrayList<>();
		list.add(id);
		return update(sql, list);
	}

	@Override
	public boolean update(Job job) {
		String sql = "update job set NAME=?,REMARK=? where id=?";
		List<Object> list = new ArrayList<>();
		list.add(job.getJname());
		list.add(job.getRemark());
		list.add(job.getJid());
		return update(sql, list);
	}

	@Override
	public PageBean<Job> findLike(int pageNow, Job job) {
		PageBean<Job> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql="select count(id) from job where 1=1 ";
		if(job.getJname() != null) {
			sql += "and name like ?";
			obj.add("%"+job.getJname().trim()+"%");
		}
		pb.setRowCount(queryCount(sql, obj.toArray()));
		
		sql = "select * from job where 1=1 ";
		if(job.getJname() != null) {
			sql += "and name like ?";
		}
		sql += "limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sql, obj.toArray()));
		return pb;
	}

	@Override
	public Job findById(int id) {
		String sql = "select * from job where id=?";
		List<Job> list = query(sql, id);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Job getEntity(ResultSet rs) throws Exception {
		Job job = new Job();
		job.setJid(rs.getInt(1));
		job.setJname(rs.getString(2));
		job.setRemark(rs.getString(3));
		return job;
	}

}
