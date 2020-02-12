package com.findme.Exceptions;

public class BadRequestException extends Exception {
    public BadRequestException(String text) {
        super(text);
    }
}
