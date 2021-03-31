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
public class Template {
    private String name;
    private String organization;
    private Date creationDate;
    private Date modifiedDate;
    private List<StructureElement> folderStructure;
}
