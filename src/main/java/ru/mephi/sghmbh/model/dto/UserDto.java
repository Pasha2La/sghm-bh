package ru.mephi.sghmbh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mephi.sghmbh.model.RoleEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    private RoleEnum role;
}
