package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.response.UserResponse;
import com.yyatsiuk.social.sonet.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper implements CustomModelMapper<User, UserResponse> {

    private final ModelMapper modelMapper;

    @Autowired
    public UserResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse toDTO(User user) {
        return new UserResponse(user.getId(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getAvatar().getUrl());
    }
}
