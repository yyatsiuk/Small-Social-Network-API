package com.yyatsiuk.social.sonet.security.service.impl;

import com.yyatsiuk.social.sonet.model.Image;
import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.security.dto.FacebookAccessTokenRequest;
import com.yyatsiuk.social.sonet.security.dto.FacebookUserInfo;
import com.yyatsiuk.social.sonet.security.dto.LoginRequest;
import com.yyatsiuk.social.sonet.security.dto.SocialLoginRequest;
import com.yyatsiuk.social.sonet.security.service.FacebookSecurityService;
import com.yyatsiuk.social.sonet.security.util.UserFactory;
import com.yyatsiuk.social.sonet.service.ImageService;
import com.yyatsiuk.social.sonet.service.UserService;
import com.yyatsiuk.social.sonet.utils.ImageCaption;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class FacebookSecurityServiceImpl implements FacebookSecurityService {

    private final UserService userService;
    private final UserFactory userFactory;
    private final ImageService imageService;

    public FacebookSecurityServiceImpl(
            UserService userService,
            UserFactory userFactory,
            ImageService imageService
    ) {
        this.userService = userService;
        this.userFactory = userFactory;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public LoginRequest authenticateFacebookUser(FacebookAccessTokenRequest facebookRequest) {
        User user;
        FacebookUserInfo facebookUser = isValidToken(facebookRequest.getAccessToken());
        LoginRequest loginRequest = new SocialLoginRequest();

        if (StringUtils.isEmpty(facebookUser.getEmail())) {

            if (userService.existByEmail(facebookUser.getId())) {
                return updateCurrentUser(facebookUser.getId(), facebookUser, loginRequest);
            } else {
                user = userFactory.toUserFromSocialUser(facebookUser);
                user.setEmail(facebookUser.getId());
                userService.save(user);

                loginRequest.setEmail(facebookUser.getId());

                return loginRequest;
            }

        } else if (!userService.existByEmail(facebookUser.getEmail())) {
            user = userFactory.toUserFromSocialUser(facebookUser);
            userService.save(user);

            loginRequest.setEmail(user.getEmail());
            return loginRequest;

        } else {
            return updateCurrentUser(facebookUser.getEmail(), facebookUser, loginRequest);
        }
    }

    private FacebookUserInfo isValidToken(String token) {
        Facebook facebook = new FacebookTemplate(token);
        String[] fields = {"email", "first_name", "last_name", "name", "picture", "id"};

        return facebook.fetchObject("me", FacebookUserInfo.class, fields);
    }

    private LoginRequest updateCurrentUser(
            String emailOrId, FacebookUserInfo facebookUser, LoginRequest loginRequest) {

        User user = userService
                .findUserByEmail(emailOrId);

        if (user.getStatus().equals(Status.NOT_ACTIVE)) {
            user.setStatus(Status.ACTIVE);
            if (user.getAvatar() == null) {

                String facebookImageURL = facebookUser
                        .getPicture()
                        .getData()
                        .getUrl();

                Image savedImage = imageService.saveImage(facebookImageURL, ImageCaption.FACEBOOK);
                user.setAvatar(savedImage);
                user = userService.save(user);
            }
        }
        loginRequest.setEmail(user.getEmail());
        return loginRequest;
    }
}
