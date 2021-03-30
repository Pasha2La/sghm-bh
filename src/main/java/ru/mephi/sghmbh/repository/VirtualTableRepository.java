package ru.mephi.sghmbh.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mephi.sghmbh.model.RoleEnum;
import ru.mephi.sghmbh.model.dto.VirtualTableDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class VirtualTableRepository {
    private final static String ID_COLUMN = "ID";
    private final static String NAME_COLUMN = "NAME";
    private final static String ORGANIZATION_COLUMN = "ORGANIZATION";
    private final static String CREATION_DATE_COLUMN = "CREATION_DATE";
    private final static String MODIFIED_DATE_COLUMN = "MODIFIED_DATE";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private StructureElementRepository structureElementRepository;

    @Autowired
    public VirtualTableRepository(NamedParameterJdbcTemplate jdbcTemplate,
                                  StructureElementRepository structureElementRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.structureElementRepository = structureElementRepository;
    }

    private static class VirtualTableRowMapper implements RowMapper<VirtualTableDto> {

        @Override
        public VirtualTableDto mapRow(ResultSet rs, int i) throws SQLException {
            return VirtualTableDto.builder()
                    .id(rs.getString(ID_COLUMN))
                    .name(rs.getString(NAME_COLUMN))
                    .organization(rs.getString(ORGANIZATION_COLUMN))
                    .creationDate(rs.getTimestamp(CREATION_DATE_COLUMN))
                    .modifiedDate(rs.getTimestamp(MODIFIED_DATE_COLUMN)).build();
        }
    }
}
