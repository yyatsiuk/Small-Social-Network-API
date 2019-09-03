package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.model.CommentDTO;
import com.yyatsiuk.social.sonet.dto.request.CreateCommentRequest;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.content.Comment;
import com.yyatsiuk.social.sonet.model.content.Post;
import com.yyatsiuk.social.sonet.repository.CommentRepo;
import com.yyatsiuk.social.sonet.service.CommentService;
import com.yyatsiuk.social.sonet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepo commentRepo, UserService userService) {
        this.commentRepo = commentRepo;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Comment saveComment(CreateCommentRequest request) {

        User user = userService.getUserById(request.getCreatorId());

        Comment comment = new Comment();
        Post post = new Post();

        post.setId(request.getPostId());

        comment.setUpdateTime(LocalDateTime.now());
        comment.setCreationTime(LocalDateTime.now());
        comment.setCreator(user);
        comment.setText(request.getText());
        comment.setPost(post);

        commentRepo.save(comment);

        return comment;
    }


    @Override
    public List<Comment> findAllCommentsByPostId(Long postId, Pageable paging) {

        Page<Comment> commentEntities = commentRepo.findAllByPostId(paging, postId);

        return commentEntities.getContent();
    }

    @Override
    public List<Comment> findAllCommentsByPostIdAndCreatorId(Long postId, Long userId) {

        return commentRepo.findAllByPostIdAndCreatorId(postId, userId);
    }

    @Override
    public void deleteCommentById(Long id) throws EntityNotFoundException {
        try {
            commentRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Comment with id:{0} not found", id);
        }
    }

    @Override
    public List<Comment> findAll() throws EntityNotFoundException {

        List<Comment> commentEntities = commentRepo.findAll();

        if (commentEntities.isEmpty()) {
            throw new EntityNotFoundException("Cannot find any comments");
        }

        return commentEntities;

    }


    @Override
    public Comment findCommentById(Long id) throws EntityNotFoundException {

        return commentRepo.findById(id).orElseThrow(getCommentNotFoundException(id));
    }

    @Override
    public Comment updateComment(Long id, CommentDTO commentToUpdate) throws EntityNotFoundException {

        Comment commentFromBD = findCommentById(id);

        commentFromBD.setText(commentToUpdate.getText());

        commentFromBD.setId(id);

        commentRepo.save(commentFromBD);

        return commentFromBD;

    }

    private Supplier<EntityNotFoundException> getCommentNotFoundException(Long id) {
        return () -> new EntityNotFoundException("Comment with id:{0} not found", id);
    }


}
