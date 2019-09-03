package com.yyatsiuk.social.sonet.dto.model;

import com.yyatsiuk.social.sonet.model.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO implements DTO {

    private Long id;

    private PostIdOnlyDTO post;

    private UserCreatorDTO creator;

    private String text;

    private LocalDateTime creationTime;

    private LocalDateTime updateTime;

    private Status status;

}
