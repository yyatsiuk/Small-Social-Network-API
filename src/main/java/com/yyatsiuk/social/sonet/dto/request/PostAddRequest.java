package com.yyatsiuk.social.sonet.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostAddRequest {

    @JsonProperty("creator_id")
    private Long creatorId;

    @JsonProperty("owner_id")
    private Long ownerId;

    @Size(max = 500)
    private String text;

    @Size(max = 10)
    private List<String> images;

}
