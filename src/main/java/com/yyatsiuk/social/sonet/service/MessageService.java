package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.model.MessageDTO;
import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.content.Message;

public interface MessageService {

    Message save(Long id, MessageDTO messageDTO);

    Message getTop1ByChannelOrderByCreationTimeDesc(Channel channel);

}
