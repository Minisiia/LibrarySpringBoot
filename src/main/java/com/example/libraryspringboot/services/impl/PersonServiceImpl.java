package com.example.libraryspringboot.services.impl;

import com.example.libraryspringboot.models.Person;
import com.example.libraryspringboot.repositories.PersonRepository;
import com.example.libraryspringboot.services.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
@Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
    return personRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> foundPerson = personRepository.findById(id);
    return foundPerson.orElse(null);
    }

    public Person save(Person person) {
    return personRepository.save(person);
    }

    public Person update (int id, Person updatedPerson) {
    Person person = personRepository.findById(id).orElseThrow();
    updatedPerson.setId(person.getId());
    return personRepository.save(updatedPerson);
    }

    public void delete (int id) {
    personRepository.deleteById(id);
    }
}
