package com.practice.questionnaires.services;

import com.practice.questionnaires.dtos.users.UserInfoDto;
import com.practice.questionnaires.dtos.users.UserRegistrationDto;
import com.practice.questionnaires.models.QueUser;
import com.practice.questionnaires.repos.QueUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.practice.questionnaires.models.Role.ROLE_USER;

@Service
public class QueUserService {
    private final QueUserRepo queUserRepo;
    private final DtoMappingService dtoMappingService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Autowired
    public QueUserService(QueUserRepo queUserRepo, DtoMappingService dtoMappingService,
                          BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.queUserRepo = queUserRepo;
        this.dtoMappingService = dtoMappingService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    public UserInfoDto addUser(UserRegistrationDto userRegistrationDto) {
        QueUser user = dtoMappingService.mapRegDtoToUser(userRegistrationDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(ROLE_USER);
        sendGreeting(user.getEmail(), user.getUsername());
        return dtoMappingService.mapUserToInfoDto(queUserRepo.save(user));
    }

    public UserInfoDto findByUsername(String username) {
        return dtoMappingService.mapUserToInfoDto(queUserRepo.findByUsername(username)
                .orElse(new QueUser()));
    }

    public UserInfoDto findByEmail(String email) {
        return dtoMappingService.mapUserToInfoDto(queUserRepo.findByEmail(email)
                .orElse(new QueUser()));
    }

    public Map<String, List<UserInfoDto>> findAllUsers() {
        List<UserInfoDto> users = queUserRepo.findAllUsers().orElseThrow(() -> new UsernameNotFoundException(
                "Users not found")).stream().map(dtoMappingService::mapUserToInfoDto).toList();
        Map<String, List<UserInfoDto>> allMap = new HashMap<>();
        allMap.put("users", users);
        return allMap;
    }

    private void sendGreeting(String email, String username) {
        String subject = "Вы зарегестрированы на сайте \"Тест-анкета\"!";
        String message = "Поздравляем, " + username + "! Вы были зарегестрированы на сайте \"Тест-анкета\"\n" +
                "Теперь вы можете войти на сайт с помощью имени и пароля и заполнять доступные анкеты!\n\n" +
                "С уважением, команда \"Тест-анкета\"";
        emailService.sendEmail(email, subject, message);
    }
}