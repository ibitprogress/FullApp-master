package com.zavada.ws.service;

import com.zavada.ws.domain.SigninRequest;

public interface AuthService {

    // void signup(SignupRequest request);

    String signin(SigninRequest request);

    Boolean checkTokenExpired(String token);
}
