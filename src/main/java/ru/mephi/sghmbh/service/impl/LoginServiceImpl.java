package ru.mephi.sghmbh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.sghmbh.UserRepository;
import ru.mephi.sghmbh.model.RoleEnum;
import ru.mephi.sghmbh.model.User;
import ru.mephi.sghmbh.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
    private UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public RoleEnum login(String username, String password) {
//        User user = userRepository.getUser(username, password);
//        return user.getRole();
        return RoleEnum.ADMIN;
    }
}
