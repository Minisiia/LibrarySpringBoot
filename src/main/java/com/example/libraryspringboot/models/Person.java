package com.example.libraryspringboot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Date;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Min(value = 1923, message = "Year of birth should be greater than 1923")
    @Max(value = 2023, message = "Year of birth should be less than 2023")
    private int year;
    @OneToMany (mappedBy = "person", fetch = FetchType.EAGER)
    //@JoinColumn(name = "person_id")
    private List<Book> bookList;

}

