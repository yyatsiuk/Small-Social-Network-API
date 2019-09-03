package com.yyatsiuk.social.sonet.security;

import com.yyatsiuk.social.sonet.security.dto.JwtUser;
import com.yyatsiuk.social.sonet.service.*;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;

    private CommentService commentService;
    private GroupService groupService;
    private PostService postService;
    private ChatService chatService;
    private ImageService imageService;
    private ChannelService channelService;

    CustomSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean isCommentCreator(Long id) {

        return commentService
                .findCommentById(id)
                .getCreator()
                .getId().equals(getJwtUser().getId());
    }

    public boolean isGroupCreator(Long id) {
        return groupService
                .findById(id)
                .getCreator()
                .getId().equals(getJwtUser().getId());
    }

    public boolean isPostCreator(Long postId) {
        return postService
                .findById(postId)
                .getOwner()
                .getId().equals(getJwtUser().getId());
    }

    public boolean isChatMember(Long chatId) {

        Long authUserId = getJwtUser().getId();

        return chatService.getChatById(chatId)
                .getMembers()
                .stream()
                .map(member -> member.getId().equals(authUserId))
                .count() != 0;
    }

    public boolean isChannelMember(Long channelId){
        Long authUserId = getJwtUser().getId();

        return channelService.getChannelById(channelId).getChat()
                .getMembers()
                .stream()
                .map(member -> member.getId().equals(authUserId))
                .count() != 0;
    }

    public boolean isImageOwner(Long imageId) {
        Long authUserId = getJwtUser().getId();

        return imageService.isImageOwner(authUserId, imageId);
    }

    private JwtUser getJwtUser() {
        return (JwtUser) getPrincipal();
    }

    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    public Object getFilterObject() {
        return filterObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public Object getThis() {
        return target;
    }

    public void setThis(Object target) {
        this.target = target;
    }

    void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    void setPostService(PostService postService) {
        this.postService = postService;
    }

    void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }
}
