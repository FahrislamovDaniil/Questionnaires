package com.practice.questionnaires.dtos.answers;

import java.io.Serializable;

public class AnswerUserDto implements Serializable {
    private Long id;
    private String text;

    public AnswerUserDto() {
    }

    public AnswerUserDto(Long id, String text) {
        this.id = id;
        this.text = text;
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

    @Override
    public String toString() {
        return "AnswerUserDto{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
