package com.example.libraryspringboot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "security")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "username")
    private String username;
    @Size(min = 6, message = "Password must contain at least 6 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "The password must contain at least one uppercase letter, one lowercase letter and one digit")
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

    @OneToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

}