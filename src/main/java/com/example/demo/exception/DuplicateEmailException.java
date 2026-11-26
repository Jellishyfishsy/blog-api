package com.example.demo.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email){
        super("email đã tồn tại: "+ email);
    }
}
