package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.request.CreateChatRequest;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.Chat;

import java.util.List;


public interface ChatService {

  Chat create(CreateChatRequest chat);

  void delete(Long chatId);

  Chat getChatById(Long chatId) throws EntityNotFoundException;

  List<Channel> getAllChannels(Long chatId);
}
