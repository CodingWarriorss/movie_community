package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.dto.payload.ApiResponse;
import com.codeworrisors.Movie_Community_Web.dto.payload.AuthResponse;
import com.codeworrisors.Movie_Community_Web.dto.payload.LoginRequest;
import com.codeworrisors.Movie_Community_Web.dto.payload.SignUpRequest;
import com.codeworrisors.Movie_Community_Web.exception.BadRequestException;
import com.codeworrisors.Movie_Community_Web.model.AuthProvider;
import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import com.codeworrisors.Movie_Community_Web.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody SignUpRequest signUpRequest) {
        if(memberRepository.existsByMemberName(signUpRequest.getEmail())) {
            throw new BadRequestException("요청이 그지음");
        }

        // Creating user's account
        Member user = new Member();
        user.setName(signUpRequest.getName());
        user.setMemberName(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Member result = memberRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

}
