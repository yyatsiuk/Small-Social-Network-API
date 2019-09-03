package com.yyatsiuk.social.sonet.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yyatsiuk.social.sonet.dto.model.GroupDTO;
import lombok.Data;

import java.util.List;

@Data
public class GroupPageableResponse {

    @JsonProperty("groups")
    private List<GroupDTO> groupDTOList;

    @JsonProperty("total_pages")
    private Integer numberOfPages;

    @JsonProperty("total_amount")
    private Long amountOfGroups;

}
