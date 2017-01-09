package cn.rain.usermanage.dao;

import cn.rain.usermanage.domain.User;

/**
 * Created by Rain on 2016-12-17.
 */
public interface UserDao {
    User login(User user);

    void add(User user);

    User checkLogonName(User user);
}
