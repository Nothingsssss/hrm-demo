package com.hrm.dao.impl;

import com.hrm.bean.Document;
import com.hrm.bean.PageBean;
import com.hrm.dao.DocumentDao;
import com.hrm.dao.UsersDao;
import com.hrm.util.Basedao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DocumentDaoImpl extends Basedao<Document> implements DocumentDao {
	UsersDao ud = new UsersDaoImpl();
	
	@Override
	public List<Document> findAll() {
		String sql = "select * from document";
		return query(sql);
	}
	
	@Override
	public PageBean<Document> findLike(int pageNow, Document d) {
		PageBean<Document> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql="select count(id) from document where 1=1 ";
		if(d.getTitle() != null) {
			sql+="and title like ?";
			obj.add("%"+d.getTitle().trim()+"%");
		}
		pb.setRowCount(queryCount(sql, obj.toArray()));
		
		sql = "select * from document where 1=1 ";
		if(d.getTitle() != null) {
			sql+="and title like ?";
		}
		sql += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sql, obj.toArray()));
		return pb;
	}

	@Override
	public Document getEntity(ResultSet rs) throws Exception {
		Document d = new Document();
		d.setId(rs.getInt(1));
		d.setTitle(rs.getString(2));
		d.setFilename(rs.getString(3));
		d.setFiletype(rs.getString(4));
		d.setFilebytes(rs.getString(5));
		d.setUrl(rs.getString(6));
		d.setRemark(rs.getString(7));
		d.setCreatedate(rs.getDate(8));
		d.setUser_id(ud.findById(rs.getInt(9)));
		return d;
	}

	@Override
	public boolean add(Document d) {
		String sql = "insert into document values(null,?,?,?,?,?,?,?,?)";
		List<Object> list = new ArrayList<>();
		list.add(d.getTitle());
		list.add(d.getFilename());
		list.add(d.getFiletype());
		list.add(d.getFilebytes());
		list.add(d.getUrl());
		list.add(d.getRemark());
		list.add(d.getCreatedate());
		list.add(d.getUser_id().getUid());
		return update(sql, list);
	}

	@Override
	public Document findById(int id) {
		String sql = "select * from document where id=?";
		List<Document> list = query(sql, id);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from document where id=?";
		List<Object> list = new ArrayList<>();
		list.add(id);
		return update(sql, list);
	}

	@Override
	public boolean update(Document d) {
		String sql = "update document set TITLE=?,filename=?,filetype=?,filebytes=?,url=?,REMARK=?,CREATE_DATE=?,USER_ID=? where id=?";
		List<Object> list = new ArrayList<>();
		list.add(d.getTitle());
		list.add(d.getFilename());
		list.add(d.getFiletype());
		list.add(d.getFilebytes());
		list.add(d.getUrl());
		list.add(d.getRemark());
		list.add(d.getCreatedate());
		list.add(d.getUser_id().getUid());
		list.add(d.getId());
		return update(sql, list);
	}

}
