package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.model.CommentDTO;
import com.yyatsiuk.social.sonet.dto.request.CreateCommentRequest;
import com.yyatsiuk.social.sonet.model.content.Comment;
import com.yyatsiuk.social.sonet.service.CommentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("SpringElInspection")
@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity createComment(@RequestBody CreateCommentRequest request) {
        Comment comment = commentService.saveComment(request);

        return new ResponseEntity<>(modelMapper.map(comment, CommentDTO.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<Comment> comments = commentService.findAll();

        List<CommentDTO> commentDTOS = modelMapper.map(comments,
                new TypeToken<List<CommentDTO>>() {}.getType());

        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/posts/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable("postId") Long postId,
                                                                   Pageable paging) {
        List<Comment> comments = commentService.findAllCommentsByPostId(postId, paging);

        List<CommentDTO> commentDTOS = modelMapper.map(comments,
                new TypeToken<List<CommentDTO>>() {}.getType());

        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @GetMapping("/posts/postId/users/userId")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostIdAndUserId(@RequestParam("postId") Long postId,
                                                                            @RequestParam("userId") Long userId) {
        List<Comment> comments = commentService.findAllCommentsByPostIdAndCreatorId(postId, userId);

        List<CommentDTO> commentDTOS = modelMapper.map(comments,
                new TypeToken<List<CommentDTO>>() {}.getType());

        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity getCommentById(@PathVariable("commentId") Long commentId) {
        Comment comment = commentService.findCommentById(commentId);

        return new ResponseEntity<>(modelMapper.map(comment, CommentDTO.class), HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    @PreAuthorize("isCommentCreator(#commentId)")
    public ResponseEntity updateComment(@PathVariable("commentId") Long commentId,
                                        @RequestBody CommentDTO commentToUpdate) {
        Comment comment = commentService.updateComment(commentId, commentToUpdate);

        return new ResponseEntity<>(modelMapper.map(comment, CommentDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("isCommentCreator(#commentId)")
    public ResponseEntity deleteCommentById(@PathVariable("commentId") Long commentId) {
        commentService.deleteCommentById(commentId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
