package com.practice.questionnaires.controllers;

import com.practice.questionnaires.dtos.questions.QuestionAdminDto;
import com.practice.questionnaires.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionsController {
    private final QuestionService questionService;

    @Autowired
    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/admin/add")
    public ResponseEntity<QuestionAdminDto> addQuestion(@RequestBody QuestionAdminDto questionDto) {
        QuestionAdminDto question = questionService.addQuestion(questionDto);
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }
}