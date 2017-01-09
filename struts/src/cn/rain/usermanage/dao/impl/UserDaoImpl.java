package cn.rain.usermanage.dao.impl;

import cn.rain.usermanage.dao.UserDao;
import cn.rain.usermanage.domain.User;
import cn.rain.usermanage.utils.C3p0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;


/**
 * Created by Rain on 2016-12-17.
 * 用户管理
 */
public class UserDaoImpl implements UserDao {
    private QueryRunner run = new QueryRunner(C3p0Utils.getDataSource());
/*
* userID INT  NOT NULL AUTO_INCREMENT, #主键ID
	userName VARCHAR(50)   NULL,  #用户姓名
	logonName VARCHAR(50)   NULL, #登录名
	logonPwd VARCHAR(50)  NULL,   #密码#
	sex VARCHAR(10)  NULL,        #性别（例如：男，女）
	birthday VARCHAR(50) NULL,    #出生日期
	education VARCHAR(20)  NULL,  #学历（例如：研究生、本科、专科、高中）
	telephone VARCHAR(50)  NULL,  #电话
	interest VARCHAR(20)  NULL,   #兴趣爱好（例如：体育、旅游、逛街）
	path VARCHAR(500)  NULL,      #上传路径（path路径）
	filename VARCHAR(100)  NULL,  #上传文件名称（文件名）
	remark VARCHAR(500)  NULL,
* */

    /**
     * 校验登录名
     * @param user
     * @return
     */
    @Override
    public User checkLogonName(User user) {
        try {
            String sql = "select * from s_user where  logonName=? ";
            Object[] param={user.getLogonName()};
           User result= run.query(sql,param,new BeanHandler<>(User.class));
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加用户
     * @param user
     */
    @Override
    public void add(User user) {
        String sql = "INSERT INTO s_user  VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] param={null,user.getUserName(),user.getLogonName(),user.getLogonPwd(),user.getSex(),
                 user.getBirthday(),user.getEducation(),user.getTelephone(),user.getInterest(),
                        user.getPath(),user.getFilename(),user.getRemark()};
        try {
            run.update(sql, param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加用户失败！");
        }
    }

    /**
     * 用户登录验证
     * @param user 用户信息
     * @return 查询后的用户信息
     */
    @Override
    public User login(User user) {
        //创建sql语句
        String sql = "select * from s_user where logonName=? and logonPwd = ?";
        //添加参数
        Object[] param={user.getLogonName(),user.getLogonPwd()};
        try {
            user = run.query(sql, new BeanHandler<>(User.class), param);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("用户登录失败！");
        }
        return user;
    }
}
















