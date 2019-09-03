package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.model.MessageDTO;
import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.content.Message;
import com.yyatsiuk.social.sonet.repository.MessageRepo;
import com.yyatsiuk.social.sonet.service.ChannelService;
import com.yyatsiuk.social.sonet.service.MessageService;
import com.yyatsiuk.social.sonet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;
    private final ChannelService channelService;
    private final UserService userService;

    @Autowired
    public MessageServiceImpl(MessageRepo messageRepo, ChannelService channelService, UserService userService) {
        this.messageRepo = messageRepo;
        this.channelService = channelService;
        this.userService = userService;
    }

    @Override
    public Message getTop1ByChannelOrderByCreationTimeDesc(Channel channel) {
        return messageRepo.getTop1ByChannelOrderByCreationTimeDesc(channel);
    }

    @Override
    @Transactional
    public Message save(Long channelId, MessageDTO messageDTO) {
        Message message = new Message();
        Channel channel = channelService.getChannelById(channelId);

        message.setChannel(channel);
        message.setText(messageDTO.getContent());
        message.setStatus(Status.ACTIVE);

        User user = userService.getUserById(messageDTO.getSenderId());

        message.setCreator(user);

        channel.getMessages().add(message);

        message.setCreationTime(LocalDateTime.now());
        message.setUpdateTime(LocalDateTime.now());

        messageRepo.save(message);

        return message;
    }

}
