package com.example.libraryspringboot.services.impl;

import com.example.libraryspringboot.models.Book;
import com.example.libraryspringboot.models.Person;
import com.example.libraryspringboot.repositories.BookRepository;
import com.example.libraryspringboot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl {
    private final BookRepository bookRepository;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update (int id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow();
        updatedBook.setId(book.getId());
        return bookRepository.save(updatedBook);
    }

    public void delete (int id) {
        bookRepository.deleteById(id);
    }

    public void unsubscribe(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        assert book != null;
        book.setPerson(null);
        bookRepository.save(book);
    }
}
