package com.yyatsiuk.social.sonet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ErrorMessage {

    private LocalDateTime dataTime;

    private String messages;

}
