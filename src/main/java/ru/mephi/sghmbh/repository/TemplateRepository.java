package ru.mephi.sghmbh.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mephi.sghmbh.model.dto.TemplateDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class TemplateRepository {
    private final static String ID_COLUMN = "ID";
    private final static String NAME_COLUMN = "NAME";
    private final static String ORGANIZATION_COLUMN = "ORGANIZATION";
    private final static String CREATION_DATE_COLUMN = "CREATION_DATE";
    private final static String MODIFIED_DATE_COLUMN = "MODIFIED_DATE";

    private final static String TEMPLATE_NAME = "template";

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TemplateRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TemplateDto getTemplate() {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM public.\"VIRTUAL_TABLES\" " +
                        "WHERE \"VIRTUAL_TABLES\".\"NAME\" = (:NAME)",
                Collections.singletonMap(NAME_COLUMN, TEMPLATE_NAME),
                new TemplateRowMapper());
    }

    public void deleteTemplate() {
        jdbcTemplate.update(
                "DELETE FROM public.\"VIRTUAL_TABLES\" " +
                        "WHERE \"VIRTUAL_TABLES\".\"NAME\" = (:NAME)",
                Collections.singletonMap(NAME_COLUMN, TEMPLATE_NAME));
    }

    public void createTemplate() {
        Map<String, Object> params = new HashMap<>();
        UUID id = UUID.randomUUID();
        Date currentDate = new Date();

        params.put(ID_COLUMN, id);
        params.put(NAME_COLUMN, TEMPLATE_NAME);
        params.put(CREATION_DATE_COLUMN, currentDate);
        params.put(MODIFIED_DATE_COLUMN, currentDate);
        params.put(ORGANIZATION_COLUMN, "all");

        jdbcTemplate.update("INSERT INTO \"VIRTUAL_TABLES\" (\"ID\", \"NAME\", \"CREATION_DATE\", \"MODIFIED_DATE\", " +
                "\"ORGANIZATION\") VALUES ((:ID), (:NAME), (:CREATION_DATE), (:MODIFIED_DATE), (:ORGANIZATION))", params);


    }

    private static class TemplateRowMapper implements RowMapper<TemplateDto> {

        @Override
        public TemplateDto mapRow(ResultSet rs, int i) throws SQLException {
            return TemplateDto.builder()
                    .id(rs.getString(ID_COLUMN))
                    .name(rs.getString(NAME_COLUMN))
                    .organization(rs.getString(ORGANIZATION_COLUMN))
                    .creationDate(rs.getTimestamp(CREATION_DATE_COLUMN))
                    .modifiedDate(rs.getTimestamp(MODIFIED_DATE_COLUMN)).build();
        }
    }
}
