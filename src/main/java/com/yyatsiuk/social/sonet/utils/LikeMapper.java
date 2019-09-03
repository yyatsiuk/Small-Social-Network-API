package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.model.DTO;
import com.yyatsiuk.social.sonet.dto.model.LikeDTO;
import com.yyatsiuk.social.sonet.dto.model.PostDTO;
import com.yyatsiuk.social.sonet.dto.model.UserDTO;
import com.yyatsiuk.social.sonet.model.entity.BaseEntity;
import com.yyatsiuk.social.sonet.model.like.Like;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("likeMapper")
public class LikeMapper implements CustomModelMapper {

    private final ModelMapper mapper;

    @Autowired
    public LikeMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public DTO toDTO(BaseEntity entity) {
        Like like = (Like) entity;
        LikeDTO likeDTO = new LikeDTO();
        UserDTO userDTO = mapper.map(like.getUser(), UserDTO.class);
        PostDTO postDTO = mapper.map(like.getPost(), PostDTO.class);
        likeDTO.setId(like.getUser().getId());
        likeDTO.setCreationTime(like.getCreationTime());
        likeDTO.setUserId(userDTO.getId());
        likeDTO.setPostId(postDTO.getId());
        likeDTO.setNickname(userDTO.getNickname());
        return likeDTO;
    }
}
