package cn.rain.usermanage.action;

import cn.rain.usermanage.domain.User;
import cn.rain.usermanage.service.UserService;
import cn.rain.usermanage.service.impl.UserServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rain on 2016-12-17.
 *用户管理系统
 */
public class UserManageAction extends ActionSupport implements ModelDriven<User> {
    private User user = new User();

    @Override
    public User getModel() {
        return user;
    }

    /**
     * 校验登录名是否已经存在
     *
     * @return
     */
    public String checkLogonName() {
        UserService service = new UserServiceImpl();
        User result = service.checkLogonName(user);
        Map map = new HashMap();
        if (result == null) {
            //登录名不存在，可以使用
            map.put("ifExist", true);
        } else {
            //用户名存在，不可以使用
            map.put("ifExist", false);
        }
        ActionContext.getContext().put("json", map);
        return "jsonResult";
    }

    /**
     * 添加用户
     *
     * @return
     */
    public String add() {
        try {
            UserService service = new UserServiceImpl();
            service.add(user);
        } catch (Exception e) {
            e.printStackTrace();
            String addError = this.getText("addError");
            this.addActionError(addError);
            return "";
        }
        return "add";
    }
    /**
     * 用户登录
     * @return
     * @throws UnsupportedEncodingException
     */
    public String login() throws UnsupportedEncodingException {
        UserService service=new UserServiceImpl();
        User result = service.login(user);
        //根据返回值判断是否登录成功
        if (result == null) {
            //表示登录不成功,设置错误提示信息
            String logonError = this.getText("LogonError");
           // logonError = new String(logonError.getBytes("iso8859-1"), "utf-8");//解决中文乱码问题
            this.addActionError(logonError);
            return INPUT;
        } else {
            //登录成功,将user保存到session
            ServletActionContext.getRequest().getSession().setAttribute("user",result);
            ServletActionContext.getRequest().setAttribute("user",result);
            return SUCCESS;
        }
    }
}
