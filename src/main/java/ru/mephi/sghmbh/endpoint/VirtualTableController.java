package ru.mephi.sghmbh.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.sghmbh.model.VirtualTable;
import ru.mephi.sghmbh.service.VirtualTableService;

@RestController
@RequestMapping("/table")
public class VirtualTableController {
    private VirtualTableService service;

    @Autowired
    public VirtualTableController(VirtualTableService service) {
        this.service = service;
    }

    @GetMapping("")
    public VirtualTable getById(@RequestParam(value = "id") String virtualTableId) {
        return service.getById(virtualTableId);
    }
}
