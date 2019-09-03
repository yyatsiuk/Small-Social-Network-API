package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.model.UserDTO;
import com.yyatsiuk.social.sonet.dto.response.ChatResponse;
import com.yyatsiuk.social.sonet.dto.response.UserResponse;
import com.yyatsiuk.social.sonet.model.Chat;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.service.UserService;
import com.yyatsiuk.social.sonet.utils.CustomModelMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CustomModelMapper<Chat, ChatResponse> chatMapper;
    private final CustomModelMapper<User, UserResponse> userResponseMapper;

    @Autowired
    public UserController(UserService userService,
                          @Qualifier("chatMapper") CustomModelMapper<Chat, ChatResponse> chatMapper,
                          @Qualifier("userResponseMapper") CustomModelMapper<User, UserResponse> userResponseMapper,
                          ModelMapper modelMapper){
        this.userService = userService;
        this.chatMapper = chatMapper;
        this.userResponseMapper = userResponseMapper;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/{id}/conversations")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<List<ChatResponse>> getConversations(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getAllChats(id)
                .stream()
                .map(chat -> {
                    if (StringUtils.isEmpty(chat.getName())) {
                        chat.setName(chat.getNameForTwoUsers(id));
                    }
                    return chatMapper.toDTO(chat);
                })
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/{id}/following")
    public ResponseEntity<List<UserResponse>> getFollowing(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserFollowing(id)
                .stream()
                .map(userResponseMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @PatchMapping("{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") Long id, @RequestBody UserDTO userToUpdate) {
        User user = userService.updateUserInfo(id, userToUpdate);

        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);

        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/is-online")
    public ResponseEntity<UserDTO> isOnline(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<UserDTO>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllActiveUsers() {
        List<User> users = userService.getAllActiveUsers();

        List<UserDTO> userDTOS = modelMapper.map(users,
                new TypeToken<List<UserDTO>>() {}.getType());

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

}
