package cn.rain.store.user.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Rain on 2016-12-11.
 * 用于自动登录时的自动登录
 */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {

    /**
     * Default constructor.
     */
    public EncodingFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 过滤器中，解决字符集乱码问题
        // post
        request.setCharacterEncoding("utf-8");

        // get
        // Servlet中使用的getParameter是处理过乱码
        // 问题：原来的getParameter方法可以自行处理乱码么？ 不可以
        // 解决：增强request对象的getParameter方法，可以在方法内部自行处理get乱码问题

        // 1、被代理对象
        final HttpServletRequest hsr = (HttpServletRequest) request;

        // 2、创建代理对象
        Object obj = Proxy.newProxyInstance(hsr.getClass().getClassLoader(), hsr.getClass().getInterfaces(),
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        String m = hsr.getMethod();
                        Object result = method.invoke(hsr, args);
                        if ("GET".equalsIgnoreCase(m)) {
                            // 区别方法名
                            if ("getParameter".equals(method.getName())) {
                                // 增强
                                String temp = result.toString();
                                return new String(temp.getBytes("iso8859-1"), "utf-8");
                            }
                        }

                        return result;
                    }
                });
        HttpServletRequest mhsr = (HttpServletRequest) obj;
        // 需要让目标资源，使用增强的对象
        chain.doFilter(mhsr, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
