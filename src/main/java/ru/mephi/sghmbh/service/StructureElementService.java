package ru.mephi.sghmbh.service;

import ru.mephi.sghmbh.model.StructureElement;

import java.util.List;

public interface StructureElementService {
    List<StructureElement> getByVirtualTableId(String virtualTableId);
    boolean deleteByVirtualTableId(String virtualTableId);
    void insertRootWithChildrenElement(List<StructureElement> element, String virtualTableId);
}
