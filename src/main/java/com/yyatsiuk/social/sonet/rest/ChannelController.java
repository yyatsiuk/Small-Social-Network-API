package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.model.ChannelDTO;
import com.yyatsiuk.social.sonet.dto.model.MessageDTO;
import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.content.Message;
import com.yyatsiuk.social.sonet.service.ChannelService;
import com.yyatsiuk.social.sonet.utils.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringElInspection")
@RestController
@RequestMapping("api/channels")
public class ChannelController {

    private final ChannelService channelService;
    private final CustomModelMapper<Message, MessageDTO> messageMapper;
    private final CustomModelMapper<Channel, ChannelDTO> channelMapper;

    @Autowired
    public ChannelController(ChannelService channelService,
                             @Qualifier("messageMapper") CustomModelMapper<Message, MessageDTO> messageMapper,
                             @Qualifier("channelMapper") CustomModelMapper<Channel, ChannelDTO> channelMapper) {
        this.channelService = channelService;
        this.messageMapper = messageMapper;
        this.channelMapper = channelMapper;
    }

    @PreAuthorize("isChatMember(#channelRequest.chatId)")
    @PostMapping
    public ResponseEntity<ChannelDTO> create(@Valid @RequestBody ChannelDTO channelRequest){
        return ResponseEntity.ok(channelMapper.toDTO(channelService.create(channelRequest)));
    }

    @PreAuthorize("isChannelMember(#id)")
    @GetMapping(path = "{id}/messages")
    public ResponseEntity<List<MessageDTO>> getAllMessages(@PathVariable Long id){

        return ResponseEntity.ok(channelService.getAllMessages(id)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList())
        );
    }

}
