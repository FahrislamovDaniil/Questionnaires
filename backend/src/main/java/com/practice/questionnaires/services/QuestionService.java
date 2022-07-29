package com.practice.questionnaires.services;

import com.practice.questionnaires.dtos.questions.QuestionAdminDto;
import com.practice.questionnaires.repos.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepo questionRepo;
    private final DtoMappingService dtoMappingService;

    @Autowired
    public QuestionService(QuestionRepo questionRepo, DtoMappingService dtoMappingService) {
        this.questionRepo = questionRepo;
        this.dtoMappingService = dtoMappingService;
    }

    public QuestionAdminDto addQuestion(QuestionAdminDto question) {
        return dtoMappingService.mapQuestionToAdminDto(questionRepo.save(
                dtoMappingService.mapAdminDtoToQuestion(question)));
    }
}
