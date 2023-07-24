package com.example.libraryspringboot.repositories;

import com.example.libraryspringboot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Integer> {
}
