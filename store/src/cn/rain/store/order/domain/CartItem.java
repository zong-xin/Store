package cn.rain.store.order.domain;

import java.io.Serializable;

import cn.rain.store.product.domain.Product;

/**
 * 购物车的购物项
 * 
 * @author Rain
 *
 */
@SuppressWarnings("all")
public class CartItem implements Serializable {

	private Product product;
	private int count;
	private double subtotal;

	// 直接内部获取数据，计算得出小计金额
	public double getSubtotal() {
		return subtotal = product.getShop_price() * count;
	}

	/*
	 * public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
	 */

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

}
