package com.yyatsiuk.social.sonet.controller;

import com.yyatsiuk.social.sonet.dto.model.MessageDTO;
import com.yyatsiuk.social.sonet.model.content.Message;
import com.yyatsiuk.social.sonet.service.MessageService;
import com.yyatsiuk.social.sonet.utils.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;


@Controller
public class SocketMessageController {

    private final MessageService messageService;
    private final CustomModelMapper<Message, MessageDTO> messageMapper;

    @Autowired
    public SocketMessageController(MessageService messageService,
                                   @Qualifier("messageMapper") CustomModelMapper<Message, MessageDTO> messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @MessageMapping("/message/{id}")
    @SendTo("/api/topic/{id}")
    public MessageDTO sendMessage(@Valid @Payload MessageDTO messageDTO, @DestinationVariable Long id){
        return messageMapper.toDTO(messageService.save(id, messageDTO));
    }

}
