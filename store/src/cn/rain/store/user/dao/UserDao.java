package cn.rain.store.user.dao;

import cn.rain.store.user.domain.User;

public interface UserDao {

	User login(User user);

	void update(User user);

	User searchUser(User user);

	void register(User user);

	void updatePwd(User user);

}
