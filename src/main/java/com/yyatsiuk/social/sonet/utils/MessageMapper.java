package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.model.MessageDTO;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.content.Message;
import com.yyatsiuk.social.sonet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper implements CustomModelMapper<Message, MessageDTO> {

    private final UserService userService;

    @Autowired
    public MessageMapper(UserService userService) {
        this.userService = userService;
    }

    @Override
    public MessageDTO toDTO(Message message) {
        if(message == null){
            return null;
        }
        User user = userService.getUserById(message.getCreator().getId());
        return new MessageDTO(message.getId(),
                user.getAvatar().getUrl(),
                message.getText(),
                user.getFullName(),
                user.getId(),
                message.getCreationTime());
    }
}
