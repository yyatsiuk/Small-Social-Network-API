package com.yyatsiuk.social.sonet.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateGroupRequest {

    @Size(max = 255)
    @NotBlank
    private String name;

    @Size(max = 55)
    @NotBlank
    private String nickname;

    @Size(max = 255)
    @NotBlank
    private String description;

    @JsonProperty("creator_id")
    @NotNull
    private Long creatorId;

    @JsonProperty("avatar_url")
    private String avatarURL;

    @JsonProperty("background_url")
    private String backgroundURL;

}
