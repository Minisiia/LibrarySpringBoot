package com.example.libraryspringboot.services.impl;

import com.example.libraryspringboot.models.Book;
import com.example.libraryspringboot.models.Person;
import com.example.libraryspringboot.repositories.BookRepository;
import com.example.libraryspringboot.repositories.PersonRepository;
import com.example.libraryspringboot.services.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
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

    public Book update(int id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow();
        updatedBook.setId(book.getId());
        return bookRepository.save(updatedBook);
    }

    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public void unsubscribe(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        assert book != null;
        book.setPerson(null);
        bookRepository.save(book);
        returnBook(book);
    }

    public void subscribe(int id, Person person) {
        Book book = bookRepository.findById(id).orElse(null);
        assert book != null;
        book.setPerson(person);
        bookRepository.save(book);
        takeBook(book);
    }

    private void takeBook(Book book) {
        book.setTakenAt(LocalDateTime.now());
        book.setReturnedAt(null);
    }

    private void returnBook(Book book) {
        book.setReturnedAt(LocalDateTime.now());
    }

    public Page<Book> getNBooksPerPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAll(pageable);
    }

    public List<Book> getSortedBooks(String sortBy) {
        return bookRepository.findAll(Sort.by(sortBy));
    }

    public List<Book> getDescSortedBooks(String sortBy) {
        return bookRepository.findAll(Sort.by(Sort.Direction.DESC,sortBy));
    }

    public Page<Book> getSortesBooksPerPage(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return bookRepository.findAll(pageable);
    }

    public Page<Book> getDescSortesBooksPerPage(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC,sortBy));
        return bookRepository.findAll(pageable);
    }

    public int getTotalBooksCount() {
        return bookRepository.findAll().size();
    }

    public List<Book> findByTitleContains(String searchQuery) {
        return bookRepository.findByTitleContains(searchQuery);
    }

    public boolean isBookExpired (Book book) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (book.getTakenAt() != null) {
            Duration duration = Duration.between(book.getTakenAt(), currentTime);
            if (book.getReturnedAt() != null) {
                System.out.println("book.getReturnedAt() != null");
                return false;
            } else if (duration.getSeconds() < 30) {
                System.out.println(duration.getSeconds());
                return false;
            }

            System.out.println(duration.getSeconds());
            return true;
        }
        return false;
    }
}
