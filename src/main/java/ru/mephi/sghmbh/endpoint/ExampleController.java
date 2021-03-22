package ru.mephi.sghmbh.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.sghmbh.model.ExampleModel;
import ru.mephi.sghmbh.service.ExampleService;

@Deprecated
@RestController
@RequestMapping("/example")
public class ExampleController {

    private ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/simple-get")
    public String exampleGet(@RequestParam(value = "name") String name) {
        return String.format("HELLO, %s!", name);
    }

    @PostMapping("/simple-post")
    public String examplePost(@RequestBody ExampleModel model) {
        return String.format("Received name: %s", model.getName());
    }

    @GetMapping("/service")
    public String exampleWithService() {
        return exampleService.getSuccess();
    }
}
