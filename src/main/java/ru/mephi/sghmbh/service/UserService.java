package ru.mephi.sghmbh.service;

import ru.mephi.sghmbh.model.User;

public interface UserService {
    User getUserInfo(String userId);

    User getUser(String username, String password);

    String getUserId(String username, String password);
}
