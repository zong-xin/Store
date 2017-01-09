package cn.rain.store.product.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Category implements Serializable {

	private String cid;
	private String cname;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

}
