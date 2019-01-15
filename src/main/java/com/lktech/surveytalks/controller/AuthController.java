package com.lktech.surveytalks.controller;

import com.lktech.surveytalks.exception.InternalServerException;
import com.lktech.surveytalks.model.Role;
import com.lktech.surveytalks.model.User;
import com.lktech.surveytalks.model.enums.RoleName;
import com.lktech.surveytalks.payload.ApiResponse;
import com.lktech.surveytalks.payload.JwtAuthenticationResponse;
import com.lktech.surveytalks.payload.LoginRequest;
import com.lktech.surveytalks.payload.SignUpRequest;
import com.lktech.surveytalks.repository.RoleRepository;
import com.lktech.surveytalks.repository.UserRepository;
import com.lktech.surveytalks.security.util.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest login) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login.getUsernameOrEmail() , login.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.generateToken(auth); // create new JWT Token to user loggedIn

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt)); //  set new jwt token to String `accessToken` & return
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
           // SecurityContextHolder.clearContext();
            new SecurityContextLogoutHandler().logout(request, response, auth);

            return ResponseEntity.ok().body(new ApiResponse(true, "User logout successfully"));
        }
        return null;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest register) {

        if (userRepository.existsByUsername(register.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken...!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(register.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // else create new user
        User user = new User(register.getName(),register.getUsername(),register.getEmail(),register.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set default user role => ROLE_USER
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER )
                .orElseThrow(()-> new InternalServerException("User Role Not set.!"));

        user.setRoles(Collections.singleton(userRole)); //  used to return an immutable set containing

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "User registered successfully"));

    }

}
