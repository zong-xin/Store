package cn.rain.store.product.web;

import cn.rain.store.product.domain.Category;
import cn.rain.store.product.domain.PageBean;
import cn.rain.store.product.domain.Product;
import cn.rain.store.product.service.ProductService;
import cn.rain.store.product.service.impl.ProductServiceImpl;
import cn.rain.store.utils.BaseServlet;
import cn.rain.store.utils.ChangeCookie;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Servlet implementation class ProductServlet
 */

public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 主要用于模糊（分页暂无）搜索商品
	 * 
	 */
	public String searchProductByKey(HttpServletRequest request) {
		try {
			// 获得参数，封装实体
			PageBean<Product> pageBean = new PageBean<>();
			// 将前端接收到的String页码转换成int并保存到对象中
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			pageBean.setPageNumber(pageNumber);
			// 获取商品的关键字保存到对象用于查询
			Product product = new Product();
			BeanUtils.populate(product, request.getParameterMap());
			// 调用service处理业务
			ProductService service = new ProductServiceImpl();
			pageBean = service.searchProductByKey(pageBean, product);
			// 数据回显--请求转发
			request.setAttribute("pageBean", pageBean);
			return "/searchProduct_list.jsp";

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			return "/msg.jsp";
		}

	}

	/**
	 * 主要用于模糊搜索5个商品
	 */
	public String searchProduct(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获得参数，封装实体
			Product product = new Product();
			BeanUtils.populate(product, request.getParameterMap());
			// 调用service处理业务
			ProductService service = new ProductServiceImpl();
			List<Product> plist = service.searchProduct(product);
			// 数据回显--通过json发送数据
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[] { "market_price", "shop_price", "pimage", "pdate", "pdesc" });
			String json = JSONArray.fromObject(plist, jsonConfig).toString();
			response.getWriter().write(json);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			return "/msg.jsp";
		}

	}

	/**
	 * 主要用于查询热门商品
	 */
	public String hotlist(HttpServletRequest request) {
		try {
			// 获得参数，封装实体【暂无】
			// 调用service处理业务
			ProductService service = new ProductServiceImpl();
			List<Product> hotlist = service.hotlist();
			// 数据回显--
			request.setAttribute("hotlist", hotlist);
			return "/index.jsp";

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			return "/msg.jsp";
		}

	}

	/**
	 * 主要用于分类分页查询商品
	 */
	public String searchAllProductByPid(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获得参数，封装实体
			Product product = new Product();
			String pid =request.getParameter("c_pid");
			product.setPid(pid);
			// 调用service处理业务
			ProductService service = new ProductServiceImpl();
			product = service.searchProductByPid(product);
			// 数据回显--json
			if(product!=null){
				response.getWriter().write(product.getPimage());
			}
			return "";

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			return "/msg.jsp";
		}

	}

	/**
	 * 主要查看单个商品的详情信息
	 */
	public String searchProductByPid(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获得参数，封装实体
			Product product = new Product();
			BeanUtils.populate(product, request.getParameterMap());

			// 调用service处理业务
			ProductService service = new ProductServiceImpl();
			product = service.searchProductByPid(product);
			// 创建cookie保存浏览浏览记录
			Cookie[] cookies = request.getCookies();
			String c_pid = product.getPid();
			// 遍历获得所有的cookie
			for (Cookie c : cookies) {
				if ("c_pid".equals(c.getName())) {
					String temp = c.getValue();
					c_pid = ChangeCookie.change(temp, c_pid);
				}
			}
			Cookie cookie = new Cookie("c_pid", c_pid);
			cookie.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(cookie);
			// 数据回显--请求转发
			request.setAttribute("product", product);
			return "/product_info.jsp";

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			return "/msg.jsp";
		}

	}

	/**
	 * 主要用于分类分页查询商品
	 */
	public String searchPlistByCid(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获得参数，封装实体
			PageBean<Product> pageBean = new PageBean<>();
			// 将前端接收到的String页码转换成int并保存到对象中
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			pageBean.setPageNumber(pageNumber);
			// 获取商品分类id保存到对象用于查询
			Category category = new Category();
			category.setCid(request.getParameter("cid"));

			// 调用service处理业务
			ProductService service = new ProductServiceImpl();
			pageBean = service.searchPlistByCid(pageBean, category);
			// 数据回显--请求转发
			request.setAttribute("pageBean", pageBean);
			return "/product_list.jsp";

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			return "/msg.jsp";
		}

	}

	/**
	 * 主要用于查询商品分类信息
	 */
	public String searchAllClist(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获得参数，封装实体【暂无】
			// 调用service处理业务
			ProductService service = new ProductServiceImpl();
			List<Category> clist = service.searchAllClist();
			// 数据回显--将json数据写给页面
			JSONArray json = JSONArray.fromObject(clist);
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			return "/msg.jsp";
		}

		return "";
	}

}
