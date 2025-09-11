package com.shubham.stockmonitoring.profile.controller;

import com.shubham.stockmonitoring.profile.dto.ProfileRequest;
import com.shubham.stockmonitoring.profile.dto.ProfileResponse;
import com.shubham.stockmonitoring.profile.service.ProfileService;
import com.shubham.stockmonitoring.commons.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    
    private final ProfileService profileService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<ProfileResponse>> getProfile(@PathVariable Long userId) {
        ProfileResponse response = profileService.getProfile(userId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }
    
    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponse<ProfileResponse>> createProfile(
            @PathVariable Long userId,
            @Valid @RequestBody ProfileRequest request) {
        ProfileResponse response = profileService.createProfile(userId, request);
        return ResponseEntity.ok(BaseResponse.success("Profile created successfully", response));
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<BaseResponse<ProfileResponse>> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody ProfileRequest request) {
        ProfileResponse response = profileService.updateProfile(userId, request);
        return ResponseEntity.ok(BaseResponse.success("Profile updated successfully", response));
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<BaseResponse<String>> deleteProfile(@PathVariable Long userId) {
        profileService.deleteProfile(userId);
        return ResponseEntity.ok(BaseResponse.success("Profile deleted successfully", "OK"));
    }
    
    @GetMapping("/health")
    public ResponseEntity<BaseResponse<String>> health() {
        return ResponseEntity.ok(BaseResponse.success("Profile service is healthy", "OK"));
    }
}
