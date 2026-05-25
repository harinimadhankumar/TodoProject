package com.example.HelloWorld.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.HelloWorld.models.User;
import com.example.HelloWorld.Repository.UserRepository;
import com.example.HelloWorld.service.UserService;
import com.example.HelloWorld.utils.JwtUtils;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtil;

    /*// 🟢 Register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = passwordEncoder.encode(body.get("password"));

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } else {
            userService.createUser(User.builder().email(email).password(password).build());
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }
    }
    // 🟢 Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return  new  ResponseEntity<>("User not     Registered", HttpStatus.UNAUTHORIZED);
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
       
        String token  = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token", token));
        
    }
*/
@PostMapping("/register")
public ResponseEntity<?> registerUser(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String password = passwordEncoder.encode(body.get("password"));

    if (userRepository.findByEmail(email).isPresent()) {
        // Conflict → user already exists
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", "User already exists"));
    } else {
        userService.createUser(User.builder()
                .email(email)
                .password(password)
                .build());
        // Return JSON instead of plain text ✅
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }
}
@PostMapping("/login")
public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String password = body.get("password");

    var userOptional = userRepository.findByEmail(email);

    if (userOptional.isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "User not registered"));
    }

    User user = userOptional.get();
    if (!passwordEncoder.matches(password, user.getPassword())) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid password"));
    }

    String token = jwtUtil.generateToken(email);
    return ResponseEntity.ok(Map.of(
            "token", token,
            "message", "Login successful"
    ));
}
}


