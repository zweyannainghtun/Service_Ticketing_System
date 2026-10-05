package com.digitalbase.ticketing.controller;

import com.digitalbase.ticketing.dto.*;
import com.digitalbase.ticketing.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        // Plain String အစား {"message": "OTP has been sent..."} ပုံစံ JSON ဖြင့် ပြန်ပေးမည်
        return ResponseEntity.ok(Map.of("message", authService.forgotPassword(request)));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody VerifyOtpRequest request) {
        return ResponseEntity.ok(Map.of("token", authService.verifyOtp(request)));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(Map.of("message", authService.resetPassword(request)));
    }
}