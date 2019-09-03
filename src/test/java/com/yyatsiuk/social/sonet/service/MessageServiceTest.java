package com.yyatsiuk.ita.sonet.service;

import com.yyatsiuk.ita.sonet.dto.model.MessageDTO;
import com.yyatsiuk.ita.sonet.model.Channel;
import com.yyatsiuk.ita.sonet.model.User;
import com.yyatsiuk.ita.sonet.model.content.Message;
import com.yyatsiuk.ita.sonet.repository.MessageRepo;
import com.yyatsiuk.ita.sonet.service.impl.MessageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @Mock
    ChannelService channelService;

    @Mock
    UserService userService;

    @Mock
    MessageRepo messageRepo;

    @InjectMocks
    MessageServiceImpl messageService;

    @Test
    public void saveMessageShouldReturnSavedMessage() {
        MessageDTO request = new MessageDTO();
        request.setContent("test content");
        request.setSenderId(1L);

        Channel channel = new Channel();
        channel.setMessages(new ArrayList<>());

        when(channelService.getChannelById(any(Long.class))).thenReturn(channel);
        when(userService.getUserById(any(Long.class))).thenReturn(new User());
        when(messageRepo.save(any(Message.class))).thenReturn(new Message());

        Message created = messageService.save(1L, request);

        assertThat(created.getText()).isSameAs(request.getContent());
    }

}
