package ru.mephi.sghmbh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StructureElement {
    private String name;
    private String virtualTableId;
    private List<StructureElement> children;
    private String parentElementId;
    private Date creationDate;
    private Date modifiedDate;
}
