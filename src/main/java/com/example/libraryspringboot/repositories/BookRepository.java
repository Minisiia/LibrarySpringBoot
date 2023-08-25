package com.example.libraryspringboot.repositories;

import com.example.libraryspringboot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByTitleContains(String searchQuery);
}
