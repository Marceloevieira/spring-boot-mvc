package com.example.springbootmvc.resource;

import com.example.springbootmvc.dto.PersonDTO;
import com.example.springbootmvc.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonResource {


    PersonService personService;

    @GetMapping("/api/v1/person")
    public Page<PersonDTO> list(@RequestParam(defaultValue = "0")  Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "id") String order)    {


      return  personService.list(page,pageSize,order);


    }

}
