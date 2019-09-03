package com.yyatsiuk.social.sonet.dto.model;

import lombok.Data;

@Data
public class ImageDTO implements DTO {

    private Long id;

    private String url;
    private String caption;
}
