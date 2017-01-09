package cn.rain.store.product.dao;

import java.util.List;

import cn.rain.store.product.domain.Category;
import cn.rain.store.product.domain.PageBean;
import cn.rain.store.product.domain.Product;

public interface ProductDao {

	List<Category> searchAllClist();

	int searchCount(Category category);

	List<Product> searchPlistByCid(PageBean<Product> pageBean, Category category);

	Product searchProductByPid(Product product);

	List<Product> hotlist();

	List<Product> searchProduct(Product product);

	int searchCountForPname(Product product);

	List<Product> searchPlistForPname(PageBean<Product> pageBean, Product product);

}
