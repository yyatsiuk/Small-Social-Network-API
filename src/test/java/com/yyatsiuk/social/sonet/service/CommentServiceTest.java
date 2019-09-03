package com.yyatsiuk.ita.sonet.service;

import com.yyatsiuk.ita.sonet.dto.model.CommentDTO;
import com.yyatsiuk.ita.sonet.dto.request.CreateCommentRequest;
import com.yyatsiuk.ita.sonet.model.User;
import com.yyatsiuk.ita.sonet.model.content.Comment;
import com.yyatsiuk.ita.sonet.repository.CommentRepo;
import com.yyatsiuk.ita.sonet.service.impl.CommentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @Mock
    CommentRepo commentRepo;

    @Mock
    UserService userService;

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    public void saveCommentMustReturnCreatedComment() {
        CreateCommentRequest request = new CreateCommentRequest();
        request.setCreatorId(1L);
        request.setPostId(1L);
        request.setText("Testing text");

        when(userService.getUserById(any(Long.class))).thenReturn(new User());
        when(commentRepo.save(any(Comment.class))).thenReturn(new Comment());

        Comment createdComment = commentService.saveComment(request);

        assertThat(createdComment.getText()).isSameAs(request.getText());
    }

    @Test
    public void updateCommentMustReturnUpdatedComment() {
        CommentDTO commentToUpdate = new CommentDTO();
        commentToUpdate.setId(1L);
        commentToUpdate.setText("Update Text");

        when(commentRepo.findById(any(Long.class))).thenReturn(Optional.of(new Comment()));
        when(commentRepo.save(any(Comment.class))).thenReturn(new Comment());

        Comment updatedComment = commentService.updateComment(1L, commentToUpdate);

        assertThat(updatedComment.getText()).isSameAs(commentToUpdate.getText());
    }

}
