package com.yyatsiuk.social.sonet.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FacebookAccessTokenRequest {

    @NotNull
    @JsonProperty("token")
    private String accessToken;

}
