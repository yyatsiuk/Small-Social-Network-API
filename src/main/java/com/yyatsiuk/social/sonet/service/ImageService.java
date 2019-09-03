package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.request.AddImageRequest;
import com.yyatsiuk.social.sonet.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImageService {

    boolean isImageOwner(Long userId, Long imageId);

    Image saveImage(String url, String caption);

    Image findByUrl(String url);

    Image save(AddImageRequest request);

    Page<Image> getActorImages(Long actorId, Pageable pageable);

    Page<Image> getActorImagesByCaptionLike(Long actorId, String caption, Pageable pageable);

    void deleteImage(Long imageId);


}
