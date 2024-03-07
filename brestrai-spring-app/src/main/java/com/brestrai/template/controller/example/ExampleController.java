package com.brestrai.template.controller.example;

import com.brestrai.template.service.example.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @RequestMapping("/someMethod")
    public void someMethod() {
        exampleService.someMethod();
    }
}
