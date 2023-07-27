package com.example.libraryspringboot.controllers;

import com.example.libraryspringboot.dto.BookDto;
import com.example.libraryspringboot.models.Book;
import com.example.libraryspringboot.models.Person;
import com.example.libraryspringboot.services.impl.BookServiceImpl;
import com.example.libraryspringboot.services.impl.PersonServiceImpl;
import com.example.libraryspringboot.utils.BookValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books") // все сслылки будут начинаться с пипл
public class BooksController {
    private final BookServiceImpl bookService;
    private final PersonServiceImpl personService;
    private final BookValidator bookValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public BooksController(BookServiceImpl bookService, PersonServiceImpl personService, BookValidator bookValidator, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookValidator = bookValidator;
        this.modelMapper = modelMapper;
    }


    //http://localhost:8080/books?page=1&books-per-page=3
    //http://localhost:8080/books?page=1&books-per-page=3&sortBy=author
    //http://localhost:8080/books?sortBy=author
    @GetMapping()// пустой, т.к. уже есть /пипл
    public String index(Model model,
                        @RequestParam(required = false) Integer page,
                        @RequestParam(required = false, name = "books-per-page") Integer booksPerPage,
                        @RequestParam(required = false) String sortBy,
                        @RequestParam(required = false) boolean isDesc,
                        HttpServletRequest request) throws SQLException {

        model.addAttribute("httpServletRequestPaging", request);
        model.addAttribute("link_builder",buildLink(request));

        //получим всех людей из дао и передадим на отображение в вивью
        if (page != null && booksPerPage != null) {
            if (sortBy == null) {
                model.addAttribute("books", convertToBookDtoPage(bookService.getNBooksPerPage(page, booksPerPage)));
            } else if (isDesc) {
                model.addAttribute("books", convertToBookDtoPage(bookService.getDescSortesBooksPerPage(page, booksPerPage, sortBy)));
            } else {
                model.addAttribute("books", convertToBookDtoPage(bookService.getSortesBooksPerPage(page, booksPerPage, sortBy)));
            }
        } else if (page == null && booksPerPage == null && sortBy != null) {
            if (isDesc) {
                model.addAttribute("books", convertToBookDtoList(bookService.getDescSortedBooks(sortBy)));
            } else {
                model.addAttribute("books", convertToBookDtoList(bookService.getSortedBooks(sortBy)));
            }
        } else {
            model.addAttribute("books", convertToBookDtoList(bookService.findAll()));
        }
        return "books/index";//возвращаем страницу, отображающую список из людей
    }


    @GetMapping("/{id}") // в адресе передается число, которое поместиться в аргументы метода с пом анн ПасВариабл
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        //получим одного человека по айди из дао и передадим его на отображение в представление
        Book book = bookService.findById(id);
        model.addAttribute("book", convertToBookDto(book)); //под этим ключем лежит человек по айди

        if (book.getPerson() != null) {
            model.addAttribute("reader", book.getPerson());
        } else {
            model.addAttribute("people", personService.findAll());
        }
        return "books/show"; // возвращаем страницу с 1 человеком по айди
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") BookDto book) {
        return "books/new";
    }

    @PostMapping()//попадаем в метод по адресу /пипл
    public String create(@ModelAttribute("book") @Valid BookDto book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult); // в полях передаем персон
        //из формы и bindingResult, где храняться ошибки со всех валидаций

        if (bindingResult.hasErrors())//если есть ошибки
            return "books/new";
        bookService.save(convertToBook(book));
        return "redirect:/books";//переход на другую страницу после добавления человека
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", convertToBookDto(bookService.findById(id)));
        return "books/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid BookDto book,
                         BindingResult bindingResult, @PathVariable("id") int id) {


        if (bindingResult.hasErrors())//если есть ошибки
            return "books/edit";
        bookService.update(id, convertToBook(book));
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";

    }

    @PatchMapping("/{id}/unsubscribe")
    public String unsubscribe(Model model, @PathVariable("id") int id) {
        bookService.unsubscribe(id);
        model.addAttribute("book", convertToBookDto(bookService.findById(id)));
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/unsubscribe-from-person-page")
    public String unsubscribeFromPersonPage(Model model, @PathVariable("id") int id) {
        int personId = bookService.findById(id).getPerson().getId();
        bookService.unsubscribe(id);
        model.addAttribute("book", convertToBookDto(bookService.findById(id)));
        return "redirect:/people/" + personId;
    }

    @PatchMapping("/{id}/subscribe")
    public String takeBook(@PathVariable("id") int id,
                           @ModelAttribute("person") Person person) {
        bookService.subscribe(id,person);// Ваш код обработки сохранения информации о читателе для книги
        return "redirect:/books/" + id; // Возвращаем URL для редиректа после сохранения
    }

    private Book convertToBook(BookDto bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    private BookDto convertToBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    private List<BookDto> convertToBookDtoList(List<Book> books) {
        return books.stream()
                .map(this::convertToBookDto)
                .collect(Collectors.toList());
    }

    private List<BookDto> convertToBookDtoPage(Page<Book> books) {
        return books.stream()
                .map(this::convertToBookDto)
                .collect(Collectors.toList());
    }

    private String buildLink(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        StringBuilder linkBuilder = new StringBuilder(requestURI);

        if (queryString != null && !queryString.isEmpty()) {
            // Проверяем наличие параметра sortBy в queryString
            int sortByIndex = queryString.indexOf("sortBy");
            if (sortByIndex == 0) linkBuilder.append("?");
            else if (sortByIndex != -1) {
                queryString= queryString.substring(0, sortByIndex-1);
                linkBuilder.append("?").append(queryString).append("&");
            }
            else linkBuilder.append("?").append(queryString).append("&");
        } else linkBuilder.append("?");

        System.out.println(linkBuilder.toString());
        // Возвращаем сформированную ссылку
        return linkBuilder.toString();
    }



}
