package com.practice.questionnaires.dtos.questions;

import com.practice.questionnaires.dtos.answers.AnswerUserDto;

import java.io.Serializable;
import java.util.List;

public class QuestionUserDto implements Serializable {
    private Long id;
    private String text;
    private List<AnswerUserDto> answers;

    public QuestionUserDto() {
    }

    public QuestionUserDto(Long id, String text, List<AnswerUserDto> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<AnswerUserDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerUserDto> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QuestionUserDto{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", answers=" + answers +
                '}';
    }
}
