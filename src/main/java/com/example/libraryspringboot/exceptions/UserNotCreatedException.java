package com.example.libraryspringboot.exceptions;

public class UserNotCreatedException extends RuntimeException{
    public UserNotCreatedException(String msg) {
        super(msg);
    }
}
