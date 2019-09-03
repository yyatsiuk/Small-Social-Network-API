package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.request.PostAddRequest;
import com.yyatsiuk.social.sonet.dto.request.PostUpdateRequest;
import com.yyatsiuk.social.sonet.model.content.Post;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    Post findById(Long id) throws DataAccessException;

    Post create(PostAddRequest postAddRequest) throws DataAccessException;

    void delete(Long id);

    Post update(PostUpdateRequest postUpdateRequest);

    List<Post> findNews(Long idUser, Pageable pageable);

    List<Post> findAll(Long idActor, Pageable pageable);

    List<Post> findTopPosts(Pageable pageable);

    List<Post> findLikedPosts(Long userId, Pageable pageable);

    List<Post> findLikedPostsOfFollowing(Long userId, Pageable pageable);

    List<Post> findByText (String text, Pageable pageable);

}
