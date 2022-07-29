package com.practice.questionnaires.dtos.questions;

import com.practice.questionnaires.dtos.answers.AnswerAdminDto;

import java.io.Serializable;
import java.util.List;

public class QuestionAdminDto implements Serializable {
    private Long id;
    private Long questionnaire;
    private String text;
    private List<AnswerAdminDto> answers;

    public QuestionAdminDto() {
    }

    public QuestionAdminDto(Long id, Long questionnaire, String text, List<AnswerAdminDto> answers) {
        this.id = id;
        this.questionnaire = questionnaire;
        this.text = text;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Long questionnaire) {
        this.questionnaire = questionnaire;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<AnswerAdminDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerAdminDto> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QuestionAdminDto{" +
                "id=" + id +
                ", questionnaire=" + questionnaire +
                ", text='" + text + '\'' +
                ", answers=" + answers +
                '}';
    }
}
