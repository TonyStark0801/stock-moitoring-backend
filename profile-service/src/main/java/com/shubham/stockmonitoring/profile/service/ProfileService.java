package com.shubham.stockmonitoring.profile.service;

import com.shubham.stockmonitoring.profile.dto.ProfileRequest;
import com.shubham.stockmonitoring.profile.dto.ProfileResponse;
import com.shubham.stockmonitoring.profile.entity.UserProfile;
import com.shubham.stockmonitoring.profile.repository.UserProfileRepository;
import com.shubham.stockmonitoring.commons.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    
    private final UserProfileRepository profileRepository;
    
    public ProfileResponse getProfile(Long userId) {
        UserProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new BusinessException("PROFILE_NOT_FOUND", "Profile not found for user: " + userId));
        
        return mapToResponse(profile);
    }
    
    public ProfileResponse createProfile(Long userId, ProfileRequest request) {
        if (profileRepository.existsByUserId(userId)) {
            throw new BusinessException("PROFILE_EXISTS", "Profile already exists for user: " + userId);
        }
        
        UserProfile profile = new UserProfile();
        profile.setUserId(userId);
        mapRequestToEntity(request, profile);
        
        UserProfile savedProfile = profileRepository.save(profile);
        return mapToResponse(savedProfile);
    }
    
    public ProfileResponse updateProfile(Long userId, ProfileRequest request) {
        UserProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new BusinessException("PROFILE_NOT_FOUND", "Profile not found for user: " + userId));
        
        mapRequestToEntity(request, profile);
        UserProfile updatedProfile = profileRepository.save(profile);
        
        return mapToResponse(updatedProfile);
    }
    
    public void deleteProfile(Long userId) {
        UserProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new BusinessException("PROFILE_NOT_FOUND", "Profile not found for user: " + userId));
        
        profileRepository.delete(profile);
    }
    
    private void mapRequestToEntity(ProfileRequest request, UserProfile profile) {
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setAddress(request.getAddress());
        profile.setCity(request.getCity());
        profile.setCountry(request.getCountry());
        profile.setProfilePictureUrl(request.getProfilePictureUrl());
    }
    
    private ProfileResponse mapToResponse(UserProfile profile) {
        return new ProfileResponse(
            profile.getId(),
            profile.getUserId(),
            profile.getFirstName(),
            profile.getLastName(),
            profile.getPhoneNumber(),
            profile.getAddress(),
            profile.getCity(),
            profile.getCountry(),
            profile.getDateOfBirth(),
            profile.getProfilePictureUrl(),
            profile.getCreatedAt(),
            profile.getUpdatedAt()
        );
    }
}
