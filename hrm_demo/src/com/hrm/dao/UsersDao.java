package com.hrm.dao;

import com.hrm.bean.PageBean;
import com.hrm.bean.Users;

import java.util.List;

public interface UsersDao {
	Users login(String loginname, String password);
	boolean addUser(Users user);
	boolean delete(int id);
	boolean update(Users user);
	PageBean<Users> findLike(int pageNow, Users user);//模糊分页查询
	Users findById(int id);
	List<Users> findAll();//查询所有信息
}
