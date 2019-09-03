package com.yyatsiuk.social.sonet.security.util;

import com.yyatsiuk.social.sonet.model.Image;
import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.repository.RoleRepo;
import com.yyatsiuk.social.sonet.security.dto.FacebookUserInfo;
import com.yyatsiuk.social.sonet.security.dto.GoogleUserInfo;
import com.yyatsiuk.social.sonet.service.ImageService;
import com.yyatsiuk.social.sonet.service.UserService;
import com.yyatsiuk.social.sonet.utils.ImageCaption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserFactory {

    @Value("${default.background.url}")
    private String defaultBackgroundUrl;

    private final RoleRepo roleRepo;
    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public UserFactory(
            RoleRepo roleRepo,
            ImageService imageService,
            UserService userService
    ) {
        this.roleRepo = roleRepo;
        this.imageService = imageService;
        this.userService = userService;
    }

    public User toUserFromSocialUser(Object object) {

        if (object instanceof GoogleUserInfo) {
            GoogleUserInfo googleUser = (GoogleUserInfo) object;

            User user = new User();

            user.setRoles(Collections.singletonList(roleRepo.findByName("ROLE_USER")));
            user.setFirstName(googleUser.getName());
            user.setLastName(googleUser.getLastName());
            user.setEmail(googleUser.getEmail());
            user.setNickname(googleUser.getEmail());
            user.setAvatar(imageService.saveImage(googleUser.getPhotoUrl(), ImageCaption.GOOGLE));
            user.setBackground(imageService.findByUrl(defaultBackgroundUrl));
            user.setStatus(Status.ACTIVE);

            return user;
        }
        else if (object instanceof FacebookUserInfo) {

            FacebookUserInfo facebookUser = (FacebookUserInfo) object;
            User user = new User();

            user.setRoles(Collections.singletonList(roleRepo.findByName("ROLE_USER")));
            user.setFirstName(facebookUser.getFirstName());
            user.setLastName(facebookUser.getLastName());
            user.setEmail(facebookUser.getEmail());

            Image image = imageService.saveImage(facebookUser
                    .getPicture()
                    .getData()
                    .getUrl(), ImageCaption.FACEBOOK);

            user.setAvatar(image);
            user.setBackground(imageService.findByUrl(defaultBackgroundUrl));

            if (!userService.existByNickname(facebookUser.getName())) {
                user.setNickname(facebookUser.getName());
            } else {
                user.setNickname(facebookUser.getId());
            }

            user.setStatus(Status.ACTIVE);

            return user;

        }else {
            throw new IllegalArgumentException("Unsupported data type, expected " +
                    "GoogleUserInfo or FacebookUserInfo classes");
        }
    }


}
