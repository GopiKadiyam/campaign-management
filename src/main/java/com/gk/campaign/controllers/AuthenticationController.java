package com.gk.campaign.controllers;

import com.gk.campaign.entities.RoleEntity;
import com.gk.campaign.entities.UserEntity;
import com.gk.campaign.exceptions.InvalidRequestException;
import com.gk.campaign.payload.request.LoginRequest;
import com.gk.campaign.payload.request.SignupRequest;
import com.gk.campaign.payload.response.JwtResponse;
import com.gk.campaign.repository.RoleRepository;
import com.gk.campaign.repository.UserRepository;
import com.gk.campaign.service.UserDetailsImpl;
import com.gk.campaign.utils.enums.CMRole;
import com.gk.campaign.utils.security.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new InvalidRequestException(Map.of("username", "Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new InvalidRequestException(Map.of("email", "Error: email is already taken!"));
        }

        // Create new user's account
        UserEntity user = new UserEntity(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        //Set<String> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();
//        if (strRoles == null || strRoles.isEmpty()) {
        RoleEntity userRole = roleRepository.findByName(CMRole.ROLE_SUPER_ADMIN)
                .orElseThrow(() -> new InvalidRequestException(Map.of("role", "Role User not present")));
        roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "SUPER_ADMIN":
//                        RoleEntity adminRole = roleRepository.findByName(CMRole.ROLE_SUPER_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "ADMIN":
//                        RoleEntity modRole = roleRepository.findByName(CMRole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        RoleEntity userRole = roleRepository.findByName(CMRole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("status", "User registered successfully!"));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        Cookie cookie = new Cookie("token", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); //60sec *10
        response.addCookie(cookie);
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthentication(@CookieValue(name = "token", required = false) String token) {
        if (token != null && !token.isEmpty()) {
            return ResponseEntity.ok().body(Collections.singletonMap("authenticated", true));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("authenticated", false));
        }
    }

//    @GetMapping("/test")
//    public ResponseEntity<?> test() {
//
//        List<RoleEntity> list = new ArrayList<>();
//        list.add(new RoleEntity(CMRole.ROLE_SUPER_ADMIN, LocalDateTime.now(), LocalDateTime.now()));
//        list.add(new RoleEntity(CMRole.ROLE_ADMIN, LocalDateTime.now(), LocalDateTime.now()));
//        list.add(new RoleEntity(CMRole.ROLE_USER, LocalDateTime.now(), LocalDateTime.now()));
//
//        return ResponseEntity.ok(roleRepository.saveAll(list));
//    }
}
