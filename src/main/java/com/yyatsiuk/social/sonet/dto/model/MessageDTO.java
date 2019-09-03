package com.yyatsiuk.social.sonet.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements DTO {

    private Long id;

    private String avatar;

    @NotBlank
    private String content;

    private String sender;

    @NotNull
    private Long senderId;

    private LocalDateTime sendTime;

}
