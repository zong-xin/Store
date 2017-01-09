package cn.rain.store.order.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.rain.store.user.domain.User;

@SuppressWarnings("all")
public class Order implements Serializable {
	private String oid;
	private Date ordertime;
	private double total;
	private int state;// 订单状态 0表示为支付 1表示已经支付
	private String address;
	private String name;
	private String telephone;

	private User user;

	private List<OrderItem> oilist;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOilist() {
		return oilist;
	}

	public void setOilist(List<OrderItem> oilist) {
		this.oilist = oilist;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

}
