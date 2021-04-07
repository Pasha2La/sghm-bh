package ru.mephi.sghmbh.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.sghmbh.mapper.TemplateMapper;
import ru.mephi.sghmbh.model.Template;
import ru.mephi.sghmbh.model.dto.TemplateDto;
import ru.mephi.sghmbh.repository.TemplateRepository;
import ru.mephi.sghmbh.service.StructureElementService;
import ru.mephi.sghmbh.service.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService {
    private final TemplateMapper mapper = Mappers.getMapper(TemplateMapper.class);

    private TemplateRepository repository;
    private StructureElementService structureElementService;

    @Autowired
    public TemplateServiceImpl(TemplateRepository repository, StructureElementService structureElementService) {
        this.repository = repository;
        this.structureElementService = structureElementService;
    }

    @Override
    public Template getTemplate() {
        TemplateDto templateDto = repository.getTemplate();
        Template template = mapper.fromDto(templateDto);
        template.setFolderStructure(structureElementService.getByVirtualTableId(templateDto.getId()));
        return template;
    }

    @Override
    public void updateTemplate(Template template) {
        String templateId = repository.getTemplate().getId();
        // удаляем шаблон ко всем чертям, да я знаю, Паша, ты меня убьешь!
        // а еще убьешь за отсутствие нормального логгера ((
        if (!structureElementService.deleteByVirtualTableId(templateId)) {
            System.out.println("Ошибка при удалении шаблона виртуального стола");
        } else {
            System.out.println("Процесс удаления шаблона виртуального стола запущен...");
        }
    }
}
