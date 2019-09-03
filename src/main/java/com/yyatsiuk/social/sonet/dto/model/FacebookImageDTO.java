package com.yyatsiuk.social.sonet.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacebookImageDTO implements DTO {

    private ImageData data;

    @Getter
    @Setter
    public static class ImageData {
        private String height;

        @JsonProperty("is_silhouette")
        private String imageProps;

        private String url;

        private String width;
    }
}
