package com.ahmad.bsi.controller;

import com.ahmad.bsi.form.LoginForm;
import com.ahmad.bsi.model.User;
import com.ahmad.bsi.service.UserService;
import com.ahmad.bsi.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm form) {
        User user = this.userService.findByEmail(form.getEmail());
        if (user == null || !this.userService.validatePassword(form.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", "Invalid credentials"
            ));
        }

        String token = JwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(
                Map.of(
                        "token", token
                )
        );
    }
}

