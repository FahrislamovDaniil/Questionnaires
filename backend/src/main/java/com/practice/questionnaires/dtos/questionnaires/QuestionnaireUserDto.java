package com.practice.questionnaires.dtos.questionnaires;

import com.practice.questionnaires.dtos.questions.QuestionUserDto;

import java.io.Serializable;
import java.util.List;

public class QuestionnaireUserDto implements Serializable {
    private Long id;
    private String name;
    private int tries;
    private List<QuestionUserDto> questions;

    public QuestionnaireUserDto() {
    }

    public QuestionnaireUserDto(Long id, String name, int tries, List<QuestionUserDto> questions) {
        this.id = id;
        this.name = name;
        this.tries = tries;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public List<QuestionUserDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionUserDto> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "QuestionnaireUserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tries=" + tries +
                ", questions=" + questions +
                '}';
    }
}