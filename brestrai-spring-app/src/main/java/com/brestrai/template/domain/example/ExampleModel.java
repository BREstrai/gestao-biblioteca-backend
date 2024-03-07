package com.brestrai.template.domain.example;

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

    private Long id;

    @Override
    public ExampleDto toDto() {
        return null;
    }
}
