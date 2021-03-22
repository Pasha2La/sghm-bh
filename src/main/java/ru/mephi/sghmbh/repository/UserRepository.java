package ru.mephi.sghmbh.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mephi.sghmbh.model.RoleEnum;
import ru.mephi.sghmbh.model.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final static String ID_COLUMN = "ID";
    private final static String USERNAME_COLUMN = "USERNAME";
    private final static String PASSWORD_COLUMN = "PASSWORD";
    private final static String EMAIL_COLUMN = "EMAIL";
    private final static String FIRST_NAME_COLUMN = "FIRST_NAME";
    private final static String LAST_NAME_COLUMN = "LAST_NAME";
    private final static String PATRONYMIC_COLUMN = "PATRONYMIC";
    private final static String ROLE_COLUMN = "ROLE";

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserDto getUser(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put(USERNAME_COLUMN, username);
        params.put(PASSWORD_COLUMN, password);
        return jdbcTemplate.queryForObject(
                "SELECT * FROM public.\"USERS\" " +
                        "WHERE \"USERS\".\"USERNAME\" = (:USERNAME) " +
                        "AND \"USERS\".\"PASSWORD\" = (:PASSWORD)",
                params,
                new UserRowMapper());
    }

    public UserDto getUserById(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(ID_COLUMN, userId);
        return jdbcTemplate.queryForObject(
                "SELECT * FROM public.\"USERS\" " +
                        "WHERE \"USERS\".\"ID\"::TEXT = (:ID) ",
                params,
                new UserRowMapper());
    }

    private static class UserRowMapper implements RowMapper<UserDto> {

        @Override
        public UserDto mapRow(ResultSet rs, int i) throws SQLException {
            return UserDto.builder()
                    .id(rs.getString(ID_COLUMN))
                    .username(rs.getString(USERNAME_COLUMN))
                    .password(rs.getString(PASSWORD_COLUMN))
                    .email(rs.getString(EMAIL_COLUMN))
                    .firstName(rs.getString(FIRST_NAME_COLUMN))
                    .lastName(rs.getString(LAST_NAME_COLUMN))
                    .patronymic(rs.getString(PATRONYMIC_COLUMN))
                    .role(RoleEnum.valueOf(rs.getString(ROLE_COLUMN))).build();
        }
    }
}
