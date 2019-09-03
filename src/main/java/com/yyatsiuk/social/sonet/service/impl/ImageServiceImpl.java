package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.request.AddImageRequest;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.Actor;
import com.yyatsiuk.social.sonet.model.Image;
import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.repository.ImageRepo;
import com.yyatsiuk.social.sonet.service.ActorService;
import com.yyatsiuk.social.sonet.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepo imageRepo;

    private final ActorService actorService;

    @Autowired
    public ImageServiceImpl(ImageRepo imageRepo, ActorService actorService) {
        this.imageRepo = imageRepo;
        this.actorService = actorService;
    }

    @Override
    public boolean isImageOwner(Long userId, Long imageId) {
        return imageRepo.getImagesCount(userId, imageId) != 0;
    }

    @Override
    public Image saveImage(String url, String caption) {
        Image image = new Image();

        image.setUrl(url);
        image.setCaption(caption);

        return imageRepo.save(image);
    }

    @Override
    public Image findByUrl(String url) {
        Image image = imageRepo.findImageByUrl(url);

        if (image == null) {
            throw new EntityNotFoundException("Image with url:{0} not found", url);
        }

        return image;
    }

    @Override
    public Image save(AddImageRequest request) {
        Image image = new Image();
        image.setUrl(request.getUrl());
        image.setCaption(request.getCaption());
        image = imageRepo.save(image);

        Actor actor = actorService.findById(request.getActorId());
        List<Image> images = new ArrayList<>(actor.getImages());
        images.add(image);
        actor.setImages(images);
        actorService.save(actor);

        return image;
    }

    @Override
    public Page<Image> getActorImages(Long actorId, Pageable pageable) {
        return imageRepo.getActorImages(actorId, pageable);
    }

    @Override
    public Page<Image> getActorImagesByCaptionLike(Long actorId, String caption, Pageable pageable) {
        return imageRepo.getActorImagesByCaptionLike(actorId, caption, pageable);
    }

    @Override
    public void deleteImage(Long imageId) {
        Image image = imageRepo.getOne(imageId);
        image.setStatus(Status.DELETED);
        imageRepo.save(image);
    }
}
