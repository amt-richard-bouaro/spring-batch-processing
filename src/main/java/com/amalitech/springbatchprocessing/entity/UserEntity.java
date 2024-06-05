package com.amalitech.springbatchprocessing.entity;

import com.amalitech.springbatchprocessing.config.CustomLocalDateDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserEntity {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @JsonProperty("user_id")
 private Long id;
 
 @NotNull
 @Size(min = 1, max = 50)
 private String username;
 
 @NotNull
 @Size(min = 1, max = 255)
 @JsonProperty("full_name")
 private String fullName;
 
 @NotNull
 @Size(min = 4, max = 50)
 private String gender;
 
 @NotNull
 @Past
 @JsonDeserialize(using = CustomLocalDateDeserializer.class)
 private LocalDate birthdate;
 
 @NotNull
 @Column(length = 1512)
 private String location;
 
 @Column(length = 1512)
 private String bio;
 
 @NotNull
 @URL
 @JsonProperty("profile_pic")
 @Column(length = 1512)
 private String profilePic;
 
 @NotNull
 @Min(0)
 @JsonProperty("followers_count")
 private Integer followersCount;
 
 @NotNull
 @Min(0)
 @JsonProperty("following_count")
 private Integer followingCount;
 
 @NotNull
 @Min(0)
 @JsonProperty("post_count")
 private Integer postCount;
 
 @NotNull
 @JsonProperty("last_login")
 @JsonDeserialize(using = CustomLocalDateDeserializer.class)
 private LocalDate lastLogin;
 
 @NotNull
 private String status;
 
 @NotNull
 @Size(min = 1, max = 1500)
 @Column(length = 1500)
 private String interests;
 
 @NotNull
 @URL
 @Size(min = 1, max = 3000)
 @Column(length = 3000)
 private String website;
 
 @NotNull
 @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
 @JsonProperty("phone_number")
 private String phoneNumber;
 
 @NotNull
 @JsonProperty("email_verified")
 private Boolean emailVerified;
 
 @NotNull
 @Size(min = 1, max = 150)
 @JsonProperty("account_type")
 private String accountType;
 
 @NotNull
 @Size(min = 1, max = 20)
 @JsonProperty("privacy_settings")
 private String privacySettings;
 
 @NotNull
 @JsonDeserialize(using = CustomLocalDateDeserializer.class)
 @JsonProperty("join_date")
 private LocalDate joinDate;
 
 @NotNull
 @Column(length = 1512)
 private String education;
 
 @NotNull
 @JsonProperty("work_experience")
 @Column(length = 1512)
 private String workExperience;
 
 @NotNull
 @JsonProperty("relationship_status")
 @Column(length = 1512)
 private String relationshipStatus;
 
 @NotNull
 @JsonProperty("favorite_books")
 @Column(length = 1512)
 private String favoriteBooks;
 
 @NotNull
 @JsonProperty("favorite_movies")
 @Column(length = 1512)
 private String favoriteMovies;
 
 @NotNull
 @JsonProperty("favorite_music")
 @Column(length = 1512)
 private String favoriteMusic;
 
 @NotNull
 @JsonProperty("online_status")
 private Boolean onlineStatus;
 
 @NotNull
 private String timezone;
 
 @NotNull
 @Column(length = 1512)
 private String language;
 
 @NotNull
 @Min(0)
 @JsonProperty("profile_views")
 private Integer profileViews;
}

