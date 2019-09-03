package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.model.ImageDTO;
import com.yyatsiuk.social.sonet.dto.model.ImageListDTO;
import com.yyatsiuk.social.sonet.dto.request.AddImageRequest;
import com.yyatsiuk.social.sonet.model.Image;
import com.yyatsiuk.social.sonet.service.ImageService;
import com.yyatsiuk.social.sonet.utils.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@SuppressWarnings("SpringElInspection")
@RestController
@RequestMapping("api/images")
public class ImageController {

    private final ImageService imageService;
    private final CustomModelMapper<Image, ImageDTO> imageMapper;

    @Autowired
    public ImageController(ImageService imageService,
                           @Qualifier("imageMapper") CustomModelMapper<Image, ImageDTO> imageMapper) {
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @PostMapping
    public ResponseEntity<ImageDTO> saveImage(@RequestBody AddImageRequest request) {
        return ResponseEntity.ok(imageMapper.toDTO(imageService.save(request)));
    }

    @GetMapping("/find")
    public ResponseEntity<ImageListDTO> getByActor(@RequestParam("actorId") Long actorId,
                                                   @RequestParam(value = "caption", required = false) String caption,
                                                   Pageable pageable) {
        ImageListDTO imageListDTO = new ImageListDTO();
        Page<Image> images;

        if (caption == null) {
            images = imageService.getActorImages(actorId, pageable);
        } else {
            images = imageService.getActorImagesByCaptionLike(actorId, caption, pageable);
        }

        imageListDTO.setTotalPages(images.getTotalPages());
        imageListDTO.setImages(images.stream()
                .map(imageMapper::toDTO)
                .collect(Collectors.toList()));

        return ResponseEntity.ok(imageListDTO);
    }

    @DeleteMapping("/{imageId}")
    @PreAuthorize("isImageOwner(#imageId)")
    public ResponseEntity deleteImage(@PathVariable("imageId") Long imageId) {
        imageService.deleteImage(imageId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
