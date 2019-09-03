package com.yyatsiuk.social.sonet.dto.request;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateChatRequest {

    private Long creatorId;

    @Size(max = 100)
    private String conversationNameLabel;

    @Size(min = 1)
    private List<Long> checkedFriends;

}
