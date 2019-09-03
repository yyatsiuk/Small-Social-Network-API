package com.yyatsiuk.social.sonet.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties
@Data
public class GroupDTO implements DTO {

    private Long id;

    private List<UserDTO> members;

    private String name;

    private String nickname;

    private String description;

    private Long creatorId;

    @JsonProperty("background_url")
    private String backgroundUrl;

    @JsonProperty("avatar_url")
    private String avatarUrl;

}
