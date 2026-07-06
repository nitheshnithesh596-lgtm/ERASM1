package com.erasm.service;

import com.erasm.dto.LoginRequest;
import com.erasm.dto.LoginResponse;
import com.erasm.dto.RegisterRequest;
import com.erasm.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}