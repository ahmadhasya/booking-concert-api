package com.ahmad.bsi.service;

import com.ahmad.bsi.model.User;
import com.ahmad.bsi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() { return repo.findAll(); }

    public Optional<User> findById(Long id) { return repo.findById(id); }

    public User save(User user) {
        if(user.getId()==null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return repo.save(user);
    }

    public User savePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return repo.save(user);
    }

    public User findByEmail(String email) {
        return this.repo.findByEmail(email);
    }

    public boolean validatePassword(String password, String encoded){
        return passwordEncoder.matches(password, encoded);
    }
}
