package cn.rain.store.order.service;

import cn.rain.store.order.domain.Order;
import cn.rain.store.product.domain.PageBean;
import cn.rain.store.user.domain.User;

public interface OrderService {

	void addOrder(Order order);

	PageBean<Order> searchOlistByUid(PageBean<Order> pageBean, User user);

	Order searchOrderByOid(Order order);

	void payForOrder(Order order);

	void modify(Order order);

}
