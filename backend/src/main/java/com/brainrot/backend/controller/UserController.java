package com.brainrot.backend.controller;

import com.brainrot.backend.model.User;
import com.brainrot.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

}






