package com.yyatsiuk.social.sonet.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yyatsiuk.social.sonet.security.validation.FieldMatch;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserRequest {

    @NotBlank
    @Size(max = 100)
    private String nickname;

    @NotBlank
    @Size(max = 40)
    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 8, max = 32, message = "Password must be from 8 characters to 32")
    private String password;
    private String confirmPassword;


}
