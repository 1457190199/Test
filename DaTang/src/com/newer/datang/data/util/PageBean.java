package com.newer.datang.data.util;

import java.util.List;

public class PageBean<T> {
	List<T> data = null;
	int totalPages;//总页数
	int pageNo;//页号（第几页）
	int pageSize;//每一页的大小（每一页的记录总数）
	
	int totalRecords;//总记录数
	
	
	public PageBean(List<T> data, int totalPages, int pageNo, int pageSize,
			int totalRecords) {
		super();
		this.data = data;
		this.totalPages = totalPages;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	@Override
	public String toString() {
		return "PageBean [data=" + data + ", totalPages=" + totalPages
				+ ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}
	public PageBean(List<T> data, int totalPages, int pageNo, int pageSize) {
		super();
		this.data = data;
		this.totalPages = totalPages;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	//总页数
	public int getTotalPages() {
		return (this.totalRecords +this.pageSize - 1)/this.pageSize;
	}
	
	//${pb.firstPage}
	//第一页
	public int getFirstPage() {
		return 1;
	}
	
	//最后一页
	public int getLastPage() {
		if (this.totalRecords == 0) {
			//没有记录，最后一页即第一页
			return 1;
		}
		//调用getTotalPages()返回页数
		return this.getTotalPages();
	}
	
	//前一页
	public int getPreviousPage() {
		if (this.pageNo == 1) {
			//本身现在就是第一页
			return 1;
		}
		return this.pageNo - 1;
	}
	
	//后一页
	public int getNextPage() {
		if (this.pageNo == this.getLastPage()) {
			//如果已经是最后一页：
			return this.pageNo;
		}
		return this.pageNo + 1;
	}
}










