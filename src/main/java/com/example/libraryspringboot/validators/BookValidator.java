package com.example.libraryspringboot.validators;

import com.example.libraryspringboot.models.Book;
import com.example.libraryspringboot.services.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BookServiceImpl bookService;

    @Autowired
    public BookValidator(BookServiceImpl bookService) {
        this.bookService = bookService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);//тру в том случае, если класс,
        // который передается в качестве аргумента равен классу Персон
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
// посмотреть есть ли в бд человек с таким емейл
        if (bookService.findById(book.getId()) != null)// нашли ли мы человека в таблице
            errors.rejectValue("id", "", "This book is already taken");
    }
}
