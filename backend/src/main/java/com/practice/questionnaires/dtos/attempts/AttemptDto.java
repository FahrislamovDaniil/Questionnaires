package com.practice.questionnaires.dtos.attempts;

import com.practice.questionnaires.dtos.answers.AnswerUserDto;

import java.io.Serializable;
import java.util.List;

public class AttemptDto implements Serializable {
    private Long id;
    private String user;
    private int score;
    private int tries;
    private List<AnswerUserDto> answers;

    public AttemptDto() {
    }

    public AttemptDto(Long id, String user, int score, int tries, List<AnswerUserDto> answers) {
        this.id = id;
        this.user = user;
        this.score = score;
        this.tries = tries;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public List<AnswerUserDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerUserDto> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "AttemptDto{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", score=" + score +
                ", tries=" + tries +
                ", answers=" + answers +
                '}';
    }
}