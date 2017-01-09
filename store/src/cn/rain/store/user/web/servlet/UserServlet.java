package cn.rain.store.user.web.servlet;

import cn.rain.store.user.domain.User;
import cn.rain.store.user.service.UserService;
import cn.rain.store.user.service.impl.UserServiceImpl;
import cn.rain.store.utils.BaseServlet;
import cn.rain.store.utils.MD5Util;
import cn.rain.store.utils.MailUtils;
import cn.rain.store.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Rain on 2016-12-11.
 * 用于处理用户的信息的相关操作
 */
@WebServlet(name = "UserServlet")
public class UserServlet extends BaseServlet {
    /**
     * 忘记密码后的修改密码
     *
     */
    public String updatePwd(HttpServletRequest request) {
        try {
            // 获得实体，封装参数
            User user = new User();
            user.setUsername(request.getParameter("username"));
            user.setPassword(MD5Util.MD5(request.getParameter("password")));
            // 调用service，处理业务
            UserService service = new UserServiceImpl();
            service.updatePwd(user);
            // 转发到修改密码界面
            request.setAttribute("msg", "密码修改成功，快起登录试试吧！");
            return "/msg.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "服务器繁忙，稍后再试！");
            e.printStackTrace();
            return "/msg.jsp";
        }

    }

    /**
     * 用于转发修改密码的页面 ，将修改密码的页面放在WEB-INF里面，
     * 只有通过请求转发才可以进入，有效避免暴力改密码。
     *
     */
    public String checkUserForPwd(HttpServletRequest request) {
        try {
            // 获得实体，封装参数
            User user = new User();
            user.setCode(request.getParameter("code"));
            User result = null;
            // 调用service，处理业务
            UserService service = new UserServiceImpl();
            result = service.searchUser(user);
            // 转发到修改密码界面
            request.setAttribute("user", result);
            return "/WEB-INF/pages/updatePwd.jsp";

        } catch (Exception e) {
            request.setAttribute("msg", "服务器繁忙，稍后再试！");
            e.printStackTrace();
            return "msg.jsp";
        }

    }

    /**
     * 用于找回密码
     *
     */
    public String findPwd(HttpServletRequest request) {
        try {
            // 获得实体，封装参数
            User user = new User();
            user.setUsername(request.getParameter("username"));
            // 调用service，处理业务
            UserService service = new UserServiceImpl();
            User result  = service.findPwd(user);
            if (result != null) {
                // 发送验证身份邮件
                MailUtils.findMail(user.getEmail(),user.getCode());
                request.setAttribute("msg", "亲，赶快去你的邮箱里点击链接设置新的密码吧！");
                return "msg.jsp";
            } else {
                request.setAttribute("msg", "用户名不存在！");
                return "msg.jsp";
            }

        } catch (Exception e) {
            request.setAttribute("msg", "服务器繁忙，稍后再试！");
            e.printStackTrace();
            return "msg.jsp";
        }

    }

    /**
     * 用于用户名核对
     *
     */
    public String checkUsername(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获得实体，封装参数
            User user = new User();
            user.setUsername(request.getParameter("username"));
            // 调用service，处理业务
            UserService service = new UserServiceImpl();
            User result = service.searchUser(user);
            // 处理回显
            if (result == null) {
                response.getWriter().write("用户名可用");
            } else {
                response.getWriter().write("用户名不可用");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 用于用户退出操作
     */
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获得实体，封装参数【暂无】

            // 调用service，处理业务【暂无】

            // 处理回显
            request.getSession().removeAttribute("user");
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("autoLogin".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } catch (Exception e) {
            request.setAttribute("msg", "服务器繁忙，稍后再试！");
            e.printStackTrace();
            return "msg.jsp";
        }
        return "";
    }

    /**
     * 用于用户登录
     */
    public String login(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获得实体，封装参数
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());
            user.setPassword(MD5Util.MD5(request.getParameter("password")));
            String autoLogin = request.getParameter("autoLogin");
            String rememberUsername = request.getParameter("rememberUsername");
            // 调用service，处理业务
            UserService service = new UserServiceImpl();
            User result = service.login(user);
            if (result != null) {// 登录成功
                if (autoLogin != null) {// 表示勾选了自动登录
                    Cookie cookie = new Cookie("autoLogin", result.getUsername() + "#" + result.getPassword());
                    cookie.setMaxAge(60 * 60 * 24);// 设置保存时长
                    response.addCookie(cookie);// 将cookie添加到响应中发给浏览器
                }
                if (rememberUsername != null) {// 表示勾选了记住用户名
                    Cookie cookie = new Cookie("rememberUsername", result.getUsername());
                    cookie.setMaxAge(60 * 60 * 24 * 7);
                    response.addCookie(cookie);

                }
                request.getSession().setAttribute("user", result);
                // 处理回显
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return "";
            } else {
                request.setAttribute("msg", "用户名或密码不对哦！也许是您账户没有激活！");
                return "msg.jsp";
            }
        } catch (Exception e) {
            request.setAttribute("msg", "服务器出现异常，请联系管理员！");
            return "msg.jsp";
        }

    }

    /**
     * 用于激活用户
     */
    public String active(HttpServletRequest request) {
        try {
            // 获得实体，封装参数
            User user = new User();
            user.setCode(request.getParameter("code"));
            // 调用service，处理业务
            UserService service = new UserServiceImpl();
            User result = service.searchUser(user);
            if (result.getState() == 0) {
                // 表示没有激活过，进行激活
                result.setState(1);
                service.active(result);
                request.setAttribute("msg", "用户激活成功，可以去疯狂购物了！");
                return "msg.jsp";
            } else {// 已经激活
                request.setAttribute("msg", "用户已经激活，请勿重复激活！");
                return "msg.jsp";
            }

            // 处理回显
        } catch (Exception e) {
            request.setAttribute("msg", "服务器出现异常，请联系管理员！");
            return "msg.jsp";
        }

    }

    /**
     * 用于注册用户
     *
     */
    public String register(HttpServletRequest request) {
        try {
            // 获得实体，封装参数
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());
            // 添加id和code以及state激活状态码
            user.setPassword(MD5Util.MD5(request.getParameter("password")));
            user.setUid(UUIDUtils.getUUID());
            user.setState(0);
            user.setCode(UUIDUtils.getUUID64());
            // 调用service，处理业务
            UserService service = new UserServiceImpl();
            service.register(user);
            // 发送激活邮件
            MailUtils.sendMail(user.getEmail(),user.getCode());
            // 处理回显
            request.setAttribute("msg", "用户注册成功，请尽快登录邮箱激活用户！");
            return "msg.jsp";
        } catch (Exception e) {
            request.setAttribute("msg", "服务器出现异常，请联系管理员！");
            e.printStackTrace();
            return "msg.jsp";
        }

    }

}
