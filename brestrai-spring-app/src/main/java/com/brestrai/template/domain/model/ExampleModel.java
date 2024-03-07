package com.brestrai.template.domain.model;

import com.brestrai.template.domain.dto.ExampleDto;
import com.brestrai.utils.template.commons.domain.IModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExampleModel implements IModel<ExampleDto> {


    @Override
    public ExampleDto toDto() {
        return null;
    }
}
