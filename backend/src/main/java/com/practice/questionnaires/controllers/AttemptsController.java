package com.practice.questionnaires.controllers;

import com.practice.questionnaires.dtos.attempts.AttemptDto;
import com.practice.questionnaires.dtos.attempts.CompletedDto;
import com.practice.questionnaires.services.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attempt")
public class AttemptsController {
    private final AttemptService attemptService;

    @Autowired
    public AttemptsController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @GetMapping("/all/{username}")
    public ResponseEntity<Map<String, List<CompletedDto>>> getAllByUser(@PathVariable("username") String username) {
        Map<String, List<CompletedDto>> completes = attemptService.findAllAttemptsByUser(username);
        return new ResponseEntity<>(completes, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CompletedDto> addAttempt(@RequestBody AttemptDto attemptDto) {
        CompletedDto completed = attemptService.addAttempt(attemptDto);
        return new ResponseEntity<>(completed, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CompletedDto> updateAttempt(@RequestBody AttemptDto attemptDto) {
        CompletedDto completed = attemptService.updateAttempt(attemptDto);
        return new ResponseEntity<>(completed, HttpStatus.CREATED);
    }
}