package com.yyatsiuk.social.sonet.utils;

import com.yyatsiuk.social.sonet.dto.model.ImageDTO;
import com.yyatsiuk.social.sonet.model.Image;
import org.springframework.stereotype.Component;

@Component("imageMapper")
public class ImageMapper implements CustomModelMapper<Image, ImageDTO> {

    @Override
    public ImageDTO toDTO(Image entity) {
        ImageDTO imageDTO = new ImageDTO();

        imageDTO.setId(entity.getId());
        imageDTO.setUrl(entity.getUrl());
        imageDTO.setCaption(entity.getCaption());

        return imageDTO;
    }
}
