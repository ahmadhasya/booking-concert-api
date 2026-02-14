package com.ahmad.bsi.controller;

import com.ahmad.bsi.model.User;
import com.ahmad.bsi.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

}

