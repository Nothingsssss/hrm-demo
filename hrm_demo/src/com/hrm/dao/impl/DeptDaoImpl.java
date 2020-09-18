package com.hrm.dao.impl;

import com.hrm.bean.Dept;
import com.hrm.bean.PageBean;
import com.hrm.dao.DeptDao;
import com.hrm.util.Basedao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeptDaoImpl extends Basedao<Dept> implements DeptDao {

	@Override
	public List<Dept> findAll() {
		String sql = "select * from dept";
		return query(sql);
	}
	
	@Override
	public boolean addDept(Dept dept) {
		String sql = "insert into dept values(null,?,?,?)";
		List<Object> list = new ArrayList<>();
		list.add(dept.getDname());
		list.add(dept.getRemark());
		list.add(dept.getState());;
		return update(sql, list);
	}

	@Override
	public boolean deleteDept(int id) {
		String sql = "delete from dept where id=?";
		List<Object> list = new ArrayList<>();
		list.add(id);
		return update(sql, list);
	}

	@Override
	public boolean updateDept(Dept dept) {
		String sql = "update dept set name=?,remark=?,state=? where id=?";
		List<Object> list = new ArrayList<>();
		list.add(dept.getDname());
		list.add(dept.getRemark());
		list.add(dept.getState());;
		list.add(dept.getDid());
		return update(sql, list);
	}

	@Override
	public PageBean<Dept> findLike(int pageNow, Dept dept) {
		PageBean<Dept> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql="select count(id) from dept where 1=1 ";
		if(dept.getDname() != null){
			sql += "and NAME like ? ";
			obj.add("%"+dept.getDname().trim()+"%");
		}
		pb.setRowCount(queryCount(sql, obj.toArray()));
		
		sql = "select * from dept where 1=1 ";
		if(dept.getDname() != null){
			sql += "and NAME like ? ";
		}
		sql += "limit ?,? ";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sql, obj.toArray()));
		return pb;
	}

	@Override
	public Dept getEntity(ResultSet rs) throws Exception {
		Dept d = new Dept();
		d.setDid(rs.getInt(1));
		d.setDname(rs.getString(2));
		d.setRemark(rs.getString(3));
		d.setState(rs.getInt(4));
		return d;
	}

	@Override
	public Dept findById(int id) {
		String sql = "select * from dept where id=?";
		List<Dept> list = query(sql, id);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
