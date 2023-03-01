package com.example.springboothospital.exception;

public class BadRequestExseption extends RuntimeException{
    public BadRequestExseption() {
        super();
    }

    public BadRequestExseption(String message) {
        super(message);
    }
}
