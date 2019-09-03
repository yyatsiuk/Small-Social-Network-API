package com.yyatsiuk.ita.sonet.service;

import com.yyatsiuk.ita.sonet.dto.model.ChannelDTO;
import com.yyatsiuk.ita.sonet.model.Channel;
import com.yyatsiuk.ita.sonet.model.Chat;
import com.yyatsiuk.ita.sonet.repository.ChannelRepo;
import com.yyatsiuk.ita.sonet.service.impl.ChannelServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChannelServiceTest {

    @Mock
    ChannelRepo channelRepo;

    @Mock
    ChatService chatService;

    @InjectMocks
    ChannelServiceImpl channelService;

    @Test
    public void createChannelShouldReturnCreatedChat() {

        ChannelDTO request = new ChannelDTO();
        request.setName("test name");
        request.setType("test type");
        request.setChatId(1L);

        when(chatService.getChatById(any(Long.class))).thenReturn(new Chat());
        when(channelRepo.save(any(Channel.class))).thenReturn(new Channel());

        Channel created = channelService.create(request);

        assertThat(created.getName()).isSameAs(request.getName());
    }
}
