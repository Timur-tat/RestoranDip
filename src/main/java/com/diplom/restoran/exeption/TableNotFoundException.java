package com.diplom.restoran.exeption;

public class TableNotFoundException extends Throwable {
    public TableNotFoundException(String message) {
        super(message);
    }
}
