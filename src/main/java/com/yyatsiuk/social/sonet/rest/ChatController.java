package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.model.ChannelDTO;
import com.yyatsiuk.social.sonet.dto.request.CreateChatRequest;
import com.yyatsiuk.social.sonet.dto.response.ChatResponse;
import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.Chat;
import com.yyatsiuk.social.sonet.service.ChatService;
import com.yyatsiuk.social.sonet.utils.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringElInspection")
@RestController
@RequestMapping("api/chats")
public class ChatController {

    private final ChatService chatService;
    private final CustomModelMapper<Channel, ChannelDTO> channelMapper;
    private final CustomModelMapper<Chat, ChatResponse> chatMapper;

    @Autowired
    public ChatController(ChatService chatService,
                          @Qualifier("channelMapper") CustomModelMapper<Channel, ChannelDTO> channelMapper,
                          @Qualifier("chatMapper") CustomModelMapper<Chat, ChatResponse> chatMapper) {
        this.chatService = chatService;
        this.channelMapper = channelMapper;
        this.chatMapper = chatMapper;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> createChat(@Valid @RequestBody CreateChatRequest chat){
        return ResponseEntity.ok(chatMapper.toDTO(chatService.create(chat)));
    }

    @PreAuthorize("isChatMember(#id)")
    @GetMapping(path = "{id}/channels")
    public ResponseEntity<List<ChannelDTO>> getChannels(@PathVariable Long id){

        return ResponseEntity.ok(chatService.getAllChannels(id)
                .stream()
                .map(channelMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity deleteChat(@PathVariable Long id, @PathVariable Long userId) {
        chatService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
