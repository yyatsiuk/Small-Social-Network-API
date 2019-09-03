package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.request.PostAddRequest;
import com.yyatsiuk.social.sonet.dto.request.PostUpdateRequest;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.Actor;
import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.content.Post;
import com.yyatsiuk.social.sonet.repository.PostRepo;
import com.yyatsiuk.social.sonet.service.ActorService;
import com.yyatsiuk.social.sonet.service.ImageService;
import com.yyatsiuk.social.sonet.service.PostService;
import com.yyatsiuk.social.sonet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final ActorService actorService;
    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public PostServiceImpl(PostRepo postRepo, ActorService actorService, UserService userService,
                           ImageService imageService) {
        this.postRepo = postRepo;
        this.actorService = actorService;
        this.userService = userService;
        this.imageService = imageService;
    }

    public Post findByIdAndStatus(Long id) throws EntityNotFoundException {
        Post post = postRepo.findByIdAndStatus(id, Status.ACTIVE);
        if(post == null) {
            throw new EntityNotFoundException("Post with id:{0} not found", id);
        }
        return post;
    }

    @Override
    @Transactional
    public Post findById(Long id) throws EntityNotFoundException  {
        return findByIdAndStatus(id);
    }

    @Override
    @Transactional
    public Post create(PostAddRequest postAddRequest) {
        Actor actor = actorService.findById(postAddRequest.getOwnerId());
        User user = userService.getUserById(postAddRequest.getCreatorId());
        Post post = new Post();
        post.setCreator(user);
        post.setOwner(actor);
        post.setText(postAddRequest.getText());
        post.setStatus(Status.ACTIVE);
        post.setCreationTime(LocalDateTime.now());
        post.setUpdateTime(LocalDateTime.now());
        post.setImages(postAddRequest.getImages()
                .stream()
                .map(imageService::findByUrl)
                .collect(Collectors.toList()));
        post = postRepo.save(post);
        return post;
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Post post = findByIdAndStatus(id);
        post.setStatus(Status.DELETED);
        postRepo.save(post);
    }

    @Override
    public Post update(PostUpdateRequest postUpdateRequest) throws EntityNotFoundException {
        Post postEntity = findByIdAndStatus(postUpdateRequest.getId());
        postEntity.setText(postUpdateRequest.getText());
        postEntity.setImages(postUpdateRequest.getImages()
                .stream()
                .map(imageService::findByUrl)
                .collect(Collectors.toList()));
        postEntity.setUpdateTime(LocalDateTime.now());
        postEntity = postRepo.save(postEntity);
        return postEntity;
    }

    @Override
    @Transactional
    public List<Post> findNews(Long idUser, Pageable pageable) {
        return postRepo.findNews(pageable, idUser).getContent();
    }


    @Override
    @Transactional
    public List<Post> findAll(Long idActor, Pageable pageable) {
        return postRepo.findAllByStatusAndOwnerId(Status.ACTIVE, idActor, pageable).getContent();
    }

    @Override
    @Transactional
    public List<Post> findTopPosts(Pageable pageable) {
        return postRepo.findTopPost(pageable).getContent();
    }

    @Override
    @Transactional
    public List<Post> findLikedPosts(Long userId, Pageable pageable) {
        return postRepo.findLikedPosts(pageable, userId).getContent();
    }

    @Override
    @Transactional
    public List<Post> findLikedPostsOfFollowing(Long userId, Pageable pageable) {
        return postRepo.findLikedPostsOfFollowing(pageable, userId).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findByText(String text, Pageable pageable) {
        return postRepo.findPostsByStatusAndTextContaining(Status.ACTIVE, text, pageable).getContent();
    }
}

