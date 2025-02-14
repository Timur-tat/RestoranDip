package com.diplom.restoran.exeption;

public class DishNotFoundException extends Throwable {
    public DishNotFoundException(String message) {
        super(message);
    }
}
