package ru.mephi.sghmbh.mapper;

import org.mapstruct.Mapper;
import ru.mephi.sghmbh.model.VirtualTable;
import ru.mephi.sghmbh.model.dto.VirtualTableDto;

@Mapper
public interface VirtualTableMapper {
    VirtualTable fromDto(VirtualTableDto virtualTableDto);

    VirtualTableDto toDto(VirtualTable virtualTable);
}
