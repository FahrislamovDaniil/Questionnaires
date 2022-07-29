package com.practice.questionnaires.dtos.users;

import java.io.Serializable;

public class UserInfoDto implements Serializable {
    private String email;
    private String username;
    private int score;

    public UserInfoDto() {
    }

    public UserInfoDto(String email, String username, int score) {
        this.email = email;
        this.username = username;
        this.score = score;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", score=" + score +
                '}';
    }
}
