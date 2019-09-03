package com.yyatsiuk.social.sonet.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdateGroupRequest {

    @Size(max = 255)
    @NotBlank
    private String name;

    @Size(max = 255)
    @NotBlank
    private String nickname;

    @Size(max = 255)
    @NotBlank
    private String description;

    @JsonProperty("background_url")
    private String backgroundURL;

    @JsonProperty("avatar_url")
    private String avatarURL;
}
