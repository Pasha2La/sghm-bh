package ru.mephi.sghmbh.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.sghmbh.mapper.VirtualTableMapper;
import ru.mephi.sghmbh.model.VirtualTable;
import ru.mephi.sghmbh.repository.VirtualTableRepository;
import ru.mephi.sghmbh.service.StructureElementService;
import ru.mephi.sghmbh.service.VirtualTableService;

@Service
public class VirtualTableServiceImpl implements VirtualTableService {
    private final VirtualTableMapper mapper = Mappers.getMapper(VirtualTableMapper.class);

    private VirtualTableRepository repository;
    private StructureElementService structureElementService;

    @Autowired
    public VirtualTableServiceImpl(VirtualTableRepository repository, StructureElementService structureElementService) {
        this.repository = repository;
        this.structureElementService = structureElementService;
    }

    @Override
    public VirtualTable getById(String virtualTableId) {
        VirtualTable table = mapper.fromDto(repository.getById(virtualTableId));
        table.setFolderStructure(structureElementService.getByVirtualTableId(virtualTableId));
        return table;
    }
}
