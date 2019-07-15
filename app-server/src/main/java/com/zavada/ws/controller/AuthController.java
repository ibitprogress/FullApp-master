package com.zavada.ws.controller;

import com.zavada.ws.domain.SigninRequest;
import com.zavada.ws.domain.SigninResponse;
import com.zavada.ws.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SigninRequest signinRequest) {
        String token = authService.signin(signinRequest);
        return ResponseEntity.ok(new SigninResponse(token));
    }

    @GetMapping("check")
    public ResponseEntity<?> checkTokenExpired(@RequestParam String token) {
        return ResponseEntity.ok(authService.checkTokenExpired(token));
    }
}
