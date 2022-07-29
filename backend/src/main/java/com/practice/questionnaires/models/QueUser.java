package com.practice.questionnaires.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class QueUser extends EntityCore implements Serializable {

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "score", nullable = false)
    private int score;

    @OneToMany(mappedBy = "queUser", fetch = FetchType.EAGER)
    private List<Attempt> attempts;

    @Column(name = "role", nullable = false)
    private Role role;

    public QueUser() {
    }

    public QueUser(String email, String username, String password, int score, List<Attempt> attempts, Role role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.score = score;
        this.attempts = attempts;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Attempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<Attempt> attempts) {
        this.attempts = attempts;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "QueUser{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", score=" + score +
                ", role=" + role +
                '}';
    }
}