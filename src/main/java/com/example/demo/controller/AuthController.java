package com.example.demo.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RoleModel;
import com.example.demo.model.RoleName;
import com.example.demo.model.UserModel;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.UserDetailsImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "&", maxAge = 3600)
@RestController
@RequestMapping("/apt/auth")
public class AuthController {
    @Autowired
    AuthenticationManager aManager;

    @Autowired
    UserRepo uRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticationUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = aManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrintcipal();
            List<String> roles = userDetails.getAutherities().stream()
            .map(item => item.getAthority())
            .collect(Collectors.toList());

            return ResponseEntity.ok()(new JwtResponse(jwt, userDetails.getId(),
            userDetails.getUsername(),
            userDetails.Email(),
            roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if(UserRepo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        
        if(UserRepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        UserModel user = new UserModel(signUpRequest.getUsername(),
                        signUpRequest.getEmail(),
                        encoder.encode(signUpRequest.getPassword()));
        
                        Set<String> strRoles = signUpRequest.getRole();
                        Set<RoleModel> roles = new HashSet<>();

                        if (strRoles == null) {
                            RoleModel userRole = roleRepo.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                        } else {
                            strRoles.forEach(role -> {
                                switch (role) {
                                    case "admin":
                                    RoleModel adminRole = roleRepo.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                break;
                                case "employee":
                                RoleModel employeeRole = roleRepo.findByName(RoleName.ROLE_EMPLOYEE).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            break;
                            default:
                                    case "user":
                                    RoleModel userRole = roleRepo.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                    roles.add(userRole);
                            }
                            });
                        }
                        user.setRoles(roles);
                        uRepo.save(user);
                        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
