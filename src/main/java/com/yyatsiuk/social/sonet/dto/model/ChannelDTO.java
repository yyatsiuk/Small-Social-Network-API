package com.yyatsiuk.social.sonet.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDTO implements DTO {

    private Long id;

    @NotNull
    private Long chatId;

    @NotBlank
    @Size(max = 100)
    private String name;

    private String type;

}
