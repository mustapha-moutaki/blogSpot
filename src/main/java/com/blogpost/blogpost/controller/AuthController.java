package com.blogpost.blogpost.controller;

import com.blogpost.blogpost.Util.PasswordUtil;
import com.blogpost.blogpost.dto.request.LoginDtoRequest;
import com.blogpost.blogpost.dto.request.UserDtoRequest;
import com.blogpost.blogpost.dto.response.LoginResponse;
import com.blogpost.blogpost.dto.response.UserDtoResponse;
import com.blogpost.blogpost.enums.UserRole;
import com.blogpost.blogpost.model.User;
import com.blogpost.blogpost.repository.UserRepository;
import com.blogpost.blogpost.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Use the bean, not PasswordUtil

    @Autowired
    private UserService userService;


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

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDtoRequest request, HttpSession session) {
//
//        Optional<User> optionalUser = userRepository.findByEmail(request.getUsername());
//
//        if (optionalUser.isEmpty()) {
//            return ResponseEntity.status(401).body("User not found");
//        }
//
//        User user = optionalUser.get();
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            return ResponseEntity.status(401).body("Invalid credentials");
//        }
//
//        session.setAttribute("userId", user.getId());
//
//        return ResponseEntity.ok(
//                new LoginResponse(true, user.getRole().name())
//        );
//    }
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginDtoRequest request, HttpServletRequest httpRequest, HttpServletResponse response) {
    Optional<User> optionalUser = userRepository.findByEmail(request.getUsername());

    if (optionalUser.isEmpty() || !passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
        return ResponseEntity.status(401).body("بيانات الدخول غير صحيحة");
    }

    User user = optionalUser.get();

    // 1. إعداد الـ Security Context
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            user.getEmail(), null,
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
    );
    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(auth);

    // 2. حفظ الجلسة (Session)
    HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
    repo.saveContext(sc, httpRequest, response);

    HttpSession session = httpRequest.getSession(true);
    session.setAttribute("user", user);

    // 3. تحويل المستخدم إلى DTO وإرجاعه
    UserDtoResponse userDto = mapToUserDto(user);

    // يمكنك إرجاع الـ DTO مباشرة أو وضعه داخل LoginResponse
    return ResponseEntity.ok(userDto);
}

    @GetMapping("/current") // لاحظ حذف v1/users لأنها موروثة من الـ Class Level
    public ResponseEntity<UserDtoResponse> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = (User) session.getAttribute("user");
        return ResponseEntity.ok(mapToUserDto(user));
    }

    // دالة مساعدة لتحويل الـ Entity إلى DTO (منع تكرار الكود)
    private UserDtoResponse mapToUserDto(User user) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        if (user.getForums() != null) {
            dto.setForumsIds(user.getForums().stream().map(f -> f.getId()).toList());
        }
        if (user.getBlogs() != null) {
            dto.setBlogsIds(user.getBlogs().stream().map(b -> b.getId()).toList());
        }
        return dto;
    }



}