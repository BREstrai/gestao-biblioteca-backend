package com.brestrai.template.domain.example;

import com.brestrai.utils.commons.domain.IDto;

public record ExampleDto() implements IDto<ExampleModel> {
    @Override
    public ExampleModel toModel() {
        return null;
    }
}
