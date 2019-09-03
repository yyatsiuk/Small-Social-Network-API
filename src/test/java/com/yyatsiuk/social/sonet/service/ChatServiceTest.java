package com.yyatsiuk.ita.sonet.service;

import com.yyatsiuk.ita.sonet.dto.request.CreateChatRequest;
import com.yyatsiuk.ita.sonet.model.Chat;
import com.yyatsiuk.ita.sonet.model.User;
import com.yyatsiuk.ita.sonet.repository.ChatRepo;
import com.yyatsiuk.ita.sonet.service.impl.ChatServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatServiceTest {

    @Mock
    ChatRepo chatRepo;

    @Mock
    UserService userService;

    @InjectMocks
    ChatServiceImpl chatService;

    @Test
    public void createChatShouldReturnCreatedChat() {

        CreateChatRequest request = new CreateChatRequest();
        request.setCreatorId(1L);
        request.setConversationNameLabel("test label");
        request.setCheckedFriends(new ArrayList<>(Arrays.asList(2L, 3L)));

        User user = new User();
        user.setChats(new ArrayList<>());

        when(userService.getUserById(any(Long.class))).thenReturn(user);
        when(chatRepo.save(any(Chat.class))).thenReturn(new Chat());

        Chat created = chatService.create(request);

        assertThat(created.getName()).isSameAs(request.getConversationNameLabel());
    }
}
