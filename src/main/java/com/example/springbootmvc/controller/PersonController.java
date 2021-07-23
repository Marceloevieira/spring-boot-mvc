package com.example.springbootmvc.controller;

import com.example.springbootmvc.dto.PersonDTO;
import com.example.springbootmvc.exception.ExceptionPersonNotFound;
import com.example.springbootmvc.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final String  BASE_PATH = "/person/";
    private final Integer PAGE_SIZE = 10;

    @GetMapping("/")
    public ModelAndView index(@RequestParam(defaultValue =  "1") Integer page,
                              @RequestParam(defaultValue = "10" ) Integer pageSize,
                              @RequestParam(defaultValue = "id" ) String order){

        var modelAndView = new ModelAndView();

        Page<PersonDTO> listPersonDTO = personService.list(page-1, pageSize, order);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, listPersonDTO.getTotalPages())
                .boxed()
                .collect(Collectors.toList());

        modelAndView.setViewName(BASE_PATH+"index");
        modelAndView.addObject("name","List of Person");
        modelAndView.addObject("pagePersonDTO",listPersonDTO);
        modelAndView.addObject("pageNumbers", pageNumbers);


        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create(){

        var modelAndView = new ModelAndView();

        modelAndView.setViewName(BASE_PATH+"show");
        modelAndView.addObject("name","Create of Person");
        modelAndView.addObject("personDTO",new PersonDTO());
        return modelAndView;
    }


    @PostMapping("/store")
    public ModelAndView store(@Valid @ModelAttribute PersonDTO personDTO, BindingResult bindingResult){

        var modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName(BASE_PATH+"show");

        }else {

            PersonDTO personDTOSaved = personService.create(personDTO);

            modelAndView = index(1,PAGE_SIZE,"id");
            modelAndView.addObject("alert"  ,"alert-success");
            modelAndView.addObject("message",String.format("Person with id %s has been created in the system",personDTOSaved.getId()));
        }


        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id){

        ModelAndView modelAndView;

        try {

            PersonDTO personDTObyId = personService.findById(id);
            modelAndView = new ModelAndView();
            modelAndView.addObject("name","Edit of Person");
            modelAndView.addObject("personDTO",personDTObyId);
            modelAndView.setViewName(BASE_PATH+"show");

        } catch (ExceptionPersonNotFound exceptionPersonNotFound) {

            modelAndView = index(1,PAGE_SIZE,"id");
            modelAndView.addObject("message",exceptionPersonNotFound.getMessage());
            modelAndView.addObject("alert"  , "alert-danger");
        }


        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid @ModelAttribute PersonDTO personDTO, BindingResult bindingResult){

            ModelAndView modelAndView;
            Map<String,Object> paramObj = new HashMap<String,Object>();


        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView();
            modelAndView.addObject("personDTO",personDTO);
            modelAndView.setViewName(BASE_PATH+"show");

        }else {

            try {

                PersonDTO update = personService.update(personDTO);
                System.out.println(update);
                paramObj.put("alert"  ,"alert-success");
                paramObj.put("message",String.format("Person with id %s has been updated in the system.",id));

            } catch (ExceptionPersonNotFound exceptionPersonNotFound) {

                paramObj.put("alert"  ,"alert-danger");
                paramObj.put("message",exceptionPersonNotFound.getMessage());
            }

            modelAndView = index(1,PAGE_SIZE,"id");
            modelAndView.addAllObjects(paramObj);

        }

        return modelAndView;
    }



    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){

        ModelAndView modelAndView;

        try {
            personService.delete(id);
            modelAndView = index(1,PAGE_SIZE,"id");
            modelAndView.addObject("alert"  ,"alert-success");
            modelAndView.addObject("message",String.format("Person with id %s has been removed from the system",id));
        } catch (ExceptionPersonNotFound exceptionPersonNotFound) {

            modelAndView = index(1,PAGE_SIZE,"id");
            modelAndView.addObject("alert"  , "alert-danger");
            modelAndView.addObject("message",exceptionPersonNotFound.getMessage());
        }

        return modelAndView;
    }
}
