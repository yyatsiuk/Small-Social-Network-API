package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.model.MessageDTO;
import com.yyatsiuk.social.sonet.dto.response.ChatResponse;
import com.yyatsiuk.social.sonet.model.Chat;
import com.yyatsiuk.social.sonet.model.content.Message;
import com.yyatsiuk.social.sonet.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper implements CustomModelMapper<Chat, ChatResponse> {

    private final MessageService messageService;
    private final CustomModelMapper<Message, MessageDTO> messageMapper;

    @Autowired
    public ChatMapper(MessageService messageService, CustomModelMapper<Message, MessageDTO> messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @Override
    public ChatResponse toDTO(Chat chat) {
        return new ChatResponse(chat.getId(),
                                chat.getName(),
                                messageMapper.toDTO(messageService.getTop1ByChannelOrderByCreationTimeDesc(chat.getChannels().get(0))));
    }
}
