package cn.rain.store.order.dao.impl;

import cn.rain.store.order.dao.OrderDao;
import cn.rain.store.order.domain.Order;
import cn.rain.store.order.domain.OrderItem;
import cn.rain.store.product.domain.PageBean;
import cn.rain.store.product.domain.Product;
import cn.rain.store.user.domain.User;
import cn.rain.store.utils.C3p0Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
	/**
	 * 修改支付状态
	 */
	@Override
	public void modify(Order order) {
		QueryRunner run = new QueryRunner(C3p0Utils.getDataSource());
		try {
			// 创建sql语句
			String sql = "update orders set state=? where oid=?";

			// 设置参数
			Object[] param = { order.getState(), order.getOid() };
			// 发送sql，获得返回值
			run.update(sql, param);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 订单支付，更新订单信息
	 */
	@Override
	public void payForOrder(Order order) {
		QueryRunner run = new QueryRunner(C3p0Utils.getDataSource());
		try {
			// 创建sql语句
			String sql = "update orders set address=?,name=?,telephone=? where oid=?";

			// 设置参数
			Object[] param = { order.getAddress(), order.getName(), order.getTelephone(), order.getOid() };
			// 发送sql，获得返回值
			run.update(sql, param);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 根据oid查询订单详情
	 */
	@Override
	public Order searchOrderByOid(Order order) {
		QueryRunner run = new QueryRunner(C3p0Utils.getDataSource());
		try {
			// 创建sql语句
			String sql = "select * from orders where oid=?";
			// 设置参数
			Object[] param = { order.getOid() };
			// 发送sql，获得返回值
			order = run.query(sql, param, new BeanHandler<>(Order.class));
			// 根据oid查询订单项和商品信息
			String sql2 = "select * from orderitem oi,product pr where oi.pid=pr.pid and oid=?";
			Object[] param2 = { order.getOid() };
			List<Map<String, Object>> maplist = run.query(sql2, param2, new MapListHandler());
			if (maplist != null && maplist.size() > 0) {
				// 创建List<OrderItem> 存储oi数据
				List<OrderItem> oilist = new ArrayList<>();
				for (Map<String, Object> map : maplist) {
					// 封装orderItem数据
					OrderItem oi = new OrderItem();
					BeanUtils.populate(oi, map);
					// 封装product数据
					Product product = new Product();
					BeanUtils.populate(product, map);
					// 将product放进oi
					oi.setProduct(product);
					oilist.add(oi);
				}
				// 将oilist存到Order
				order.setOilist(oilist);
			}
			return order;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 查询用户总的订单数
	 */
	@Override
	public int searchOrderCount(User user) {
		QueryRunner run = new QueryRunner(C3p0Utils.getDataSource());
		try {
			// 创建sql语句
			String sql = "select count(*) from orders where uid=?";
			// 设置参数
			Object[] param = { user.getUid() };
			// 发送sql，获得返回值
			Long count = (long) run.query(sql, param, new ScalarHandler());
			return count.intValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 查询用户的所有订单
	 */
	@Override
	public PageBean<Order> searchOlistByUid(PageBean<Order> pageBean, User user) {
		QueryRunner run = new QueryRunner(C3p0Utils.getDataSource());
		List<Order> olist = new ArrayList<>();
		try {
			// 创建sql语句
			String sql = "select * from orders where uid=? limit ?,?";

			// 设置参数
			Object[] param = { user.getUid(), pageBean.getBeginIndex(), pageBean.getPageSize() };
			// 发送sql，获得返回值
			List<Order> orderlist = run.query(sql, param, new BeanListHandler<Order>(Order.class));
			// 遍历olist获得每个order，然后去查询对应订单项以及商品
			if (orderlist != null && orderlist.size() > 0) {
				for (Order order : orderlist) {
					String sql2 = "select * from orderitem oi,product pro  where oi.pid=pro.pid and oid=?";
					Object[] param2 = { order.getOid() };
					List<Map<String, Object>> maps = run.query(sql2, param2, new MapListHandler());
					List<OrderItem> oilist = new ArrayList<>();
					for (Map<String, Object> map : maps) {
						// 封装订单项信息
						OrderItem oi = new OrderItem();
						BeanUtils.populate(oi, map);
						// 封装商品信息
						Product product = new Product();
						BeanUtils.populate(product, map);
						// 将商品信息加到订单项中
						oi.setProduct(product);
						oi.setOrder(order);
						oilist.add(oi);
					}
					order.setOilist(oilist);
					// 把每个添加完数据order添加到olist里面
					olist.add(order);
				}

			}

			pageBean.setList(olist);
			return pageBean;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将订单信息添加到数据库
	 * 
	 */
	@Override
	public void addOrder(Connection conn, Order order) {
		QueryRunner run = new QueryRunner();
		try {
			// 创建sql语句
			String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
			/*
			 * oid varchar(32) NOT NULL, ordertime datetime DEFAULT NULL, total
			 * double DEFAULT NULL, state int(11) DEFAULT NULL, address
			 * varchar(30) DEFAULT NULL, name varchar(20) DEFAULT NULL,
			 * telephone varchar(20) DEFAULT NULL, uid varchar(32) DEFAULT NULL,
			 */
			// 设置参数
			Object[] param = { order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
					order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid() };
			// 发送sql，获得返回值
			run.update(conn, sql, param);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将订单项信息添加到数据库
	 * 
	 */
	@Override
	public void addOrderItem(Connection conn, OrderItem oi) {
		QueryRunner run = new QueryRunner();
		try {
			// 创建sql语句
			String sql = "insert into orderitem values(?,?,?,?,?)";

			// 设置参数
			Object[] param = { oi.getItemid(), oi.getCount(), oi.getSubtotal(), oi.getProduct().getPid(),
					oi.getOrder().getOid() };
			// 发送sql，获得返回值
			run.update(conn, sql, param);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
