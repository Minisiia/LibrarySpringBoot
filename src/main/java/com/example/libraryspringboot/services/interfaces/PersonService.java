package com.example.libraryspringboot.services.interfaces;

import com.example.libraryspringboot.models.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    Person findById(int id);

    Person save(Person person);

    Person update(int id, Person updatedPerson);

    void delete(int id);
}
