package com.yyatsiuk.social.sonet.security.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.yyatsiuk.social.sonet.exception.GoogleAuthenticationException;
import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.security.dto.GoogleUserInfo;
import com.yyatsiuk.social.sonet.security.dto.LoginRequest;
import com.yyatsiuk.social.sonet.security.dto.SecuredUserDTO;
import com.yyatsiuk.social.sonet.security.dto.SocialLoginRequest;
import com.yyatsiuk.social.sonet.security.service.GoogleSecurityService;
import com.yyatsiuk.social.sonet.security.service.UserSecurityService;
import com.yyatsiuk.social.sonet.security.util.UserFactory;
import com.yyatsiuk.social.sonet.service.ImageService;
import com.yyatsiuk.social.sonet.service.UserService;
import com.yyatsiuk.social.sonet.utils.ImageCaption;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@PropertySource("classpath:security_resource/security.properties")
@Service
public class GoogleSecurityServiceImpl implements GoogleSecurityService {

    private final GoogleIdTokenVerifier verifier;
    private final UserSecurityService userSecurityService;
    private final UserService userService;
    private final UserFactory userFactory;
    private final ImageService imageService;
    private final ModelMapper mapper;


    @Autowired
    public GoogleSecurityServiceImpl(
            @Value("${google.client-id}") String googleClientId,
            @Lazy UserSecurityService userSecurityService,
            UserService userService,
            UserFactory userFactory,
            ImageService imageService,
            BCryptPasswordEncoder passwordEncoder,
            ModelMapper mapper
    ) {
        this.userSecurityService = userSecurityService;
        this.userService = userService;
        this.userFactory = userFactory;
        this.imageService = imageService;
        this.mapper = mapper;
        this.verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    @Override
    @Transactional
    public LoginRequest authenticateGoogleUser(GoogleUserInfo googleUser){

        if (googleUser == null) {
            throw new GoogleAuthenticationException("Google user data cannot be null");
        }

        if (!isValidToken(googleUser.getIdToken())) {
            throw new GoogleAuthenticationException("Google client id token has been expired");
        }

        LoginRequest request = new SocialLoginRequest();

        if (!userService.existByEmail(googleUser.getEmail())) {

            User userNew = userFactory.toUserFromSocialUser(googleUser);
            userService.save(userNew);

            request.setEmail(googleUser.getEmail());

        } else {

            SecuredUserDTO userUpdate = userSecurityService.findByEmail(googleUser.getEmail());

            if (userUpdate.getStatus().equals(Status.NOT_ACTIVE)) {

                userUpdate.setStatus(Status.ACTIVE);

                if (userUpdate.getAvatar() == null) {
                    userUpdate.setAvatar(imageService.saveImage(googleUser.getPhotoUrl(), ImageCaption.GOOGLE));
                }
                userService.save(mapper.map(userUpdate, User.class));
            }
            request.setEmail(googleUser.getEmail());
        }

        return request;
    }

    private boolean isValidToken(String clientIdToken){

        if (clientIdToken != null) {
            GoogleIdToken idToken;
            try {
                idToken = verifier.verify(clientIdToken);
                return idToken != null;
            } catch (GeneralSecurityException | IOException e) {
                throw new GoogleAuthenticationException("Error while validation client id token");
            }
        } else {
            throw new GoogleAuthenticationException("Google token cannot be null");
        }
    }
}
