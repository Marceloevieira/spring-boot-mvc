package com.example.springbootmvc.seed;

import com.example.springbootmvc.model.Person;
import com.example.springbootmvc.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDataLoader  implements CommandLineRunner {

    @Autowired
    PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {
            loadPersonData();
    }

    private void loadPersonData() {

        if(personRepository.count() == 0){

            List<Person> personList = new ArrayList<Person>();

            personList.add(new Person(1L,"Marcelo",28));
            personList.add(new Person(2L,"Clarissa",29));
            personList.add(new Person(3L,"Vilmar",29));
            personList.add(new Person(4L,"Gilsa",29));
            personList.add(new Person(5L,"Matheus",29));
            personList.add(new Person(6L,"Davi",29));
            personList.add(new Person(6L,"Rengar",29));
            personList.add(new Person(6L,"Horacio",29));
            personList.add(new Person(6L,"Yumi",29));

            personRepository.saveAll(personList);


        }
    }
}
