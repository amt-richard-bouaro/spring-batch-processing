package com.amalitech.springbatchprocessing.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long user_id;
    
    @NotNull
    @Size(min = 1, max = 20)
    private String username;
    
    @NotNull
    @Size(min = 1, max = 100)
    private String fullName;
    
    @NotNull
    @Size(min = 4, max = 6)
    private String gender;
    
    @NotNull
    @Past
    private LocalDate birthdate;
    
    @NotNull
    @Size(min = 1, max = 50)
    private String location;
    
    @Size(max = 255)
    private String bio;
    
    @NotNull
    @Size(min = 1, max = 255)
    @URL
    private String profilePic;
    
    @NotNull
    @Min(0)
    private Integer followersCount;
    
    @NotNull
    @Min(0)
    private Integer followingCount;
    
    @NotNull
    @Min(0)
    private Integer postCount;
    
    @NotNull
    private LocalDate lastLogin;
    
    @NotNull
    @Size(min = 1, max = 20)
    private String status;
    
    @NotNull
    @Size(min = 1, max = 255)
    private String interests;
    
    @NotNull
    @URL
    private String website;
    
    @NotNull
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
    private String phoneNumber;
    
    @NotNull
    private Boolean emailVerified;
    
    @NotNull
    @Size(min = 1, max = 20)
    private String accountType;
    
    @NotNull
    @Size(min = 1, max = 20)
    private String privacySettings;
    
    @NotNull
    private LocalDate joinDate;
    
    @NotNull
    @Size(min = 1, max = 255)
    private String education;
    
    @NotNull
    @Size(min = 1, max = 255)
    private String workExperience;
    
    @NotNull
    @Size(min = 1, max = 20)
    private String relationshipStatus;
    
    @NotNull
    @Size(min = 1, max = 255)
    private String favoriteBooks;
    
    @NotNull
    @Size(min = 1, max = 255)
    private String favoriteMovies;
    
    @NotNull
    @Size(min = 1, max = 255)
    private String favoriteMusic;
    
    @NotNull
    private Boolean onlineStatus;
    
    @NotNull
    @Size(min = 1, max = 50)
    private String timezone;
    
    @NotNull
    @Size(min = 1, max = 20)
    private String language;
    
    @NotNull
    @Min(0)
    private Integer profileViews;
    
}

