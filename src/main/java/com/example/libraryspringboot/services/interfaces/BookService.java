package com.example.libraryspringboot.services.interfaces;

import com.example.libraryspringboot.models.Book;
import com.example.libraryspringboot.models.Person;
import com.example.libraryspringboot.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(int id) ;

    Book save(Book book) ;

     Book update (int id, Book updatedBook) ;

    void delete (int id) ;

    void unsubscribe(int id);

    void subscribe(int id, Person person);


    Page<Book> getNBooksPerPage(int pageNumber, int pageSize);

    List<Book> getSortedBooks(String sortBy);

    Page<Book> getSortesBooksPerPage(int pageNumber, int pageSize, String sortBy);
}
