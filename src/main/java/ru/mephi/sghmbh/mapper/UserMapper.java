package ru.mephi.sghmbh.mapper;

import org.mapstruct.Mapper;
import ru.mephi.sghmbh.model.User;
import ru.mephi.sghmbh.model.dto.UserDto;

@Mapper
public interface UserMapper {
    User fromDto(UserDto userDto);

    UserDto toDto(User user);
}
