package cn.rain.store.product.service.impl;

import java.util.List;

import cn.rain.store.product.dao.ProductDao;
import cn.rain.store.product.dao.impl.ProductDaoImpl;
import cn.rain.store.product.domain.Category;
import cn.rain.store.product.domain.PageBean;
import cn.rain.store.product.domain.Product;
import cn.rain.store.product.service.ProductService;

public class ProductServiceImpl implements ProductService {
	ProductDao dao = new ProductDaoImpl();

	/**
	 * 根据关键字分页查询商品信息
	 */
	@Override
	public PageBean<Product> searchProductByKey(PageBean<Product> pageBean, Product product) {
		// 设置每页显示商品记录为12条
		pageBean.setPageSize(12);
		// 查询总共的商品记录
		int count = dao.searchCountForPname(product);
		pageBean.setTotalRecord(count);

		List<Product> plist = dao.searchPlistForPname(pageBean, product);
		pageBean.setList(plist);

		return pageBean;
	}

	/**
	 * 模糊查询商品
	 */
	@Override
	public List<Product> searchProduct(Product product) {

		return dao.searchProduct(product);
	}

	/**
	 * 查询热销商品
	 */
	@Override
	public List<Product> hotlist() {
		List<Product> product = dao.hotlist();
		return product;
	}

	/**
	 * 查询单个商品的详细信息
	 */
	@Override
	public Product searchProductByPid(Product product) {
		return dao.searchProductByPid(product);
	}

	/**
	 * 用于处理分类分页查询商品列表业务
	 */
	@Override
	public PageBean<Product> searchPlistByCid(PageBean<Product> pageBean, Category category) {
		// 设置每页显示商品记录为12条
		pageBean.setPageSize(12);
		// 查询总共的商品记录
		int count = dao.searchCount(category);
		pageBean.setTotalRecord(count);

		List<Product> plist = dao.searchPlistByCid(pageBean, category);
		pageBean.setList(plist);

		return pageBean;
	}

	/**
	 * 查询商品种类列表
	 */
	@Override
	public List<Category> searchAllClist() {
		List<Category> clist = dao.searchAllClist();
		return clist;
	}

}
