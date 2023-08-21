package com.example.libraryspringboot.repositories;

import com.example.libraryspringboot.models.Book;
import com.example.libraryspringboot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    public List<Book> findByTitleContains(String searchQuery);
}
