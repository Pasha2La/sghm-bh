package ru.mephi.sghmbh.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mephi.sghmbh.model.Template;
import ru.mephi.sghmbh.service.TemplateService;

@RestController
@RequestMapping("/template")
public class TemplateController {
    private TemplateService service;

    @Autowired
    public TemplateController(TemplateService service) {
        this.service = service;
    }

    @GetMapping("")
    public Template getTemplate() {
        return service.getTemplate();
    }

    @PostMapping("")
    public void updateTemplate(@RequestBody Template request) {
        service.updateTemplate(request);
    }
}