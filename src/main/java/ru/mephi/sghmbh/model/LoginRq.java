package ru.mephi.sghmbh.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRq {
    private String username;
    private String password;
}
