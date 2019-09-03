package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.model.ChannelDTO;
import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.content.Message;

import java.util.List;

public interface ChannelService {

    Channel create(ChannelDTO channelRequest);

    Channel save(Channel channel);

    List<Message> getAllMessages(Long channelId);

    Channel getChannelById(Long channelId);
}
