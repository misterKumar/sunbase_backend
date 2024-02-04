package com.sunbase.customerApp.auth;



import com.sunbase.customerApp.dto.AuthRequest;
import com.sunbase.customerApp.dto.AuthResponse;
import com.sunbase.customerApp.dto.RegistrationRequest;
import com.sunbase.customerApp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sunbase/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    //     user registration
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegistrationRequest request
    ){
        return new ResponseEntity<>(service.register(request), HttpStatus.OK);
    }
    //    user login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ){
        return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
    }
}