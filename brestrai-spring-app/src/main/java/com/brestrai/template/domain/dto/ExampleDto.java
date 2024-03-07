package com.brestrai.template.domain.dto;

import com.brestrai.template.domain.model.ExampleModel;
import com.brestrai.utils.template.commons.domain.IDto;

public record ExampleDto() implements IDto<ExampleModel> {
    @Override
    public ExampleModel toModel() {
        return null;
    }
}
