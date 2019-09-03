package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.model.ChannelDTO;
import com.yyatsiuk.social.sonet.exception.*;
import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.content.Message;
import com.yyatsiuk.social.sonet.repository.ChannelRepo;
import com.yyatsiuk.social.sonet.service.ChannelService;
import com.yyatsiuk.social.sonet.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepo channelRepo;
    private final ChatService chatService;

    @Autowired
    public ChannelServiceImpl(ChannelRepo channelRepo, ChatService chatService) {
        this.channelRepo = channelRepo;
        this.chatService = chatService;
    }

    @Override
    public Channel create(ChannelDTO channelRequest) {
        if(channelRepo.existsByChatAndName(chatService.getChatById(channelRequest.getChatId()), channelRequest.getName())){
            throw new ChannelAlreadyExistException("Channel with such name already exists in this conversation");
        }

        Channel newChannel = new Channel();

        newChannel.setName(channelRequest.getName());
        newChannel.setType(channelRequest.getType());
        newChannel.setChat(chatService.getChatById(channelRequest.getChatId()));

        channelRepo.save(newChannel);

        return newChannel;
    }

    @Override
    public Channel save(Channel channel){
        return channelRepo.save(channel);
    }

    @Override
    public Channel getChannelById(Long id){
        return channelRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Channel with id:{0} not found", id));
    }

    @Override
    public List<Message> getAllMessages(Long channelId) {
        return getChannelById(channelId).getMessages();
    }
}
