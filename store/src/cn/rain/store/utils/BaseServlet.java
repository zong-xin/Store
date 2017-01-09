package cn.rain.store.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 使用反射增强程序扩展性 缺陷： ①只能处理非上传表单 ②文件上传处理不了 getParameter("methodName")为null
 * 
 * @author xps13
 *
 */
@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		// 1、接收参数
		String methodName = request.getParameter("methodName");
		// 2、判断调用哪个方法 ---反射
		// 2.1、获取当前Servlet的字节码
		Class clazz = this.getClass();
		// 2.2、根据methodName 针对性获取需要执行的方法
		// 方法修饰符public
		try {
			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			String invoke = (String) method.invoke(this, request, response);
			if (invoke != null && !"".equals(invoke)) {
				// 程序员需要请求转发了
				request.getRequestDispatcher(invoke).forward(request, response);
				return;
			}
		} catch (Exception e) {
			System.out.println("获取不到对应方法");
			e.printStackTrace();
		}
	}
}
