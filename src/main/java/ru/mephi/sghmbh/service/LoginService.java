package ru.mephi.sghmbh.service;

import ru.mephi.sghmbh.model.RoleEnum;

public interface LoginService {
    RoleEnum login(String username, String password);
}
