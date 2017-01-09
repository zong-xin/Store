package cn.rain.store.product.dao.impl;

import cn.rain.store.product.dao.ProductDao;
import cn.rain.store.product.domain.Category;
import cn.rain.store.product.domain.PageBean;
import cn.rain.store.product.domain.Product;
import cn.rain.store.utils.C3p0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
	// 获得连接
	QueryRunner run = new QueryRunner(C3p0Utils.getDataSource());

	/**
	 * 根据模糊Pname查询商品总数
	 */
	@Override
	public int searchCountForPname(Product product) {
		try {
			// 创建sql语句
			String sql = "select count(*) from product where  pname like ? ";
			// 创建参数
			Object[] param = { "%" + product.getPname() + "%" };
			// 发送sql获得结果
			Long result = (Long) run.query(sql, param, new ScalarHandler());
			return result.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		// 处理结果
	}

	/**
	 * 根据模糊pname查询商品
	 */
	@Override
	public List<Product> searchPlistForPname(PageBean<Product> pageBean, Product product) {
		try {

			// 创建sql语句
			String sql = "select * from product where  pname like ? order by pdate desc limit 12";
			// 配置参数
			Object[] param = { "%" + product.getPname() + "%" };
			// 发送sql获得结果
			List<Product> result  = run.query(sql, param, new BeanListHandler<>(Product.class));
			// 处理结果
			return result;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 模糊查询商品信息
	 */
	@Override
	public List<Product> searchProduct(Product product) {
		try {
			List<Product> result = new ArrayList<>();
			// 创建sql语句
			String sql = "select * from product where  pname like ? order by pdate desc limit 5";
			// 获得参数
			Object[] param = { "%" + product.getPname() + "%" };
			// 发送sql获得结果
			result = run.query(sql, param, new BeanListHandler<Product>(Product.class));
			// 处理结果
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询热销商品
	 */
	@Override
	public List<Product> hotlist() {
		try {
			// 创建sql语句
			String sql = "select * from product where is_hot=1 order by pdate desc limit 9";
			// 发送sql获得结果
			List<Product> result= run.query(sql, new BeanListHandler<>(Product.class));
			// 处理结果
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询单个商品的详细信息
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Product searchProductByPid(Product product) {
		try {
			// 创建sql语句
			String sql = "select * from product where pid=?";
			// 创建参数
			Object[] param = { product.getPid() };
			// 发送sql获得结果
			Product result = run.query(sql, param, new BeanHandler<>(Product.class));
			// 处理结果
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得分类信息的中数据条数
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int searchCount(Category category) {
		try {
			// 创建sql语句
			String sql = "select count(*) from product where cid=?";
			// 创建参数
			Object[] param = { category.getCid() };
			// 发送sql获得结果
			Long result = (Long) run.query(sql, param, new ScalarHandler());
			return result.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		// 处理结果
	}

	/**
	 * 分页分类查询商品信息
	 */
	@SuppressWarnings("all")
	public List<Product> searchPlistByCid(PageBean<Product> pageBean, Category category) {
		try {
			List<Product> result = new ArrayList<>();
			// 创建sql语句
			String sql = "select * from product where cid=? order by pdate desc limit ?,?";
			// 配置参数
			Object[] param = { category.getCid(), pageBean.getBeginIndex(), pageBean.getPageSize() };
			// 发送sql获得结果
			result = run.query(sql, param, new BeanListHandler<Product>(Product.class));
			// 处理结果
			return result;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得商品分类列表
	 */
	@Override
	public List<Category> searchAllClist() {
		try {
			// 创建sql语句
			String sql = "select * from category";
			// 发送sql，获得结果
			List<Category> clist  = run.query(sql, new BeanListHandler<>(Category.class));
			// 处理结果
			return clist;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
