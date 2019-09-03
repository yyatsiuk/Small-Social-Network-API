package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.request.CreateChatRequest;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.Chat;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.repository.ChatRepo;
import com.yyatsiuk.social.sonet.service.ChatService;
import com.yyatsiuk.social.sonet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepo chatRepo;
    private final UserService userService;

    @Autowired
    public ChatServiceImpl(ChatRepo chatRepo, UserService userService) {
        this.chatRepo = chatRepo;
        this.userService = userService;
    }

    @Override
    public Chat create(CreateChatRequest chatRequest) {

        Chat chat = new Chat();
        Long creatorId = chatRequest.getCreatorId();
        chat.setName(chatRequest.getConversationNameLabel());

        User creator = userService.getUserById(creatorId);

        chat.setCreator(creator);
        List<User> members = new ArrayList<>();

        chatRequest.getCheckedFriends()
                .stream()
                .map(userService::getUserById)
                .forEach(user ->  {
                    members.add(user);
                    user.getChats().add(chat);
                });

        members.add(creator);
        creator.getChats().add(chat);
        chat.setMembers(members);

        Channel channel = new Channel();
        channel.setName("Main");
        channel.setType("normal");
        channel.setChat(chat);

        chat.setChannels(new ArrayList<>(Collections.singleton(channel)));

        chatRepo.save(chat);

        return chat;
    }

    @Override
    public Chat getChatById(Long chatId) throws EntityNotFoundException {
        return chatRepo.findById(chatId)
                .orElseThrow(()->new EntityNotFoundException("Chat with id: " + chatId + " not found"));
    }

    @Override
    public List<Channel> getAllChannels(Long chatId) {
        return getChatById(chatId).getChannels();
    }

    @Override
    public void delete(Long id){
        chatRepo.delete(getChatById(id));
    }
}
