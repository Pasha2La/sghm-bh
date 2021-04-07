package ru.mephi.sghmbh.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mephi.sghmbh.model.StructureElement;
import ru.mephi.sghmbh.model.dto.StructureElementDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
                new StructureElementRowMapper());
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

    public boolean deleteByVirtualTableId(String virtualTableId) {
        List<StructureElementDto> elements = getByVirtualTableId(virtualTableId);
        for (StructureElementDto element: elements) {
            deleteById(element.getId());
            deleteByIdFromTableLinks(element.getId());
        }
        return true;
    }

    private boolean deleteById(String structureElementId) {
        Map<String, String> params = new HashMap<>();
        params.put(ID_COLUMN, structureElementId);
        return jdbcTemplate.update("DELETE FROM \"STRUCTURE_ELEMENTS\"" +
                "WHERE \"STRUCTURE_ELEMENTS\".\"ID\"::TEXT = (:ID)", params) == 1;
    }

    private boolean deleteByIdFromTableLinks(String structureElementId) {
        Map<String, String> params = new HashMap<>();

        params.put(PARENT_ID_COLUMN, structureElementId);
        params.put(CHILD_ID_COLUMN, structureElementId);
        return jdbcTemplate.update("DELETE FROM \"STRUCTURE_ELEMENT_LINKS\"" +
                "WHERE \"STRUCTURE_ELEMENT_LINKS\".\"CHILD_ID\"::TEXT = (:CHILD_ID) OR " +
                        "\"STRUCTURE_ELEMENT_LINKS\".\"PARENT_ID\"::TEXT = (:PARENT_ID)",
                params) == 1;
    }

    public void insertRootElements(List<StructureElement> elements, String virtualTableId) {
        for (StructureElement element: elements) {
            UUID id = insertElement(element, virtualTableId, "Y");
            if (element.getChildren() != null) {
                for (StructureElement child : element.getChildren()) {
                    recourseAddChildElements(child, id, virtualTableId);
                }
            }
        }
    }

    public UUID insertElement(StructureElement element, String virtualTableId, String isRoot) {
        Map<String, Object> params = new HashMap<>();
        UUID id = UUID.randomUUID();
        UUID virtualTableIdUUID = UUID.fromString(virtualTableId);
        params.put(ID_COLUMN, id);
        params.put(NAME_COLUMN, element.getName());
        params.put(CREATION_DATE_COLUMN, element.getCreationDate());
        params.put(MODIFIED_DATE_COLUMN, element.getModifiedDate());
        params.put(VIRTUAL_TABLE_ID_COLUMN, virtualTableIdUUID);
        params.put(IS_ROOT_COLUMN, isRoot);
        jdbcTemplate.update("INSERT INTO \"STRUCTURE_ELEMENTS\"(\"ID\", \"NAME\", \"CREATION_DATE\", " +
                "\"MODIFIED_DATE\", \"VIRTUAL_TABLE_ID\", \"IS_ROOT\") VALUES ((:ID), (:NAME)," +
                " (:CREATION_DATE), (:MODIFIED_DATE), (:VIRTUAL_TABLE_ID), (:IS_ROOT))", params);

        return id;
    }

    public void insertLinkBetweenElements(UUID parentId, UUID childId) {
        Map<String, Object> params = new HashMap<>();
        UUID id = UUID.randomUUID();
        params.put(ID_COLUMN, id);
        params.put(PARENT_ID_COLUMN, parentId);
        params.put(CHILD_ID_COLUMN, childId);
        jdbcTemplate.update("INSERT INTO \"STRUCTURE_ELEMENT_LINKS\"(" +
                "\"ID\", \"PARENT_ID\", \"CHILD_ID\") VALUES ((:ID), (:PARENT_ID), (:CHILD_ID))",
                params);
    }

    private void recourseAddChildElements(StructureElement element, UUID parentId, String virtualTableId) {
        UUID thisId = insertElement(element, virtualTableId, "N");
        List<StructureElement> children = element.getChildren();
        if (children == null) {
            insertLinkBetweenElements(parentId, thisId);
        } else {
            for (StructureElement child: children) {
                recourseAddChildElements(child, thisId, virtualTableId);
            }
            insertLinkBetweenElements(parentId, thisId);
        }
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
