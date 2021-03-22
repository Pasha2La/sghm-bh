package ru.mephi.sghmbh.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.sghmbh.mapper.UserMapper;
import ru.mephi.sghmbh.model.User;
import ru.mephi.sghmbh.repository.UserRepository;
import ru.mephi.sghmbh.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserInfo(String userId) {
        return mapper.fromDto(userRepository.getUserById(userId));
    }

    @Override
    public User getUser(String username, String password) {
        return mapper.fromDto(userRepository.getUser(username, password));
    }

    @Override
    public String getUserId(String username, String password) {
        return userRepository.getUser(username, password).getId();
    }
}
