package com.yyatsiuk.social.sonet.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UpdateLocationRequest {

    public UpdateLocationRequest(){
        updateTime = LocalDateTime.now();
    }

    @NotNull
    private Long userId;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    private LocalDateTime updateTime;
}
