package cn.rain.store.user.service;

import cn.rain.store.user.domain.User;

public interface UserService {

	User login(User user);

	User searchUser(User user);

	void active(User result);

	void register(User user);

	User findPwd(User user);

	void updatePwd(User user);

}
