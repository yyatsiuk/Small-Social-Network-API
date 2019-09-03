package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.model.CommentDTO;
import com.yyatsiuk.social.sonet.dto.request.CreateCommentRequest;
import com.yyatsiuk.social.sonet.model.content.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<Comment> findAllCommentsByPostId(Long postId, Pageable paging);

    List<Comment> findAllCommentsByPostIdAndCreatorId(Long postId, Long userId);

    List<Comment> findAll();

    Comment findCommentById(Long id);

    Comment updateComment(Long id, CommentDTO commentToUpdate);

    void deleteCommentById(Long id);

    Comment saveComment(CreateCommentRequest request);

}
