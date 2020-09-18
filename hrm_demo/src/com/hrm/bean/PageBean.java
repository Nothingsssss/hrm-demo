package com.hrm.bean;

import java.util.List;

public class PageBean<T> {
	private int rowCount; // 总数据条数
	private int pageSize = 4; // 页面大小
	private int pageNow; // 当前页面号
	private int pageCount; // 总页数
	private List<T> list; // 封装页面显示数据

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		setPageCount(); //当总行数受到影响,重新计算总页数
	}

	public int getPageSize() {
		return pageSize;
	}

//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//		setPageCount();  //当每页大小受到影响时,重新计算
//	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getPageCount() {
		return pageCount;
	}

	//总页数不是设置的,而是通过计算出来的,但是当有余数时,需要增加一页存储剩余数据
	public void setPageCount() {
		this.pageCount = this.rowCount % this.pageSize == 0 ? this.rowCount / this.pageSize
				: this.rowCount / this.pageSize + 1;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
