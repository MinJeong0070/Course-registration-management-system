package org.example.deu_courseregistration.exception;

public class CustomEnrollmentException extends RuntimeException {
    public CustomEnrollmentException(String message) {
        super(message);
        System.out.println("CustomEnrollmentException로 넘어온 메세지: " + message);
    }
}