package com.yyatsiuk.social.sonet.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateCommentRequest {

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("creator_id")
    private Long creatorId;

    private String text;

}
