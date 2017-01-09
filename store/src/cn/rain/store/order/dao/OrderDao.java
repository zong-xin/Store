package cn.rain.store.order.dao;

import java.sql.Connection;

import cn.rain.store.order.domain.Order;
import cn.rain.store.order.domain.OrderItem;
import cn.rain.store.product.domain.PageBean;
import cn.rain.store.user.domain.User;

public interface OrderDao {

	void addOrder(Connection conn, Order order);

	void addOrderItem(Connection conn, OrderItem oi);

	int searchOrderCount(User user);

	PageBean<Order> searchOlistByUid(PageBean<Order> pageBean, User user);

	Order searchOrderByOid(Order order);

	void payForOrder(Order order);

	void modify(Order order);

}
