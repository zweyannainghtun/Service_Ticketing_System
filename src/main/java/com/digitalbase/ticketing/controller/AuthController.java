package com.digitalbase.ticketing.controller;

import com.digitalbase.ticketing.dto.*;
import com.digitalbase.ticketing.model.User;
import com.digitalbase.ticketing.service.AuthService;
import com.digitalbase.ticketing.service.DeviceTrackingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final DeviceTrackingService deviceTrackingService; // Device Tracking Service ကို Inject လုပ်ရန်

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        // ၁။ Email & Password စစ်ဆေး၍ Login ဝင်ခြင်း (Token ထုတ်ပေးခြင်း)
        AuthResponse response = authService.login(request);

        // ၂။ Login အောင်မြင်ပါက User Object ကိုယူ၍ Device Tracking လုပ်ခြင်း
        User loggedInUser = authService.getUserByEmail(request.getEmail());
        deviceTrackingService.trackDevice(loggedInUser, httpRequest);

        return ResponseEntity.ok(response);
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