package com.practice.questionnaires.services;

import com.practice.questionnaires.dtos.attempts.AttemptDto;
import com.practice.questionnaires.dtos.attempts.CompletedDto;
import com.practice.questionnaires.models.*;
import com.practice.questionnaires.repos.AttemptRepo;
import com.practice.questionnaires.repos.QueUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttemptService {
    private final AttemptRepo attemptRepo;
    private final QueUserRepo queUserRepo;
    private final DtoMappingService dtoMappingService;

    @Autowired
    public AttemptService(AttemptRepo attemptRepo, QueUserRepo queUserRepo, DtoMappingService dtoMappingService) {
        this.attemptRepo = attemptRepo;
        this.queUserRepo = queUserRepo;
        this.dtoMappingService = dtoMappingService;
    }

    public CompletedDto addAttempt(AttemptDto attemptDto) {
        Attempt attempt = dtoMappingService.mapDtoToAttempt(attemptDto);
        Questionnaire questionnaire = attempt.getAnswers().get(0).getQuestion().getQuestionnaire();
        QueUser user = attempt.getQueUser();
        attempt.setTries(attempt.getTries() + 1);
        attempt.setScore(calcScore(questionnaire.getQuestions(), attempt.getAnswers()));
        user.setScore(user.getScore() + attempt.getScore());
        queUserRepo.save(user);
        return dtoMappingService.mapAttemptToCompleted(attemptRepo.save(attempt));
    }

    public CompletedDto updateAttempt(AttemptDto attemptDto) {
        Attempt attempt = dtoMappingService.mapDtoToAttempt(attemptDto);
        Questionnaire questionnaire = attempt.getAnswers().get(0).getQuestion().getQuestionnaire();
        QueUser user = attempt.getQueUser();
        attempt.setTries(attempt.getTries() + 1);
        user.setScore(user.getScore() - attempt.getScore());
        attempt.setScore(calcScore(questionnaire.getQuestions(), attempt.getAnswers()));
        user.setScore(user.getScore() + attempt.getScore());
        queUserRepo.save(user);
        return dtoMappingService.mapAttemptToCompleted(attemptRepo.save(attempt));
    }

    public Map<String, List<CompletedDto>> findAllAttemptsByUser(String username) {
        QueUser user = queUserRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "Username by username " + username + " was not found"));
        List<CompletedDto> attempts = user.getAttempts().stream().map(dtoMappingService::mapAttemptToCompleted).toList();
        Map<String, List<CompletedDto>> allMap = new HashMap<>();
        allMap.put("attempts", attempts);
        return allMap;
    }

    private int calcScore(List<Question> questions, List<Answer> answers) {
        int score = 0;
        for (Question que : questions) {
            List<Answer> correctAns = que.getAnswers().stream().filter(Answer::isCorrect).toList();
            List<Answer> ansToQue = answers.stream().filter(a -> a.getQuestion().equals(que)).toList();
            if (ansToQue.size() != correctAns.size())
                score--;
            else {
                if (ansToQue.containsAll(correctAns))
                    score++;
                else
                    score--;
            }
        }
        return score;
    }
}