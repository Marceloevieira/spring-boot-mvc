package com.example.springbootmvc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {


    private Long id;

    @NotNull
    @Size(min = 1,max = 120)
    private String name;

    @NotNull
    @Max(150)
    private Integer age;

}
