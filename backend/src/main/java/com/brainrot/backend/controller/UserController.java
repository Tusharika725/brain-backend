package com.brainrot.backend.controller;

import com.brainrot.backend.dto.LoginRequestDto;
import com.brainrot.backend.dto.RegisterRequestDto;
import com.brainrot.backend.dto.UserProfileDto;
import com.brainrot.backend.model.User;
import com.brainrot.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
 //signup endpoint
   @PostMapping("/register")
   public ResponseEntity<UserProfileDto> register(@RequestBody RegisterRequestDto request) {
       if (request == null || request.getEmail() == null) {
           logger.error("Registration failed: Received empty or invalid payload.");
           return ResponseEntity.badRequest().build(); // Returns 400 Bad Request
       }
       logger.info("Incoming registration request for email: [{}]", request.getEmail());
       User newUser = userService.registerUser(request.getEmail(), request.getUsername(), request.getPassword());

       if (newUser == null) {
           return ResponseEntity.status(HttpStatus.CONFLICT).build();
       }

       UserProfileDto profile = new UserProfileDto(newUser.getUsername(), newUser.getAuraPoints());
       return ResponseEntity.ok(profile);
   }

    @PostMapping("/login")
    public ResponseEntity<UserProfileDto> login(@RequestBody LoginRequestDto request) {
        logger.info("Incoming login request for email: [{}]", request.getEmail());

        User authenticatedUser = userService.authenticateUser(request.getEmail(), request.getPassword());

        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserProfileDto profile = new UserProfileDto(authenticatedUser.getUsername(), authenticatedUser.getAuraPoints());
        return ResponseEntity.ok(profile);
    }
}






