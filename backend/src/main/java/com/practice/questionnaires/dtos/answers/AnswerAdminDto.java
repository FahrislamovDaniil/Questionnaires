package com.practice.questionnaires.dtos.answers;

import java.io.Serializable;

public class AnswerAdminDto implements Serializable {
    private Long id;
    private Long question;
    private String text;
    private boolean correct;

    public AnswerAdminDto() {
    }

    public AnswerAdminDto(Long id, Long question, String text, boolean correct) {
        this.id = id;
        this.question = question;
        this.text = text;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestion() {
        return question;
    }

    public void setQuestion(Long question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "AnswerAdminDto{" +
                "id=" + id +
                ", question=" + question +
                ", text='" + text + '\'' +
                ", correct=" + correct +
                '}';
    }
}
