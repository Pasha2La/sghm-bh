package ru.mephi.sghmbh.service;


import ru.mephi.sghmbh.model.Template;

public interface TemplateService {
    Template getTemplate();
    void updateTemplate(Template template);
    void deleteTemplate();
}
