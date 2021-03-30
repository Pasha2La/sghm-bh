package ru.mephi.sghmbh.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mephi.sghmbh.model.dto.StructureElementDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StructureElementRepository {
    private final static String ID_COLUMN = "ID";
    private final static String NAME_COLUMN = "NAME";
    private final static String VIRTUAL_TABLE_ID_COLUMN = "VIRTUAL_TABLE_ID";
    private final static String PARENT_ID_COLUMN = "PARENT_ID";
    private final static String CHILD_ID_COLUMN = "CHILD_ID";
    private final static String CREATION_DATE_COLUMN = "CREATION_DATE";
    private final static String MODIFIED_DATE_COLUMN = "MODIFIED_DATE";
    private final static String IS_ROOT_COLUMN = "IS_ROOT";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public StructureElementRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<StructureElementDto> getByVirtualTableId(String virtualTableId) {
        Map<String, String> params = new HashMap<>();
        params.put(VIRTUAL_TABLE_ID_COLUMN, virtualTableId);
        return jdbcTemplate.query(
                "SELECT * FROM public.\"STRUCTURE_ELEMENTS\"" +
                        "WHERE \"STRUCTURE_ELEMENTS\".\"VIRTUAL_TABLE_ID\"::TEXT = (:VIRTUAL_TABLE_ID)",
                params,
                new StructureElementWithLinksRowMapper());
    }

    public List<StructureElementDto> getRootVirtualTableId(String virtualTableId) {
        return jdbcTemplate.query(
                "SELECT * FROM \"STRUCTURE_ELEMENTS\"" +
                        "WHERE \"STRUCTURE_ELEMENTS\".\"VIRTUAL_TABLE_ID\"::TEXT = (:VIRTUAL_TABLE_ID)" +
                        "AND \"STRUCTURE_ELEMENTS\".\"IS_ROOT\" = 'Y'",
                Collections.singletonMap(VIRTUAL_TABLE_ID_COLUMN, virtualTableId),
                new StructureElementRowMapper());
    }

    public List<StructureElementDto> getChildVirtualTableId(String virtualTableId) {
        return jdbcTemplate.query(
                "SELECT * FROM \"STRUCTURE_ELEMENTS\" JOIN \"STRUCTURE_ELEMENT_LINKS\"" +
                        "ON \"STRUCTURE_ELEMENTS\".\"ID\"::TEXT = \"STRUCTURE_ELEMENT_LINKS\".\"CHILD_ID\"::TEXT " +
                        "WHERE \"STRUCTURE_ELEMENTS\".\"VIRTUAL_TABLE_ID\"::TEXT = (:VIRTUAL_TABLE_ID)" +
                        "AND \"STRUCTURE_ELEMENTS\".\"IS_ROOT\" = 'N'",
                Collections.singletonMap(VIRTUAL_TABLE_ID_COLUMN, virtualTableId),
                new StructureElementWithLinksRowMapper());
    }

    private static class StructureElementWithLinksRowMapper implements RowMapper<StructureElementDto> {

        @Override
        public StructureElementDto mapRow(ResultSet rs, int i) throws SQLException {
            return StructureElementDto.builder()
                    .id(rs.getString(ID_COLUMN))
                    .name(rs.getString(NAME_COLUMN))
                    .virtualTableId(rs.getString(VIRTUAL_TABLE_ID_COLUMN))
                    .childId(rs.getString(CHILD_ID_COLUMN))
                    .parentId(rs.getString(PARENT_ID_COLUMN))
                    .creationDate(rs.getTimestamp(CREATION_DATE_COLUMN))
                    .modifiedDate(rs.getTimestamp(MODIFIED_DATE_COLUMN))
                    .root(isRoot(rs.getString(IS_ROOT_COLUMN))).build();
        }
    }

    private static class StructureElementRowMapper implements RowMapper<StructureElementDto> {

        @Override
        public StructureElementDto mapRow(ResultSet rs, int i) throws SQLException {
            return StructureElementDto.builder()
                    .id(rs.getString(ID_COLUMN))
                    .name(rs.getString(NAME_COLUMN))
                    .virtualTableId(rs.getString(VIRTUAL_TABLE_ID_COLUMN))
                    .creationDate(rs.getTimestamp(CREATION_DATE_COLUMN))
                    .modifiedDate(rs.getTimestamp(MODIFIED_DATE_COLUMN))
                    .root(isRoot(rs.getString(IS_ROOT_COLUMN))).build();
        }
    }

    private static Boolean isRoot(String value) {
        if ("Y".equals(value)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
