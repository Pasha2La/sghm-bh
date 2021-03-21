package ru.mephi.sghmbh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mephi.sghmbh.model.RoleEnum;
import ru.mephi.sghmbh.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final static String USERNAME_COLUMN = "USERNAME";
    private final static String PASSWORD_COLUMN = "PASSWORD";
    private final static String ROLE_COLUMN = "ROLE";
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUser(String username, String password) {

        Map<String, String> params = new HashMap<>();
        params.put(USERNAME_COLUMN, username);
        params.put(PASSWORD_COLUMN, password);
        return jdbcTemplate.queryForObject(
                "SELECT username, password, role FROM PUBLIC.USERS WHERE username = (:USERNAME) AND password = (:PASSWORD)",
                params,
                new UserRowMapper());
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            return User.builder()
                    .username(rs.getString(USERNAME_COLUMN))
                    .password(rs.getString(PASSWORD_COLUMN))
                    .role(RoleEnum.valueOf(rs.getString(ROLE_COLUMN))).build();
        }
    }
}
