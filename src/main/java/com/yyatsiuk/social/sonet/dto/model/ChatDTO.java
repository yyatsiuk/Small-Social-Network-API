package com.yyatsiuk.social.sonet.dto.model;

import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.Image;
import com.yyatsiuk.social.sonet.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO implements DTO{

    protected Long id;

    private String name;

    private LocalDateTime creationTime;

    protected LocalDateTime updateTime;

    private List<User> members;

    private List<Channel> channels;

    private Image image;

}
