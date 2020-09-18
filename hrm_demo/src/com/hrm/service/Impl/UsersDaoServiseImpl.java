package com.hrm.service.Impl;

import com.hrm.bean.PageBean;
import com.hrm.bean.Users;
import com.hrm.dao.UsersDao;
import com.hrm.dao.impl.UsersDaoImpl;
import com.hrm.service.UsersDaoServise;

import java.util.List;

public class UsersDaoServiseImpl implements UsersDaoServise {
	
	UsersDao ud = new UsersDaoImpl();
	@Override
	public Users login(String loginname, String password) {
		return ud.login(loginname, password);
	}

	@Override
	public boolean addUser(Users user) {
		return ud.addUser(user);
	}

	@Override
	public boolean delete(int id) {
		return ud.delete(id);
	}

	@Override
	public boolean update(Users user) {
		return ud.update(user);
	}

	@Override
	public PageBean<Users> findLike(int pageNow, Users user) {
		return ud.findLike(pageNow, user);
	}

	@Override
	public Users findById(int id) {
		return ud.findById(id);
	}

	@Override
	public List<Users> findAll() {
		return ud.findAll();
	}

}
