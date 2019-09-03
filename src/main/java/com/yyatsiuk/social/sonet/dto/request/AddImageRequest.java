package com.yyatsiuk.social.sonet.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class AddImageRequest {

    @JsonProperty("actor_id")
    private Long actorId;

    private String url;
    @Size(max = 255)

    private String caption;
}
