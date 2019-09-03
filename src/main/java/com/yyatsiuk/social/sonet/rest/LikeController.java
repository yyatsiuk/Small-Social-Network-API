package com.yyatsiuk.social.sonet.rest;


import com.yyatsiuk.social.sonet.dto.model.LikeDTO;
import com.yyatsiuk.social.sonet.dto.request.CreateLikeRequest;
import com.yyatsiuk.social.sonet.model.like.Like;
import com.yyatsiuk.social.sonet.service.LikeService;
import com.yyatsiuk.social.sonet.utils.CustomModelMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/likes")
public class LikeController {

    private final LikeService likeService;
    private final ModelMapper modelMapper;
    private final CustomModelMapper<Like, LikeDTO> likeCustomModelMapper;


    @Autowired
    public LikeController(LikeService likeService, @Qualifier("likeMapper") CustomModelMapper<Like, LikeDTO> likeCustomModelMapper, ModelMapper modelMapper) {
        this.likeCustomModelMapper = likeCustomModelMapper;
        this.likeService = likeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @PreAuthorize("#request.userId == authentication.principal.id")
    public ResponseEntity likeCreation(@RequestBody CreateLikeRequest request) {
        Like like = likeService.insertLike(request);
        return new ResponseEntity<>(modelMapper.map(like, LikeDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("posts/{postId}/users/{userId}")
    public ResponseEntity getLikeByPostIdAndUserId(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId) {
        Like like = likeService.findLikeByPostIdAndUserId(postId, userId);
        LikeDTO likeDTO = modelMapper.map(like, new TypeToken<LikeDTO>() {
        }.getType());
        return new ResponseEntity<>(likeDTO, HttpStatus.OK);
    }

    @GetMapping("posts/{postId}")
    public ResponseEntity getAllLikesByPostId(@PathVariable("postId") Long postId) {
        List<Like> likes = likeService.findAllByPostId(postId);
        List<LikeDTO> likeDTOS = likes.stream().map(likeCustomModelMapper::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(likeDTOS, HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/users/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity deleteLikeByPostIdAndUserId(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId) {
       likeService.deleteLikeByUserIdAndPostId(postId, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
