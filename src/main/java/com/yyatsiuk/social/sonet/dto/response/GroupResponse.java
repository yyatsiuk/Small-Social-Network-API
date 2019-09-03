package com.yyatsiuk.social.sonet.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {

    protected Long id;

    private String name;

    private String nickname;

    @JsonProperty("avatar_url")
    private String avatarUrl;

}
