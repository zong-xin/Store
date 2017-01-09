package cn.rain.store.user.web.filter;

import cn.rain.store.user.domain.User;
import cn.rain.store.user.service.UserService;
import cn.rain.store.user.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Rain on 2016-12-11.
  * 用于自动登录的过滤器
 *
 */
@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 强转
        HttpServletRequest hsrq = (HttpServletRequest) request;
        HttpServletResponse hsrp = (HttpServletResponse) response;
        // 获取用户登录信息
        User user = new User();
        Cookie[] cookies = hsrq.getCookies();
        for (Cookie cookie : cookies) {
            if ("autoLogin".equals(cookie.getName())) {
                String[] split = cookie.getValue().split("#");
                user.setUsername(split[0]);
                user.setPassword(split[1]);
                // 调用service，处理业务
                UserService service = new UserServiceImpl();
                User result = service.login(user);
                if (result != null) {
                    hsrq.getSession().setAttribute("user", result);
                    // 处理回显
                    hsrq.getRequestDispatcher("/index.jsp").forward(hsrq, hsrp);
                    return;
                } else {

                    chain.doFilter(request, response);
                }

            }
        }

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}

