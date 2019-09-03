package com.yyatsiuk.social.sonet.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO implements DTO{

    private Long id;

    @JsonProperty("creator_id")
    private Long creatorId;

    private String text;

    private String nickname;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private List<String> images;

    @JsonProperty("update_time")
    private LocalDateTime updateTime;

    @JsonProperty("creation_time")
    private LocalDateTime creationTime;

}


