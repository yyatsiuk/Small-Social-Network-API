package com.yyatsiuk.social.sonet.dto.model;

import lombok.Data;

import java.util.Map;

@Data
public class PollDTO implements DTO {

    private Long id;

    private String question;

    private Map<Long, String> choices;

}
