package com.example.libraryspringboot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Title should not be empty")
   // @Size(min = 2, max = 30, message = "Title should be between 2 and 30 characters")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 30, message = "Author should be between 2 and 30 characters")
    private String author;

    @Min(value = 0, message = "Year should be greater than 0")
    private int year;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "taken_at")
    private LocalDateTime takenAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Transient
    private boolean isExpired;

}
