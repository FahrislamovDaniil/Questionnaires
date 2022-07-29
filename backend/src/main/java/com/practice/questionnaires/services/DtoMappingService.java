package com.practice.questionnaires.services;

import com.practice.questionnaires.dtos.answers.AnswerAdminDto;
import com.practice.questionnaires.dtos.answers.AnswerUserDto;
import com.practice.questionnaires.dtos.attempts.AttemptDto;
import com.practice.questionnaires.dtos.attempts.CompletedDto;
import com.practice.questionnaires.dtos.questionnaires.QuestionnaireAdminDto;
import com.practice.questionnaires.dtos.questionnaires.QuestionnaireUserDto;
import com.practice.questionnaires.dtos.questions.QuestionAdminDto;
import com.practice.questionnaires.dtos.questions.QuestionUserDto;
import com.practice.questionnaires.dtos.users.UserInfoDto;
import com.practice.questionnaires.dtos.users.UserRegistrationDto;
import com.practice.questionnaires.exceptions.AnswerNotFoundException;
import com.practice.questionnaires.exceptions.QuestionNotFoundException;
import com.practice.questionnaires.exceptions.QuestionnaireNotFoundException;
import com.practice.questionnaires.models.*;
import com.practice.questionnaires.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DtoMappingService {
    private final QueUserRepo queUserRepo;
    private final QuestionnaireRepo questionnaireRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;

    @Autowired
    public DtoMappingService(QueUserRepo queUserRepo, QuestionnaireRepo questionnaireRepo,
                             QuestionRepo questionRepo, AnswerRepo answerRepo) {
        this.queUserRepo = queUserRepo;
        this.questionnaireRepo = questionnaireRepo;
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
    }

    public QueUser mapRegDtoToUser(UserRegistrationDto regDto) {
        QueUser user = new QueUser();
        user.setEmail(regDto.getEmail());
        user.setUsername(regDto.getUsername());
        user.setPassword(regDto.getPassword());
        user.setAttempts(new ArrayList<>());
        return user;
    }

    public UserInfoDto mapUserToInfoDto(QueUser user) {
        UserInfoDto infoDto = new UserInfoDto();
        infoDto.setEmail(user.getEmail());
        infoDto.setUsername(user.getUsername());
        infoDto.setScore(user.getScore());
        return infoDto;
    }

    public Answer mapAdminDtoToAnswer(AnswerAdminDto answerDto) {
        Answer answer = new Answer();
        answer.setId(answerDto.getId());
        answer.setText(answerDto.getText());
        answer.setCorrect(answerDto.isCorrect());
        answer.setQuestion(questionRepo.getQuestionById(answerDto.getQuestion()).orElseThrow(() ->
                new QuestionNotFoundException("Question by id = " + answerDto.getQuestion() + " was not found")));
        answer.setAttempts(new ArrayList<>());
        return answer;
    }

    public AnswerAdminDto mapAnswerToAdminDto(Answer answer) {
        AnswerAdminDto answerDto = new AnswerAdminDto();
        answerDto.setId(answer.getId());
        answerDto.setText(answer.getText());
        answerDto.setCorrect(answer.isCorrect());
        answerDto.setQuestion(answer.getQuestion().getId());
        return answerDto;
    }

    public AnswerUserDto mapAnswerToUserDto(Answer answer) {
        AnswerUserDto answerDto = new AnswerUserDto();
        answerDto.setId(answer.getId());
        answerDto.setText(answer.getText());
        return answerDto;
    }

    public Question mapAdminDtoToQuestion(QuestionAdminDto questionDto) {
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setText(questionDto.getText());
        question.setQuestionnaire(questionnaireRepo.findQuestionnaireById(questionDto.getQuestionnaire()).orElseThrow(
                () -> new QuestionnaireNotFoundException(
                        "Questionnaire by id = " + questionDto.getQuestionnaire() + " was not found")));
        question.setAnswers(new ArrayList<>());
        return question;
    }

    public QuestionAdminDto mapQuestionToAdminDto(Question question) {
        QuestionAdminDto questionDto = new QuestionAdminDto();
        questionDto.setId(question.getId());
        questionDto.setText(question.getText());
        questionDto.setQuestionnaire(question.getQuestionnaire().getId());
        questionDto.setAnswers(question.getAnswers().stream().map(this::mapAnswerToAdminDto)
                .collect(Collectors.toList()));
        return questionDto;
    }

    public QuestionUserDto mapQuestionToUserDto(Question question) {
        QuestionUserDto questionDto = new QuestionUserDto();
        questionDto.setId(question.getId());
        questionDto.setText(question.getText());
        questionDto.setAnswers(question.getAnswers().stream().map(this::mapAnswerToUserDto)
                .collect(Collectors.toList()));
        return questionDto;
    }

    public Questionnaire mapAdminDtoToQuestionnaire(QuestionnaireAdminDto questionnaireDto) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(questionnaireDto.getId());
        questionnaire.setName(questionnaireDto.getName());
        questionnaire.setTries(questionnaireDto.getTries());
        questionnaire.setQuestions(new ArrayList<>());
        return questionnaire;
    }

    public QuestionnaireAdminDto mapQuestionnaireToAdminDto(Questionnaire questionnaire) {
        QuestionnaireAdminDto questionnaireDto = new QuestionnaireAdminDto();
        questionnaireDto.setId(questionnaire.getId());
        questionnaireDto.setName(questionnaire.getName());
        questionnaireDto.setTries(questionnaire.getTries());
        if (questionnaire.getQuestions() == null) {
            questionnaire.setQuestions(new ArrayList<>());
        } else {
            questionnaireDto.setQuestions(questionnaire.getQuestions().stream().map(this::mapQuestionToAdminDto)
                    .collect(Collectors.toList()));
        }
        return questionnaireDto;
    }

    public QuestionnaireUserDto mapQuestionnaireToUserDto(Questionnaire questionnaire) {
        QuestionnaireUserDto questionnaireDto = new QuestionnaireUserDto();
        questionnaireDto.setId(questionnaire.getId());
        questionnaireDto.setName(questionnaire.getName());
        questionnaireDto.setTries(questionnaire.getTries());
        questionnaireDto.setQuestions(questionnaire.getQuestions().stream().map(this::mapQuestionToUserDto)
                .collect(Collectors.toList()));
        return questionnaireDto;
    }

    public Attempt mapDtoToAttempt(AttemptDto attemptDto) {
        Attempt attempt = new Attempt();
        String username = attemptDto.getUser();
        attempt.setId(attemptDto.getId());
        attempt.setQueUser(queUserRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User by username = " + attemptDto.getUser() + " was not found")));
        attempt.setScore(attemptDto.getScore());
        attempt.setTries(attemptDto.getTries());
        attempt.setAnswers(attemptDto.getAnswers().stream().map(a -> answerRepo.findAnswerById(a.getId()).orElseThrow(
                        () -> new AnswerNotFoundException("Answer by id = " + a.getId() + " was not found")))
                .collect(Collectors.toList()));
        return attempt;
    }

    public AttemptDto mapAttemptToDto(Attempt attempt) {
        AttemptDto attemptDto = new AttemptDto();
        attemptDto.setId(attempt.getId());
        attemptDto.setUser(attempt.getQueUser().getUsername());
        attemptDto.setScore(attempt.getScore());
        attemptDto.setTries(attempt.getTries());
        attemptDto.setAnswers(attempt.getAnswers().stream().map(this::mapAnswerToUserDto)
                .collect(Collectors.toList()));
        return attemptDto;
    }

    public CompletedDto mapAttemptToCompleted(Attempt attempt) {
        CompletedDto completed = new CompletedDto();
        completed.setQuestionnaireDto(this.mapQuestionnaireToAdminDto(
                attempt.getAnswers().get(0).getQuestion().getQuestionnaire()));
        completed.setAttemptDto(this.mapAttemptToDto(attempt));
        return completed;
    }
}