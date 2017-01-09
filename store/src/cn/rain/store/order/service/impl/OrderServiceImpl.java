package cn.rain.store.order.service.impl;

import cn.rain.store.order.dao.OrderDao;
import cn.rain.store.order.dao.impl.OrderDaoImpl;
import cn.rain.store.order.domain.Order;
import cn.rain.store.order.domain.OrderItem;
import cn.rain.store.order.service.OrderService;
import cn.rain.store.product.domain.PageBean;
import cn.rain.store.user.domain.User;
import cn.rain.store.utils.C3p0Utils;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class OrderServiceImpl implements OrderService {
	OrderDao dao = new OrderDaoImpl();

	/**
	 * 根据付款返回值更改订单状态
	 */
	@Override
	public void modify(Order order) {
		dao.modify(order);

	}

	/**
	 * 订单支付
	 */
	@Override
	public void payForOrder(Order order) {
		dao.payForOrder(order);

	}

	/**
	 * 根据oid查询订单详情
	 */
	@Override
	public Order searchOrderByOid(Order order) {
		Order result = dao.searchOrderByOid(order);
		return result;
	}

	/**
	 * 根据用户id查询所有订单信息
	 */
	public PageBean<Order> searchOlistByUid(PageBean<Order> pageBean, User user) {
		// 设置分页信息
		pageBean.setPageSize(2);
		// 查询订单的总数量
		int count = dao.searchOrderCount(user);
		pageBean.setTotalRecord(count);
		// 根据用户查询所有订单信息
		PageBean<Order> result = dao.searchOlistByUid(pageBean, user);
		return result;
	}

	/**
	 * 生成订单
	 * 
	 */
	public void addOrder(Order order) {
		// 需要先生成订单，再生成订单项表， 由于涉及到事物所有要使用同一个connection
		Connection conn = C3p0Utils.getConnection();
		try {
			// 关闭事物自动提交
			conn.setAutoCommit(false);
			dao.addOrder(conn, order);
			List<OrderItem> oilist = order.getOilist();
			// 遍历订单里面的所有订单项，添加到数据库
			for (OrderItem oi : oilist) {
				dao.addOrderItem(conn, oi);
			}
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn);
			throw new RuntimeException(e);
		}
	}

}
