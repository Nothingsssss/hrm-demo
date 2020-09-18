package com.hrm.dao.impl;

import com.hrm.bean.PageBean;
import com.hrm.bean.Users;
import com.hrm.dao.UsersDao;
import com.hrm.util.Basedao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsersDaoImpl extends Basedao<Users> implements UsersDao{

	@Override
	public Users login(String loginname, String password) {
		String sql = "select * from user where loginname=? and password=?";
		List<Users> list = query(sql, loginname,password);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean addUser(Users user) {
		String sql = "insert into user values(null,?,?,?,?,?)";
		List<Object> list = new ArrayList<>();
		list.add(user.getLoginname());
		list.add(user.getPassword());
		list.add(user.getStatus());
		list.add(user.getCreatedate());
		list.add(user.getUsername());
		return update(sql, list);
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from user where id=?";
		List<Object> list = new ArrayList<>();
		list.add(id);
		return update(sql, list);
	}

	@Override
	public boolean update(Users user) {
		String sql = "update user set loginname=?,password=?,status=?,createdate=?,username=? where id=?";
		List<Object> list = new ArrayList<>();
		list.add(user.getLoginname());
		list.add(user.getPassword());
		list.add(user.getStatus());
		list.add(user.getCreatedate());
		list.add(user.getUsername());
		list.add(user.getUid());
		return update(sql, list);
		
	}

	@Override
	public PageBean<Users> findLike(int pageNow, Users user) {
		PageBean<Users> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql="select count(id) from user where 1=1 ";
		if(user.getUsername() != null){
			sql += "and username like ? ";
			obj.add("%"+user.getUsername().trim()+"%");
		}
		if(user.getStatus() > 0){
			sql += "and status = ?";
			obj.add(user.getStatus());
		}
		if(user.getLoginname() !=null) {
			sql+="and loginname like ?";
			obj.add("%"+user.getLoginname().trim()+"%");
		}
		pb.setRowCount(queryCount(sql, obj.toArray()));
		
		sql = "select * from user where 1=1 ";
		if(user.getUsername() != null){
			sql += "and username like ? ";
			
		}
		if(user.getStatus() > 0){
			sql += "and status=? ";
		
		}
		if(user.getLoginname() !=null) {
			sql+="and loginname like ?";
			
		}
		sql += "limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sql, obj.toArray()));
		return pb;
	}

	@Override
	public Users getEntity(ResultSet rs) throws Exception {
		Users users = new Users();
		users.setUid(rs.getInt(1));
		users.setLoginname(rs.getString(2));
		users.setPassword(rs.getString(3));
		users.setStatus(rs.getInt(4));
		users.setCreatedate(rs.getDate(5));
		users.setUsername(rs.getString(6));
		return users;
	}

	@Override
	public Users findById(int id) {
		String sql = "select * from user where id=?";
		List<Users> list = query(sql, id);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Users> findAll() {
		String sql = "select * from user";
		return query(sql);
	}

}
