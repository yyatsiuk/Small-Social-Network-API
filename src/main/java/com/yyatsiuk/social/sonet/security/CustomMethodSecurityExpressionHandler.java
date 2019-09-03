package com.yyatsiuk.social.sonet.security;

import com.yyatsiuk.social.sonet.service.impl.*;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private ApplicationContext applicationContext;

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication,
            MethodInvocation invocation
    ) {
        final CustomSecurityExpressionRoot root = new CustomSecurityExpressionRoot(authentication);
        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(new AuthenticationTrustResolverImpl());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setDefaultRolePrefix("ROLE_");
        root.setGroupService(applicationContext.getBean(GroupServiceImpl.class));
        root.setChatService(applicationContext.getBean(ChatServiceImpl.class));
        root.setCommentService(applicationContext.getBean(CommentServiceImpl.class));
        root.setPostService(applicationContext.getBean(PostServiceImpl.class));
        root.setImageService(applicationContext.getBean(ImageServiceImpl.class));
        root.setChannelService(applicationContext.getBean(ChannelServiceImpl.class));

        return root;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;

    }
}
