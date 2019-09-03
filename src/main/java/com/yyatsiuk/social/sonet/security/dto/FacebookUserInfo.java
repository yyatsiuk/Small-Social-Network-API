package com.yyatsiuk.social.sonet.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yyatsiuk.social.sonet.dto.model.FacebookImageDTO;
import lombok.Data;

@Data
public class FacebookUserInfo {

    private String id;

    private FacebookImageDTO picture;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("name")
    private String name;

}
