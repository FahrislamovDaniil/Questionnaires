package com.practice.questionnaires.dtos.attempts;

import com.practice.questionnaires.dtos.questionnaires.QuestionnaireAdminDto;

import java.io.Serializable;

public class CompletedDto implements Serializable {
    private QuestionnaireAdminDto questionnaireDto;
    private AttemptDto attemptDto;

    public CompletedDto() {
    }

    public CompletedDto(QuestionnaireAdminDto questionnaireDto, AttemptDto attemptDto) {
        this.questionnaireDto = questionnaireDto;
        this.attemptDto = attemptDto;
    }

    public QuestionnaireAdminDto getQuestionnaireDto() {
        return questionnaireDto;
    }

    public void setQuestionnaireDto(QuestionnaireAdminDto questionnaireDto) {
        this.questionnaireDto = questionnaireDto;
    }

    public AttemptDto getAttemptDto() {
        return attemptDto;
    }

    public void setAttemptDto(AttemptDto attemptDto) {
        this.attemptDto = attemptDto;
    }

    @Override
    public String toString() {
        return "CompletedDto{" +
                "questionnaireDto=" + questionnaireDto +
                ", attemptDto=" + attemptDto +
                '}';
    }
}