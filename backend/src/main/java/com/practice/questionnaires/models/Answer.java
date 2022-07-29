package com.practice.questionnaires.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Answer extends EntityCore implements Serializable {

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "correct", nullable = false)
    private boolean correct;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToMany(mappedBy = "answers", fetch = FetchType.LAZY)
    private List<Attempt> attempts;

    public Answer() {
    }

    public Answer(String text, boolean correct, Question question, List<Attempt> attempts) {
        this.text = text;
        this.correct = correct;
        this.question = question;
        this.attempts = attempts;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Attempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<Attempt> attempts) {
        this.attempts = attempts;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "text='" + text + '\'' +
                ", correct=" + correct +
                ", question=" + question +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Answer answer = (Answer) o;
        return Objects.equals(getId(), answer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}