package com.example.springbootmvc.mapper;

import com.example.springbootmvc.dto.PersonDTO;
import com.example.springbootmvc.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person toModel(PersonDTO personDTO);
    PersonDTO toDTO(Person person);
}
