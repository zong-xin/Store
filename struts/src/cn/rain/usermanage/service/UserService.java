package cn.rain.usermanage.service;

import cn.rain.usermanage.domain.User;

/**
 * Created by Rain on 2016-12-17.
 */
public interface UserService {
    User login(User user);

    void add(User user);

    User checkLogonName(User user);
}
