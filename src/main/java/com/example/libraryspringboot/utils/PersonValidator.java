package com.example.libraryspringboot.utils;

import com.example.libraryspringboot.models.Person;
import com.example.libraryspringboot.services.impl.PersonServiceImpl;
import com.example.libraryspringboot.services.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonServiceImpl personService;

    @Autowired
    public PersonValidator(PersonServiceImpl personService) {
        this.personService = personService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);//тру в том случае, если класс,
        // который передается в качестве аргумента равен классу Персон
    }

    @Override
    public void validate(Object o, Errors errors) {
//        Person person = (Person) o;
//// посмотреть есть ли в бд человек с таким емейл
//        if (personService.findById(person.getId())!= null)// нашли ли мы человека в таблице
//            errors.rejectValue("name", "", "This person is already taken");
    }
}
