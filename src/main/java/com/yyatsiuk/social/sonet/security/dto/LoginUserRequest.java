package com.yyatsiuk.social.sonet.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserRequest implements LoginRequest {

    @Email(message = "Email should be valid")
    @NotBlank
    private String email;

    @Size(min = 8, max = 32)
    @NotBlank
    private String password;

}
