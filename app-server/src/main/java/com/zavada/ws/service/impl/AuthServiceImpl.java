package com.zavada.ws.service.impl;

import com.zavada.ws.config.jwt.JwtTokenProvider;
import com.zavada.ws.domain.SigninRequest;
import com.zavada.ws.entity.UserEntity;
import com.zavada.ws.exception.NotFoundException;
import com.zavada.ws.repositories.UserRepository;
import com.zavada.ws.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String signin(SigninRequest request) {
        final Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);

        UserEntity userEntity = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found by email [" + request.getEmail() + "]"));
        userEntity.setLastLogin(LocalDateTime.now());
        userRepository.save(userEntity);

        return token;
    }

    @Override
    public Boolean checkTokenExpired(String token) {
        return jwtTokenUtil.isTokenExpired(token);
    }
}
