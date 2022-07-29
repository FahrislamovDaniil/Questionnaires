package com.practice.questionnaires.controllers;

import com.practice.questionnaires.dtos.users.UserInfoDto;
import com.practice.questionnaires.dtos.users.UserRegistrationDto;
import com.practice.questionnaires.services.QueUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class QueUsersController {
    private final QueUserService queUserService;

    @Autowired
    public QueUsersController(QueUserService queUserService) {
        this.queUserService = queUserService;
    }

    @GetMapping("/get/username/{username}")
    public ResponseEntity<UserInfoDto> getByUsername(@PathVariable("username") String username) {
        UserInfoDto infoDto = queUserService.findByUsername(username);
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<UserInfoDto> getByEmail(@PathVariable("email") String email) {
        UserInfoDto infoDto = queUserService.findByEmail(email);
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, List<UserInfoDto>>> getAllUsers() {
        Map<String, List<UserInfoDto>> users = queUserService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<UserInfoDto> addUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        UserInfoDto infoDto = queUserService.addUser(userRegistrationDto);
        return new ResponseEntity<>(infoDto, HttpStatus.CREATED);
    }
}