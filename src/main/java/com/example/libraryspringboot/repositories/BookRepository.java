package com.example.libraryspringboot.repositories;

import com.example.libraryspringboot.models.Book;
import com.example.libraryspringboot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {
}
