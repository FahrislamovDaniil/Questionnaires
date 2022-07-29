package com.practice.questionnaires.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Questionnaire extends EntityCore implements Serializable {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "tries", nullable = false)
    private int tries;

    @OneToMany(mappedBy = "questionnaire", fetch = FetchType.EAGER)
    private List<Question> questions;

    public Questionnaire() {
    }

    public Questionnaire(String name, int tries, List<Question> questions) {
        this.name = name;
        this.tries = tries;
        this.questions = questions;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "name='" + name + '\'' +
                ", tries=" + tries +
                '}';
    }
}