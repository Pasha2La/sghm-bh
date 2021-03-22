package ru.mephi.sghmbh.service.impl;

import org.springframework.stereotype.Service;
import ru.mephi.sghmbh.service.ExampleService;

@Deprecated
@Service
public class ExampleServiceImpl implements ExampleService {
    private static final String SUCCESS = "SUCCESS";

    @Override
    public String getSuccess() {
        return SUCCESS;
    }
}
