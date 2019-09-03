package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.request.CreateLikeRequest;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.content.Post;
import com.yyatsiuk.social.sonet.model.like.Like;
import com.yyatsiuk.social.sonet.model.like.LikePrimaryKey;
import com.yyatsiuk.social.sonet.repository.LikeRepository;
import com.yyatsiuk.social.sonet.service.LikeService;
import com.yyatsiuk.social.sonet.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, ModelMapper modelMapper, UserService userService) {
        this.likeRepository = likeRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public Like insertLike(CreateLikeRequest request) {
        User user = userService.getUserById(request.getUserId());
        Like like = new Like();
        Post post = new Post();
        post.setId(request.getPostId());
        like.setPost(post);
        like.setUser(user);
        like.setCreationTime(LocalDateTime.now());
        like = likeRepository.save(like);
        return like;
    }

    @Override
    public void deleteLikeByUserIdAndPostId(Long postId, Long userId) {
        LikePrimaryKey likePrmKey = new LikePrimaryKey(userId, postId);
        likeRepository.deleteById(likePrmKey);
    }

    @Override
    public List<Like> findAllByPostId(Long id) {
        List<Like> likeEntities = likeRepository.findAllByPostId(id);
        return likeEntities;
    }

    @Override
    public Like findLikeByPostIdAndUserId(Long postId, Long userId) {
        LikePrimaryKey likePrmKey = new LikePrimaryKey(userId, postId);
        Like likeEntity = likeRepository.findById(likePrmKey).get();
        return modelMapper.map(likeEntity,
                new TypeToken<Like>() {
                }.getType());
    }
}
