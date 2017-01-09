package cn.rain.store.order.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 购物车对象
 * 
 * @author Rain
 *
 */
@SuppressWarnings("serial")
public class Cart implements Serializable {
	// 总金额
	private double total;
	// 购物项集合
	private Map<String, CartItem> map = new LinkedHashMap<>();

	/**
	 * 删除单个商品
	 * 
	 * @param ci
	 *            被删除的商品
	 */
	public void delCart(CartItem ci) {
		// 获得商品的pid
		String pid = ci.getProduct().getPid();
		map.remove(pid);
	}

	/**
	 * 将商品项添加到购物车里面
	 * 
	 * @param ci
	 *            要新添加的商品
	 */
	public void addCart(CartItem ci) {
		// 将新商品加入购物车
		String pid = ci.getProduct().getPid();
		CartItem cartItem = map.get(pid);
		if (cartItem != null) {
			// 表示原来购物车里面有了这个商品，现在只要执行对数量的追加操作
			int count = cartItem.getCount() + ci.getCount();
			cartItem.setCount(count);
		} else {
			// 直接添加到购物车中
			map.put(pid, ci);
		}

	}

	/**
	 * 获取购物车里面商品总价
	 * 
	 * @return
	 */
	public double getTotal() {
		total = 0;
		if (map.size() > 0) {
			// 遍历获得所有商品项目,获得总价
			Set<String> keys = map.keySet();
			for (String key : keys) {
				CartItem ci = map.get(key);
				total += ci.getSubtotal();
			}
		}
		return total;
	}

	/*
	 * public void setTotal(double total) { this.total = total; }
	 */

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

}
