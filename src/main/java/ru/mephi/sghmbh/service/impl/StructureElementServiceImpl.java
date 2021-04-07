package ru.mephi.sghmbh.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import ru.mephi.sghmbh.mapper.StructureElementMapper;
import ru.mephi.sghmbh.model.StructureElement;
import ru.mephi.sghmbh.model.dto.StructureElementDto;
import ru.mephi.sghmbh.repository.StructureElementRepository;
import ru.mephi.sghmbh.service.StructureElementService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StructureElementServiceImpl implements StructureElementService {
    private final StructureElementMapper mapper = Mappers.getMapper(StructureElementMapper.class);

    private StructureElementRepository repository;

    @Autowired
    public StructureElementServiceImpl(StructureElementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<StructureElement> getByVirtualTableId(String virtualTableId) {
        if (virtualTableId == null || virtualTableId.isEmpty()) {
            return null;
        }

        List<StructureElementDto> children = repository.getChildVirtualTableId(virtualTableId);
        List<StructureElementDto> roots = repository.getRootVirtualTableId(virtualTableId);
        List<StructureElement> result = new ArrayList<>();
        for (StructureElementDto root : roots) {
            result.add(formTree(root, children));
        }
        return result;
    }

    @Override
    public void insertRootWithChildrenElement(List<StructureElement> element, String virtualTableId) {
        repository.insertRootElements(element, virtualTableId);
    }

    @Override
    public boolean deleteByVirtualTableId(String virtualTableId) {
        return repository.deleteByVirtualTableId(virtualTableId);
    }

    private StructureElement formTree(StructureElementDto currentElement, List<StructureElementDto> unattachedChildren) {
        if (CollectionUtils.isEmpty(unattachedChildren) || ObjectUtils.isEmpty(currentElement)) {
            return mapper.fromDto(currentElement);
        }
        StructureElement result = mapper.fromDto(currentElement);
        result.setChildren(new ArrayList<>());

        //собираем из списка ничейных дочерних элементов дочерние для текущего элемента дерева
        List<StructureElementDto> currentChildren = unattachedChildren.stream()
                .filter(child ->
                        currentElement.getId().equals(child.getParentId()))
                .collect(Collectors.toList());

        //если таких нет - просто возвращаем на выход текущий элемент
        if (CollectionUtils.isEmpty(currentChildren)) {
            return result;
        }

        //вычитаем из списка ничейных дочерних элементов дочерние для текущего элемента дерева
        unattachedChildren.removeAll(currentChildren);

        //заполняем дочерние элементы для текущего элемента дерева
        for (StructureElementDto elementDto : currentChildren) {
            result.getChildren().add(formTree(elementDto, unattachedChildren));
        }
        return result;
    }
}
