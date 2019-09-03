package com.yyatsiuk.social.sonet.dto.response;

import com.yyatsiuk.social.sonet.dto.model.DTO;
import com.yyatsiuk.social.sonet.dto.model.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse implements DTO {

    private Long id;

    private String title;

    private MessageDTO lastMessage;

}
