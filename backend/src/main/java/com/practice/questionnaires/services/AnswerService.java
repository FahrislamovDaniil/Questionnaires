package com.practice.questionnaires.services;

import com.practice.questionnaires.dtos.answers.AnswerAdminDto;
import com.practice.questionnaires.repos.AnswerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepo answerRepo;
    private final DtoMappingService dtoMappingService;

    @Autowired
    public AnswerService(AnswerRepo answerRepo, DtoMappingService dtoMappingService) {
        this.answerRepo = answerRepo;
        this.dtoMappingService = dtoMappingService;
    }

    public AnswerAdminDto addAnswer(AnswerAdminDto answer) {
        return dtoMappingService.mapAnswerToAdminDto(answerRepo.save(
                dtoMappingService.mapAdminDtoToAnswer(answer)));
    }
}