package com.practice.questionnaires.exceptions;

public class QuestionnaireNotFoundException extends RuntimeException {
    public QuestionnaireNotFoundException(String message) {
        super(message);
    }
}