package com.yyatsiuk.social.sonet.dto.model;

import lombok.Data;

import java.util.List;

@Data
public class ImageListDTO {

    private int totalPages;
    private List<ImageDTO> images;

}
