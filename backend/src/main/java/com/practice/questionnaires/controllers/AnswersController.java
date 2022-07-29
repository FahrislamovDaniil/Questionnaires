package com.practice.questionnaires.controllers;

import com.practice.questionnaires.dtos.answers.AnswerAdminDto;
import com.practice.questionnaires.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
public class AnswersController {
    private final AnswerService answerService;

    @Autowired
    public AnswersController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/admin/add")
    public ResponseEntity<AnswerAdminDto> addAnswer(@RequestBody AnswerAdminDto answerDto) {
        AnswerAdminDto answer = answerService.addAnswer(answerDto);
        return new ResponseEntity<>(answer, HttpStatus.CREATED);
    }
}