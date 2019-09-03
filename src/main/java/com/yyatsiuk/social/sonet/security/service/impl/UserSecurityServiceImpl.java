package com.yyatsiuk.social.sonet.security.service.impl;

import com.yyatsiuk.social.sonet.exception.EmailAlreadyExistException;
import com.yyatsiuk.social.sonet.exception.EmailSendingException;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.exception.NicknameAlreadyExistException;
import com.yyatsiuk.social.sonet.model.*;
import com.yyatsiuk.social.sonet.repository.PasswordResetTokenRepo;
import com.yyatsiuk.social.sonet.repository.RoleRepo;
import com.yyatsiuk.social.sonet.repository.UserRepo;
import com.yyatsiuk.social.sonet.security.dto.RegisterUserRequest;
import com.yyatsiuk.social.sonet.security.dto.SecuredUserDTO;
import com.yyatsiuk.social.sonet.security.service.UserSecurityService;
import com.yyatsiuk.social.sonet.security.util.SecurityUtils;
import com.yyatsiuk.social.sonet.service.EmailService;
import com.yyatsiuk.social.sonet.service.ImageService;
import com.yyatsiuk.social.sonet.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.yyatsiuk.social.sonet.security.constant.SecurityConstant.EMAIL_VERIFY_LINK;
import static com.yyatsiuk.social.sonet.security.constant.SecurityConstant.PASSWORD_RESTORE_LINK;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    private static final Logger logger = LoggerFactory.getLogger(UserSecurityServiceImpl.class);

    @Value("${web.url}")
    private String WEB_END_POINT;

    @Value("${default.avatar.url}")
    private String defaultImageUrl;

    @Value("${default.background.url}")
    private String defaultBackgroundUrl;

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final SecurityUtils securityUtils;
    private final EmailService emailService;
    private final PasswordResetTokenRepo resetTokenRepository;
    private final ImageService imageService;

    @Autowired
    public UserSecurityServiceImpl(
            RoleRepo roleRepo,
            UserRepo userRepo,
            UserService userService,
            @Lazy BCryptPasswordEncoder passwordEncoder,
            @Lazy ModelMapper mapper,
            @Lazy ImageService imageService,
            @Lazy SecurityUtils securityUtils,
            @Lazy EmailService emailService,
            @Lazy PasswordResetTokenRepo resetTokenRepository
    ) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.imageService = imageService;
        this.securityUtils = securityUtils;
        this.emailService = emailService;
        this.resetTokenRepository = resetTokenRepository;
    }

    @Override
    @Transactional
    public User register(RegisterUserRequest requestUser) throws EmailAlreadyExistException, EmailSendingException {

        if (userRepo.existsByEmail(requestUser.getEmail())) {
            throw new EmailAlreadyExistException("This email already exists");
        }
        if (userRepo.existsByNickname(requestUser.getNickname())) {
            throw new NicknameAlreadyExistException("This nickname already exists");
        }

        User user = new User();
        String email = requestUser.getEmail();

        Role roleUser = roleRepo.findByName(ActorRole.ROLE_USER.name());

        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setEmail(email);
        user.setNickname(requestUser.getNickname());
        user.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        user.setAvatar(imageService.findByUrl(defaultImageUrl));
        user.setBackground(imageService.findByUrl(defaultBackgroundUrl));
        user.setRoles(userRoles);
        user.setStatus(Status.NOT_ACTIVE);

        String emailVerifyToken = securityUtils
                .generateEmailVerificationToken(email);
        user.setEmailVerificationToken(emailVerifyToken);

        user = userService.save(user);

        try {
            emailService.sendConfirm(email, getEmailVerificationLink(emailVerifyToken));
        } catch (MessagingException e) {
            throw new EmailSendingException("Error while sending email", e);
        }

        return user;
    }

    @Override
    public SecuredUserDTO findByEmail(String email) throws DataAccessException, EntityNotFoundException {
        User user = userRepo
                .findOptionalByEmail(email)
                .orElseThrow(() ->
                        new EntityNotFoundException("User with email:{0} not found", email));

        return mapper.map(user, SecuredUserDTO.class);

    }

    @Override
    public SecuredUserDTO findById(Long id) throws DataAccessException, EntityNotFoundException {
        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User with id:{0} not found", id));

        return mapper.map(user, SecuredUserDTO.class);

    }

    @Override
    public boolean verifyEmailToken(String token) {

        if (securityUtils.hasTokenExpired(token)) {
            return false;
        }

        User user = userRepo.findUserByEmailVerificationToken(token);

        if (user == null) {
            return false;
        }
        user.setStatus(Status.ACTIVE);

        user = userService.save(user);
        return user.getEmailVerificationToken() != null;

    }


    @Override
    public boolean resetPasswordRequest(String email) {

        User user = userService.findUserByEmail(email);

        String token = securityUtils.generateEmailVerificationToken(email);

        PasswordResetToken passwordResetTokenEntity = new PasswordResetToken();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUser(user);

        resetTokenRepository.save(passwordResetTokenEntity);

        try {

            emailService.sendRestorePassword(email, getPasswordResetToken(token));

        } catch (MessagingException e) {
            throw new EmailSendingException("Error while sending email", e);
        }

        return true;
    }

    @Override
    public boolean resetPassword(String token, String password) throws EmailSendingException {

        if (token == null) {
            return false;
        }

        PasswordResetToken passwordResetTokenEntity = resetTokenRepository.findByToken(token);

        if (passwordResetTokenEntity == null) {
            return false;
        }

        String encodedPassword = passwordEncoder.encode(password);

        User userEntity = passwordResetTokenEntity.getUser();
        userEntity.setPassword(encodedPassword);

        User savedUserEntity = userService.save(userEntity);

        boolean isSuccess = false;
        if (savedUserEntity.getPassword().equalsIgnoreCase(encodedPassword)) {
            isSuccess = true;
        }

        resetTokenRepository.delete(passwordResetTokenEntity);

        return isSuccess;
    }

    @Override
    public boolean isActive(String email) throws EntityNotFoundException {
        return userService
                .findUserByEmail(email)
                .getStatus()
                .equals(Status.ACTIVE);
    }

    private String getEmailVerificationLink(String token) {
        return WEB_END_POINT + EMAIL_VERIFY_LINK + token;
    }

    private String getPasswordResetToken(String token) {
        return WEB_END_POINT + PASSWORD_RESTORE_LINK + token;
    }

}
