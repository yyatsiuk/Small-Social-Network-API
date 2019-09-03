package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.model.ChannelDTO;
import com.yyatsiuk.social.sonet.model.Channel;
import org.springframework.stereotype.Component;

@Component
public class ChannelMapper implements CustomModelMapper<Channel, ChannelDTO> {
    @Override
    public ChannelDTO toDTO(Channel channel) {
        return new ChannelDTO(channel.getId(), channel.getChat().getId(), channel.getName(), channel.getType());
    }
}
