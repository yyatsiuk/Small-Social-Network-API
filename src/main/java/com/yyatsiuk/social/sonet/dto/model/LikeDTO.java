package com.yyatsiuk.social.sonet.dto.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class LikeDTO implements DTO{

    private Long id;

    private LocalDateTime creationTime;
    @JsonProperty("post_id")
    private Long postId;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("user_name")
    private String nickname;
}
