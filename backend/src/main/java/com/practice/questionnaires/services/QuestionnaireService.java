package com.practice.questionnaires.services;

import com.practice.questionnaires.dtos.questionnaires.QuestionnaireAdminDto;
import com.practice.questionnaires.dtos.questionnaires.QuestionnaireUserDto;
import com.practice.questionnaires.exceptions.QuestionnaireNotFoundException;
import com.practice.questionnaires.models.Questionnaire;
import com.practice.questionnaires.repos.QuestionnaireRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionnaireService {
    private final QuestionnaireRepo questionnaireRepo;
    private final DtoMappingService dtoMappingService;

    @Autowired
    public QuestionnaireService(QuestionnaireRepo questionnaireRepo, DtoMappingService dtoMappingService) {
        this.questionnaireRepo = questionnaireRepo;
        this.dtoMappingService = dtoMappingService;
    }

    public QuestionnaireAdminDto addQuestionnaire(QuestionnaireAdminDto questionnaireDto) {
        return dtoMappingService.mapQuestionnaireToAdminDto(questionnaireRepo.save(
                dtoMappingService.mapAdminDtoToQuestionnaire(questionnaireDto)));
    }

    public QuestionnaireAdminDto findQuestionnaireAdminByName(String name) {
        return dtoMappingService.mapQuestionnaireToAdminDto(questionnaireRepo.findByName(name).orElse(
                new Questionnaire()));
    }

    public QuestionnaireUserDto findQuestionnaireUserByName(String name) {
        return dtoMappingService.mapQuestionnaireToUserDto(questionnaireRepo.findByName(name).orElseThrow(
                () -> new QuestionnaireNotFoundException(
                        "Questionnaire by name = " + name + " was not found")));
    }

    public Map<String, List<String>> findAll() {
        List<String> questionnaires = questionnaireRepo.findAllQuestionnaires().orElseThrow(() -> new QuestionnaireNotFoundException(
                "Questionnaires was not found")).stream().map(Questionnaire::getName).toList();
        Map<String, List<String>> allMap = new HashMap<>();
        allMap.put("questionnaires", questionnaires);
        return allMap;
    }
}