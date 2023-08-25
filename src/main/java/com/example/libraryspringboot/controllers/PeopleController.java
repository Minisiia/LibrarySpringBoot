package com.example.libraryspringboot.controllers;

import com.example.libraryspringboot.models.Book;
import com.example.libraryspringboot.models.Person;
import com.example.libraryspringboot.services.impl.BookServiceImpl;
import com.example.libraryspringboot.services.impl.PersonServiceImpl;
import com.example.libraryspringboot.validators.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/people") // все сслылки будут начинаться с пипл
public class PeopleController {
    private final PersonServiceImpl personService;
    private final BookServiceImpl bookService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonServiceImpl personService, BookServiceImpl bookService, PersonValidator personValidator) {
        this.personService = personService;
        this.bookService = bookService;
        this.personValidator = personValidator;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()// пустой, т.к. уже есть /пипл
    public String index(Model model) throws SQLException { // в моделе будем передавать
        //получим всех людей из дао и передадим на отображение в вивью
        model.addAttribute("people", personService.findAll()); //под ключем пипл лежит список людей
        HomeController.authenticate(model);
        return "people/index";//возвращаем страницу, отображающую список из людей
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}") // в адресе передается число, которое поместиться в аргументы метода с пом анн ПасВариабл
    public String show(@PathVariable("id") int id, Model model) {
        //получим одного человека по айди из дао и передадим его на отображение в представление
        model.addAttribute("person", personService.findById(id)); //под этим ключем лежит человек по айди
        List<Book> books = personService.findById(id).getBookList();

        // Проверяем каждую книгу на просрочку и устанавливаем isExpired соответствующим образом
        for (Book book : books) {
            boolean flag = bookService.isBookExpired(book);
            book.setExpired(flag);
        }
        model.addAttribute("books", books);
        HomeController.authenticate(model);
        return "people/show"; // возвращаем страницу с 1 человеком по айди
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping()//попадаем в метод по адресу /пипл
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult); // в полях передаем персон
        //из формы и bindingResult, где храняться ошибки со всех валидаций

        if (bindingResult.hasErrors())//если есть ошибки
            return "people/new";
        personService.save(person);
        return "redirect:/people";//переход на другую страницу после добавления человека
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.findById(id));
        return "people/edit";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())//если есть ошибки
            return "people/edit";
        personService.update(id, person);
        return "redirect:/people/{id}";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";

    }
}
