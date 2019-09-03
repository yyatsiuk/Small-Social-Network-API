package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.request.CreateLikeRequest;
import com.yyatsiuk.social.sonet.model.like.Like;

import java.util.List;

public interface LikeService {

    void deleteLikeByUserIdAndPostId(Long postId, Long userId);

    Like findLikeByPostIdAndUserId(Long postId, Long userId);

    Like insertLike(CreateLikeRequest request);

    List<Like> findAllByPostId(Long id);
}
