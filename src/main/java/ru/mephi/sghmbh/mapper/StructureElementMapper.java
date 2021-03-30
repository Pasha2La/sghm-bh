package ru.mephi.sghmbh.mapper;

import org.mapstruct.Mapper;
import ru.mephi.sghmbh.model.StructureElement;
import ru.mephi.sghmbh.model.dto.StructureElementDto;

@Mapper
public interface StructureElementMapper {
    StructureElement fromDto(StructureElementDto structureElementDto);

    StructureElementDto toDto(StructureElement structureElement);
}
