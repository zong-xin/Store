package cn.rain.store.product.domain;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("all")
public class PageBean<T> implements Serializable {

	/**
	 * pageNumber 当前页/ 用户想看第几页 beginIndex 起始索引 pageSize 一页显示记录数 totalRecord 总记录数
	 * totalPage 总页数 List<Product> list 分页的查询信息
	 * 
	 */
	private int pageNumber;
	private int beginIndex;
	private int pageSize;
	private int totalRecord;
	private int totalPage;
	private List<T> list;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	// 修改get方法，计算公式放入get方法中，使得每次获取beginIndex值，再去进行计算
	public int getBeginIndex() {
		return beginIndex = (this.getPageNumber() - 1) * this.getPageSize();
	}

	/*
	 * public void setBeginIndex(int beginIndex) { this.beginIndex = beginIndex;
	 * }
	 */

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	// 总的页数也是自动计算得出，不需要外部设置
	public int getTotalPage() {
		return totalPage = (this.getTotalRecord() % this.getPageSize() == 0)
				? (this.getTotalRecord() / this.getPageSize()) : (this.getTotalRecord() / this.getPageSize() + 1);
	}

	/*
	 * public void setTotalPage(int totalPage) { this.totalPage = totalPage; }
	 */

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}

}
