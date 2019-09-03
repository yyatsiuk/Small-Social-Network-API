package com.yyatsiuk.social.sonet.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PollAddRequest {

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("creator_id")
    private Long creatorId;

    private String question;

    private List<String> choices;

}
