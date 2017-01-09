package cn.rain.store.user.service.impl;

import cn.rain.store.user.dao.UserDao;
import cn.rain.store.user.dao.impl.UserDaoImpl;
import cn.rain.store.user.domain.User;
import cn.rain.store.user.service.UserService;

public class UserServiceImpl implements UserService {
	UserDao dao = new UserDaoImpl();

	/**
	 * 忘记密码后的修改密码
	 */
	@Override
	public void updatePwd(User user) {
		dao.updatePwd(user);

	}

	/**
	 * 找回密码业务
	 */
	@Override
	public User findPwd(User user) {
		User result = dao.searchUser(user);
		return result;
	}

	/**
	 * 用于查询业务
	 */
	@Override
	public User searchUser(User user) {
		User result = dao.searchUser(user);
		return result;
	}

	/**
	 * 用于激活用户业务 ，调用dao的更新操作
	 */
	@Override
	public void active(User user) {
		dao.update(user);

	}

	/**
	 * 注册用户业务
	 * 
	 * @return
	 */
	@Override
	public User login(User user) {
		return dao.login(user);
	}

	/**
	 * 注册用户业务
	 */
	@Override
	public void register(User user) {
		dao.register(user);

	}

}
