package com.dins.internship.task.phonebook.exceptions;

public class PhonebookException extends Exception {
    public PhonebookException(String message) {
        super(message);
    }

    public PhonebookException(Throwable cause) {
        super(cause);
    }

    public PhonebookException(String message, Throwable cause) {
        super(message, cause);
    }
}
