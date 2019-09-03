package com.yyatsiuk.social.sonet.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostUpdateRequest {

    private Long id;

    private String text;

    private List<String> images;

}
