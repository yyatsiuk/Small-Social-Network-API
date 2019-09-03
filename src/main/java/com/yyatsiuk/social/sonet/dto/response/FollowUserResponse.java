package com.yyatsiuk.social.sonet.dto.response;


import com.yyatsiuk.social.sonet.dto.model.DTO;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FollowUserResponse implements DTO {

    private Long followerId;

    private Long followingId;

    private LocalDateTime creationTime;

    private LocalDateTime updateTime;

    private Boolean isOnline;
}
