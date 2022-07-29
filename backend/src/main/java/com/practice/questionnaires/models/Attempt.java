package com.practice.questionnaires.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Attempt extends EntityCore implements Serializable {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private QueUser queUser;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "tries", nullable = false)
    private int tries;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "attempt_answer", joinColumns =
            {@JoinColumn(name = "attempt_id", referencedColumnName = "id")}, inverseJoinColumns =
            {@JoinColumn(name = "answer_id", referencedColumnName = "id")})
    private List<Answer> answers;

    public Attempt() {
    }

    public Attempt(QueUser queUser, int score, int tries, List<Answer> answers) {
        this.queUser = queUser;
        this.score = score;
        this.tries = tries;
        this.answers = answers;
    }

    public QueUser getQueUser() {
        return queUser;
    }

    public void setQueUser(QueUser queUser) {
        this.queUser = queUser;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "queUser=" + queUser +
                ", score=" + score +
                ", tries=" + tries +
                '}';
    }
}