package ru.mephi.sghmbh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.sghmbh.service.LoginService;
import ru.mephi.sghmbh.service.UserService;

@Service
public class LoginServiceImpl implements LoginService {
    private UserService userService;

    @Autowired
    public LoginServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String login(String username, String password) {
        return userService.getUserId(username, password);
    }
}
