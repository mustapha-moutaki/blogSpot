package com.blogpost.blogpost.controller;

import com.blogpost.blogpost.Util.PasswordUtil;
import com.blogpost.blogpost.dto.request.LoginDtoRequest;
import com.blogpost.blogpost.dto.request.UserDtoRequest;
import com.blogpost.blogpost.enums.UserRole;
import com.blogpost.blogpost.model.User;
import com.blogpost.blogpost.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Use the bean, not PasswordUtil



    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDtoRequest request) {
        // 1. Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Email is already in use!");
        }

        try {
            // 2. Create new User entity
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());

            // 3. ENCODE the password before saving
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            // 4. Set default role to USER
            user.setRole(UserRole.USER);

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDtoRequest request, HttpSession session) {
        try {
            System.out.println("DEBUG: Login attempt for user: " + request.getUsername());

            Optional<User> optionalUser = userRepository.findByEmail(request.getUsername());

            if (optionalUser.isEmpty()) {
                System.out.println("DEBUG: User not found in database");
                return ResponseEntity.status(401).body("User not found");
            }

            User user = optionalUser.get();
            System.out.println("DEBUG: User found: " + user.getEmail());
            System.out.println("DEBUG: Role from DB: " + user.getRole());

            boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
            System.out.println("DEBUG: Password match: " + matches);

            if (matches) {
                session.setAttribute("userId", user.getId());
                return ResponseEntity.ok(user.getRole() + " logged in");
            }

            return ResponseEntity.status(401).body("Invalid credentials");

        } catch (Exception e) {
            e.printStackTrace(); // This prints the RED text to your console
            return ResponseEntity.status(500).body(new java.util.HashMap<String, Object>() {{
                put("error", e.getClass().getSimpleName());
                put("message", e.getMessage());
                put("trace", e.getStackTrace()[0].toString());
            }});
        }
    }
}