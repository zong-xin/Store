package cn.rain.store.product.service;

import java.util.List;

import cn.rain.store.product.domain.Category;
import cn.rain.store.product.domain.PageBean;
import cn.rain.store.product.domain.Product;

public interface ProductService {

	List<Category> searchAllClist();

	PageBean<Product> searchPlistByCid(PageBean<Product> pageBean, Category category);

	Product searchProductByPid(Product product);

	List<Product> hotlist();

	List<Product> searchProduct(Product product);

	PageBean<Product> searchProductByKey(PageBean<Product> pageBean, Product product);

}
