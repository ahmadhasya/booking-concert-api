package com.ahmad.bsi.controller;

import com.ahmad.bsi.model.User;
import com.ahmad.bsi.service.UserService;
import com.ahmad.bsi.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/self")
public class SelfController {
    private final UserService service;

    public SelfController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public User getSelf(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = JwtUtil.validateToken(token);
        return this.service.findByEmail(email);
    }
}

