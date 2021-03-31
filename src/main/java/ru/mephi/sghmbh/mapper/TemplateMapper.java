package ru.mephi.sghmbh.mapper;

import org.mapstruct.Mapper;
import ru.mephi.sghmbh.model.Template;
import ru.mephi.sghmbh.model.dto.TemplateDto;

@Mapper
public interface TemplateMapper {
    Template fromDto(TemplateDto templateDto);

    TemplateDto toDto(Template template);
}
