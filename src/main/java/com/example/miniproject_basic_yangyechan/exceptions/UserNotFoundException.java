package com.example.miniproject_basic_yangyechan.exceptions;

public class UserNotFoundException extends Status404Exception {
    public UserNotFoundException() {
        super("target user not found");
    }
}
