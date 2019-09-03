package com.yyatsiuk.social.sonet.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yyatsiuk.social.sonet.dto.model.PostDTO;
import lombok.Data;

import java.util.List;

@Data
public class PostPageableResponse {

    @JsonProperty("posts")
    private List<PostDTO> postDTOList;

    @JsonProperty("total_pages")
    private Integer numberOfPages;
}
