package com.sunbase.customerApp.service;


import com.sunbase.customerApp.dto.AuthRequest;
import com.sunbase.customerApp.dto.AuthResponse;
import com.sunbase.customerApp.dto.RegistrationRequest;

public interface AuthenticationService {

    AuthResponse register(RegistrationRequest request);

    AuthResponse authenticate(AuthRequest request);
}