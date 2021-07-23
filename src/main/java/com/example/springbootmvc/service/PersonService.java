package com.example.springbootmvc.service;

import com.example.springbootmvc.dto.PersonDTO;
import com.example.springbootmvc.exception.ExceptionPersonNotFound;
import com.example.springbootmvc.mapper.PersonMapper;
import com.example.springbootmvc.model.Person;
import com.example.springbootmvc.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;


    public Page<PersonDTO> list(Integer page ,Integer size, String order){

        Pageable pageRequest = PageRequest.of(page,size, Sort.by(order).ascending());

        return personRepository.findAll(pageRequest)
                .map(personMapper::toDTO);
    }

    public PersonDTO create(PersonDTO personDTO){

        Person personToSave = personMapper.toModel(personDTO);

        Person personSave = personRepository.save(personToSave);

        return personMapper.toDTO(personSave);
    }


    public void delete(Long id) throws ExceptionPersonNotFound {

        Person personById = verifyExistPersonById(id);

        personRepository.delete(personById);
    }


    public PersonDTO findById(Long id) throws ExceptionPersonNotFound {

        Person personById = verifyExistPersonById(id);
        return personMapper.toDTO(personById);
    }

    private Person verifyExistPersonById(Long id) throws ExceptionPersonNotFound {
        return personRepository.findById(id).orElseThrow(() -> new ExceptionPersonNotFound(id));
    }

    public PersonDTO update( PersonDTO personDTO) throws ExceptionPersonNotFound {

        Person personToSave = personMapper.toModel(personDTO);

        verifyExistPersonById(personToSave.getId());

        return personMapper.toDTO(personRepository.save(personToSave));
    }
}
