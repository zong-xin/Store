package cn.rain.store.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * HttpServletRequestWrapper
 * 
 * 上面就是使用了装饰者设计模式，而且对HttpServletRequest方法进行了简单实现。 并没有增强任何方法功能。
 * 
 * 
 * 写一个类 继承 HttpServletRequestWrapper
 * 
 * @author xps13
 *
 */
public class MyHttpServletRequest extends HttpServletRequestWrapper {

	private HttpServletRequest old;

	public MyHttpServletRequest(HttpServletRequest old) {
		super(old);
		this.old = old;
	}

	/**
	 * 只需要关注增强这个方法就行了
	 */
	@Override
	public String getParameter(String name) {

		// 判断是否是get请求
		String method = old.getMethod();
		if ("GET".equalsIgnoreCase(method)) {
			// get new String(u.getBytes("iso8859-1"),"utf-8");

			// temp 没有处理乱码的
			String temp = old.getParameter(name);

			try {
				// 处理了乱码
				temp = new String(temp.getBytes("iso8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return temp;
		}
		return old.getParameter(name);
	}

}
