package com.brestrai.template.service;

import com.brestrai.utils.template.commons.service.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExampleService extends AbstractService {

    public void someMethod() {
        // Implement method functionality
        log.info("Executing someMethod in ConcreteClass");
    }

    public static void main(String[] args) {
        ExampleService exampleService = new ExampleService();
        exampleService.someMethod();
    }
}
