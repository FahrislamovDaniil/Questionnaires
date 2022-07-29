package com.practice.questionnaires.controllers;

import com.practice.questionnaires.dtos.questionnaires.QuestionnaireAdminDto;
import com.practice.questionnaires.dtos.questionnaires.QuestionnaireUserDto;
import com.practice.questionnaires.services.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnairesController {
    private final QuestionnaireService questionnaireService;

    @Autowired
    public QuestionnairesController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @GetMapping("/admin/get/{name}")
    public ResponseEntity<QuestionnaireAdminDto> getByNameAdmin(@PathVariable("name") String name) {
        QuestionnaireAdminDto questionnaire = questionnaireService.findQuestionnaireAdminByName(name);
        return new ResponseEntity<>(questionnaire, HttpStatus.OK);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<QuestionnaireUserDto> getByNameUser(@PathVariable("name") String name) {
        QuestionnaireUserDto questionnaire = questionnaireService.findQuestionnaireUserByName(name);
        return new ResponseEntity<>(questionnaire, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, List<String>>> getAllUser() {
        Map<String, List<String>> questionnaires = questionnaireService.findAll();
        return new ResponseEntity<>(questionnaires, HttpStatus.OK);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<QuestionnaireAdminDto> addQuestionnaire(@RequestBody QuestionnaireAdminDto questionnaireDto) {
        QuestionnaireAdminDto questionnaire = questionnaireService.addQuestionnaire(questionnaireDto);
        return new ResponseEntity<>(questionnaire, HttpStatus.CREATED);
    }
}