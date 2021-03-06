package ru.mephi.sghmbh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    private RoleEnum role;
}
