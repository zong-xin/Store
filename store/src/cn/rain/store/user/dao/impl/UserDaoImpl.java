package cn.rain.store.user.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.rain.store.user.dao.UserDao;
import cn.rain.store.user.domain.User;
import cn.rain.store.utils.C3p0Utils;

public class UserDaoImpl implements UserDao {

	QueryRunner run = new QueryRunner(C3p0Utils.getDataSource());

	/**
	 * 修改密码
	 */
	@Override
	public void updatePwd(User user) {
		try {
			// 创建sql语句
			String sql = "update user set password=? where username=?";
			// 添加参数
			Object[] params = { user.getPassword(), user.getUsername() };
			// 发送sql语句
			run.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 用于登录操作
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Override
	public User login(User user) {
		User result = null;
		try {
			// 创建sql语句
			String sql = "select * from user where username=? and password=?";
			// 添加参数
			Object[] params = { user.getUsername(), user.getPassword() };
			// 发送sql语句
			result = run.query(sql, params, new BeanHandler<User>(User.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 用于激活用户
	 */
	@Override
	public void update(User user) {
		try {
			// 创建sql语句
			String sql = "update user set password=?,name=?,telephone=?,birthday=?,sex=?,state=? where Uid=?  ";
			// 添加参数
			Object[] params = { user.getPassword(), user.getName(), user.getTelephone(), user.getBirthday(),
					user.getSex(), user.getState(), user.getUid() };
			// 发送sql语句
			run.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 用于查询用户
	 */
	@SuppressWarnings("deprecation")
	@Override
	public User searchUser(User user) {
		try {
			User result = null;
			// 创建sql语句
			String sql = "select * from user where uid=? or username=? or email =? or code=?";
			// 添加参数
			Object[] params = { user.getUid(), user.getUsername(), user.getEmail(), user.getCode() };
			// 发送sql语句
			result = run.query(sql, params, new BeanHandler<User>(User.class));
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 进行用户注册，添加数据
	 */
	@Override
	public void register(User user) {
		try {
			// 创建sql语句
			String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
			// 添加参数
			Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
					user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };
			// 发送sql语句
			run.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
