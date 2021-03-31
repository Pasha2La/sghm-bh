package ru.mephi.sghmbh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDto {
    private String id;
    private String name;
    private String organization;
    private Date creationDate;
    private Date modifiedDate;
}
