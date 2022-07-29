package com.practice.questionnaires.dtos.questionnaires;

import com.practice.questionnaires.dtos.questions.QuestionAdminDto;

import java.io.Serializable;
import java.util.List;

public class QuestionnaireAdminDto implements Serializable {
    private Long id;
    private String name;
    private int tries;
    private List<QuestionAdminDto> questions;

    public QuestionnaireAdminDto() {
    }

    public QuestionnaireAdminDto(Long id, String name, int tries, List<QuestionAdminDto> questions) {
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

    public List<QuestionAdminDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionAdminDto> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "QuestionnaireAdminDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tries=" + tries +
                ", questions=" + questions +
                '}';
    }
}
