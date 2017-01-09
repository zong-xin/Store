package cn.rain.store.order.web;

import cn.rain.store.order.domain.Cart;
import cn.rain.store.order.domain.CartItem;
import cn.rain.store.order.domain.Order;
import cn.rain.store.order.domain.OrderItem;
import cn.rain.store.order.service.OrderService;
import cn.rain.store.order.service.impl.OrderServiceImpl;
import cn.rain.store.product.domain.PageBean;
import cn.rain.store.product.domain.Product;
import cn.rain.store.product.service.ProductService;
import cn.rain.store.product.service.impl.ProductServiceImpl;
import cn.rain.store.user.domain.User;
import cn.rain.store.utils.BaseServlet;
import cn.rain.store.utils.PaymentUtil;
import cn.rain.store.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单支付成功
	 * 
	 */
	public String payOrderSuccess(HttpServletRequest request) {
		// 获取返回的所有参数
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		String hmac = request.getParameter("hmac");

		// 校验数据是否正确
		boolean flag = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		if (flag) {
			// 数据正确,修改订单状态
			OrderService service = new OrderServiceImpl();
			try {
				Order order = new Order();
				order.setOid(r6_Order);
				order = service.searchOrderByOid(order);
				// 更改订单状态
				order.setState(1);
				service.modify(order);
				request.setAttribute("msg", "订单付款成功,订单号为:" + r6_Order + "///付款金额为:" + r3_Amt);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		} else {
			throw new RuntimeException("数据遭篡改");
		}

		return "/msg.jsp";
	}

	/**
	 * 订单支付
	 * 
	 */
	public String payForOrder(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获得参数，封装实体
			Order order = new Order();
			BeanUtils.populate(order, request.getParameterMap());
			// 业务处理
			OrderService service = new OrderServiceImpl();
			service.payForOrder(order);
			// 数据回显
			String p0_Cmd = "Buy";
			String p1_MerId = "10001126856";
			String p2_Order = order.getOid();
			String p3_Amt = "0.01";// 测试用1分钱，真正开发中用order.getTotal();
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = "http://localhost:80" + request.getContextPath() + "/order?methodName=payOrderSuccess";
			String p9_SAF = "0";
			String pa_MP = "";
			String pd_FrpId = request.getParameter("pd_FrpId");
			String pr_NeedResponse = "1";
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
					p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse,
					"69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");

			StringBuffer buffer = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			buffer.append("p0_Cmd=" + p0_Cmd);
			buffer.append("&p1_MerId=" + p1_MerId);
			buffer.append("&p2_Order=" + p2_Order);
			buffer.append("&p3_Amt=" + p3_Amt);
			buffer.append("&p4_Cur=" + p4_Cur);
			buffer.append("&p5_Pid=" + p5_Pid);
			buffer.append("&p6_Pcat=" + p6_Pcat);
			buffer.append("&p7_Pdesc=" + p7_Pdesc);
			buffer.append("&p8_Url=" + p8_Url);
			buffer.append("&p9_SAF=" + p9_SAF);
			buffer.append("&pa_MP=" + pa_MP);
			buffer.append("&pd_FrpId=" + pd_FrpId);
			buffer.append("&pr_NeedResponse=" + pr_NeedResponse);
			buffer.append("&hmac=" + hmac);
			// 重定向到第三方支付平台
			response.sendRedirect(buffer.toString());
		} catch (Exception e) {
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			e.printStackTrace();
			return "/msg.jsp";
		}
		return "";

	}

	/**
	 * 查询订单信息
	 */
	public String searchOrderByOid(HttpServletRequest request) {
		try {
			// 获得参数，封装实体
			Order order = new Order();
			BeanUtils.populate(order, request.getParameterMap());
			// 业务处理
			OrderService service = new OrderServiceImpl();
			order = service.searchOrderByOid(order);
			// 数据回显
			request.setAttribute("order", order);
			return "/order_info.jsp";
		} catch (Exception e) {
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			e.printStackTrace();
			return "/msg.jsp";
		}

	}

	/**
	 * 查询订单信息
	 * 
	 */
	public String searchOlistByUid(HttpServletRequest request) {
		try {
			// 对用户的登录状态进行判断
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				// 表示未登录
				request.setAttribute("msg", "亲，请先登录再查询你的订单！");
				return "/msg.jsp";
			}
			// 获得参数，封装实体
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			PageBean<Order> pageBean = new PageBean<>();
			pageBean.setPageNumber(pageNumber);
			// 业务处理
			OrderService service = new OrderServiceImpl();
			pageBean = service.searchOlistByUid(pageBean, user);
			// 数据回显
			request.setAttribute("pageBean", pageBean);
			return "/order_list.jsp";
		} catch (Exception e) {
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			e.printStackTrace();
			return "/msg.jsp";
		}

	}

	/**
	 * 添加生成订单
	 * 
	 */
	public String addOrder(HttpServletRequest request, HttpServletResponse response) {

		try {
			Cart cart = this.getCart(request);
			Map<String, CartItem> map = cart.getMap();
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				// 表示未登录，则提示用户先登录再提交订单
				request.setAttribute("msg", "亲，只有登录以后才能提交订单哦！");
				return "/msg.jsp";
			}
			if (map.size() == 0) {
				// 表示购物车里面没有任何商品
				request.setAttribute("msg", "亲，你的购物车是空的哦！");
				return "/msg.jsp";
			}
			// 获得参数，封装实体 order orderItem product
			Order order = new Order();
			Set<String> keys = map.keySet();
			double total = order.getTotal();
			List<OrderItem> list = new ArrayList<>();
			for (String key : keys) {
				OrderItem oi = new OrderItem();
				CartItem ci = map.get(key);
				oi.setItemid(UUIDUtils.getUUID());
				oi.setProduct(ci.getProduct());
				oi.setCount(ci.getCount());
				oi.setSubtotal(ci.getSubtotal());
				oi.setOrder(order);
				list.add(oi);
				// 累计订单的总金额
				total += ci.getSubtotal();

			}
			order.setOilist(list);
			order.setOid(UUIDUtils.getUUID());
			order.setTotal(total);
			order.setOrdertime(new Date());
			order.setState(0);
			order.setUser(user);

			// 处理业务
			OrderService service = new OrderServiceImpl();
			service.addOrder(order);

			// 数据回显
			map.clear();
			request.setAttribute("order", order);
			return "/order_info.jsp";
		} catch (Exception e) {
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			e.printStackTrace();
			return "/msg.jsp";
		}
	}

	/**
	 * 清空购物车
	 * 
	 */
	public String delAllCart(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获得参数，封装实体【暂无】
			// 获取购物车
			Cart cart = this.getCart(request);
			// 业务处理
			Map<String, CartItem> map = cart.getMap();
			map.clear();
			// 数据回显
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
		} catch (Exception e) {
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			e.printStackTrace();
			return "/msg.jsp";
		}

		return "";
	}

	/**
	 * 删除购物车单个商品
	 * 
	 */
	public String delCart(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获得参数，封装实体
			Product product = new Product();
			BeanUtils.populate(product, request.getParameterMap());

			CartItem ci = new CartItem();
			ci.setProduct(product);
			// 获取购物车
			Cart cart = this.getCart(request);
			// 业务处理
			cart.delCart(ci);
			// 数据回显
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
		} catch (Exception e) {
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			e.printStackTrace();
			return "/msg.jsp";
		}

		return "";
	}

	/**
	 * 创建购物车session
	 */
	private Cart getCart(HttpServletRequest request) {
		// 首先获得session
		HttpSession session = request.getSession();
		Cart result = (Cart) session.getAttribute("cart");
		if (session == null || result == null) {// 表示里面原来没有购物车,直接新建存入
			result = new Cart();
			session.setAttribute("cart", result);
		}
		return result;
	}

	/**
	 * 添加购物车操作
	 * 
	 */
	public String addCart(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获得参数，封装实体
			String pid = request.getParameter("pid");
			Product product = new Product();
			product.setPid(pid);
			int count = Integer.parseInt(request.getParameter("count"));

			// 获得product的详细信息
			ProductService service = new ProductServiceImpl();
			product = service.searchProductByPid(product);

			CartItem ci = new CartItem();
			ci.setCount(count);
			ci.setProduct(product);
			// 处理业务
			Cart cart = this.getCart(request);// 调用方法，判断或者创建购物车session
			// 将商品项添加到购物车里，添加前先判断，是否里面有相同产品
			cart.addCart(ci);
			// 数据回显--重定向到购物车页面
			response.sendRedirect(request.getContextPath() + "/cart.jsp");

		} catch (Exception e) {
			request.setAttribute("msg", "服务器异常，请稍后再试！");
			e.printStackTrace();
			return "msg.jsp";
		}
		return "";
	}

}
