package com.brestrai.template.service.example;

import com.brestrai.utils.commons.service.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExampleService extends AbstractService {

    public void someMethod() {

        log.info("Executing someMethod in ConcreteClass");
    }
}
