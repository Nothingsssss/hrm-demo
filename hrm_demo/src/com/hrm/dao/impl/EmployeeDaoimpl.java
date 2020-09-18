package com.hrm.dao.impl;

import com.hrm.bean.Employee;
import com.hrm.bean.PageBean;
import com.hrm.dao.DeptDao;
import com.hrm.dao.EmployeeDao;
import com.hrm.dao.JobDao;
import com.hrm.util.Basedao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoimpl extends Basedao<Employee> implements EmployeeDao {
	
	DeptDao dd = new DeptDaoImpl();
	JobDao jd = new JobDaoImpl();
	
	@Override
	public List<Employee> findAll() {
		String sql = "select * from employee";
		return query(sql);
	}
	
	@Override
	public boolean add(Employee e) {
		String sql = "insert into employee values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List<Object> list = new ArrayList<>();
		list.add(e.getName());
		list.add(e.getCard_id());
		list.add(e.getAddress());
		list.add(e.getPost_code());
		list.add(e.getTel());
		list.add(e.getPhone());
		list.add(e.getQq_num());
		list.add(e.getEmail());
		list.add(e.getSex());
		list.add(e.getParty());
		list.add(e.getBirthday());
		list.add(e.getRace());
		list.add(e.getEducate());
		list.add(e.getSpeciality());
		list.add(e.getHobby());
		list.add(e.getRemark());
		list.add(e.getCreate_date());
		list.add(e.getD_id().getDid());
		list.add(e.getJ_id().getJid());
		return update(sql, list);
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from employee where id=?";
		List<Object> list = new ArrayList<>();
		list.add(id);
		return update(sql, list);
	}

	@Override
	public boolean update(Employee e) {
		String sql = "update employee set NAME=?,CARD_ID=?,"
				+ "ADDRESS=?,POST_CODE=?,TEL=?,"
				+ "PHONE=?,QQ_NUM=?,EMAIL=?,SEX=?,"
				+ "PARTY=?,BIRTHDAY=?,RACE=?,EDUCATION=?,SPECIALITY=?,"
				+ "HOBBY=?,REMARK=?,CREATE_DATE=?,DEPT_ID=?,JOB_ID=? where id=?";
		List<Object> list = new ArrayList<>();
		list.add(e.getName());
		list.add(e.getCard_id());
		list.add(e.getAddress());
		list.add(e.getPost_code());
		list.add(e.getTel());
		list.add(e.getPhone());
		list.add(e.getQq_num());
		list.add(e.getEmail());
		list.add(e.getSex());
		list.add(e.getParty());
		list.add(e.getBirthday());
		list.add(e.getRace());
		list.add(e.getEducate());
		list.add(e.getSpeciality());
		list.add(e.getHobby());
		list.add(e.getRemark());
		list.add(e.getCreate_date());
		list.add(e.getD_id().getDid());
		list.add(e.getJ_id().getJid());
		list.add(e.getEid());
		return update(sql, list);
	}

	@Override
	public PageBean<Employee> findLike(int pageNow, Employee e) {
		PageBean<Employee> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql="select count(id) from employee where 1=1 ";
		if(e.getJ_id() != null){
			sql += "and JOB_ID=? ";
			obj.add(e.getJ_id().getJid());
		}
		if(e.getSex() > 0){
			sql += "and SEX=? ";
			obj.add(e.getSex());
		}
		if(e.getName() != null){
			sql += "and name like ? ";
			obj.add("%"+e.getName().trim()+"%");
		}
		if(e.getPhone() != null){
			sql += "and PHONE like ? ";
			obj.add("%"+e.getPhone().trim()+"%" );
		}
		if(e.getCard_id() != null){
			sql += "and CARD_ID like ? ";
			obj.add("%"+e.getCard_id().trim()+"%");
		}
		if(e.getD_id() != null){
			sql += "and DEPT_ID=?";
			obj.add(e.getD_id().getDid());
		}
		pb.setRowCount(queryCount(sql, obj.toArray()));
		
		sql = "select * from employee where 1=1 ";
		if(e.getJ_id() != null){
			sql += "and JOB_ID=? ";
		}
		if(e.getSex() > 0){
			sql += "and SEX=? ";
		}
		if(e.getName() != null){
			sql += "and name like ? ";
		}
		if(e.getPhone() != null){
			sql += "and PHONE like ? ";
		}
		if(e.getCard_id() != null){
			sql += "and CARD_ID like ? ";
		}
		if(e.getD_id() != null){
			sql += "and DEPT_ID=? ";
		}
		sql += "limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sql, obj.toArray()));
		return pb;
	}

	@Override
	public Employee getEntity(ResultSet rs) throws Exception {
		Employee e = new Employee();
		e.setEid(rs.getInt(1));
		e.setName(rs.getString(2));
		e.setCard_id(rs.getString(3));
		e.setAddress(rs.getString(4));
		e.setPost_code(rs.getString(5));
		e.setTel(rs.getString(6));
		e.setPhone(rs.getString(7));
		e.setQq_num(rs.getString(8));
		e.setEmail(rs.getString(9));
		e.setSex(rs.getInt(10));
		e.setParty(rs.getString(11));
		e.setBirthday(rs.getDate(12));
		e.setRace(rs.getString(13));
		e.setEducate(rs.getString(14));
		e.setSpeciality(rs.getString(15));
		e.setHobby(rs.getString(16));
		e.setRemark(rs.getString(17));
		e.setCreate_date(rs.getDate(18));
		e.setD_id(dd.findById(rs.getInt(19)));
		e.setJ_id(jd.findById(rs.getInt(20)));
		return e;
	}

	@Override
	public Employee findById(int id) {
		String sql = "select * from employee where id=?";
		List<Employee> list = query(sql, id);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean findCard(String card) {
		String sql = "select * from employee where CARD_ID=?";
		List<Employee> list =  query(sql, card);
		if(!list.isEmpty()) {
			return true;
		}
		return false;
	}

}
