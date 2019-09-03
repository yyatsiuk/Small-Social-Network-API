package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.model.PostDTO;
import com.yyatsiuk.social.sonet.model.Image;
import com.yyatsiuk.social.sonet.model.content.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component("postMapper")
public class PostMapper implements CustomModelMapper<Post, PostDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDTO toDTO(Post post) {
        PostDTO postDTO =  modelMapper.map(post, PostDTO.class);
        postDTO.setCreatorId(post.getCreator().getId());
        postDTO.setText(post.getText());
        postDTO.setNickname(post.getOwner().getNickname());
        postDTO.setFirstName(post.getCreator().getFirstName());
        postDTO.setLastName(post.getCreator().getLastName());
        postDTO.setAvatarUrl(post.getOwner()
                .getAvatar()
                .getUrl());
        postDTO.setImages(post.getImages()
                .stream()
                .map(Image::getUrl)
                .collect(Collectors.toList()));
        postDTO.setCreationTime(post.getCreationTime());
        postDTO.setUpdateTime(post.getUpdateTime());
        return postDTO;
    }
}
