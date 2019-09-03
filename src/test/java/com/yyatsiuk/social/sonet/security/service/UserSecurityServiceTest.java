package com.yyatsiuk.ita.sonet.security.service;

import com.yyatsiuk.ita.sonet.exception.EmailAlreadyExistException;
import com.yyatsiuk.ita.sonet.exception.NicknameAlreadyExistException;
import com.yyatsiuk.ita.sonet.model.ActorRole;
import com.yyatsiuk.ita.sonet.model.Image;
import com.yyatsiuk.ita.sonet.model.Role;
import com.yyatsiuk.ita.sonet.model.Status;
import com.yyatsiuk.ita.sonet.model.User;
import com.yyatsiuk.ita.sonet.repository.PasswordResetTokenRepo;
import com.yyatsiuk.ita.sonet.repository.RoleRepo;
import com.yyatsiuk.ita.sonet.repository.UserRepo;
import com.yyatsiuk.ita.sonet.security.dto.RegisterUserRequest;
import com.yyatsiuk.ita.sonet.security.service.impl.UserSecurityServiceImpl;
import com.yyatsiuk.ita.sonet.security.util.SecurityUtils;
import com.yyatsiuk.ita.sonet.service.EmailService;
import com.yyatsiuk.ita.sonet.service.ImageService;
import com.yyatsiuk.ita.sonet.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserSecurityServiceTest {

    @Mock
    private RoleRepo roleRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private UserService userService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper mapper;
    @Mock
    private SecurityUtils securityUtils;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordResetTokenRepo resetTokenRepository;
    @Mock
    private ImageService imageService;

    @InjectMocks
    private UserSecurityServiceImpl securityService;

    @Test
    public void register_test() throws MessagingException {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("test@email");
        request.setNickname("testNickname");
        request.setPassword("testPassword");
        request.setConfirmPassword("testConfirmPassword");

        Role userRole = new Role();
        userRole.setName(ActorRole.ROLE_USER.name());
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);

        User user = new User();
        user.setRoles(userRoles);
        user.setPassword(request.getPassword());
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setEmailVerificationToken("emailVerificationToken");

        Image image = new Image();
        image.setUrl("testUrl");
        image.setStatus(Status.ACTIVE);

        user.setAvatar(image);
        user.setBackground(image);
        user.setStatus(Status.NOT_ACTIVE);


        when(userRepo.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepo.existsByNickname(request.getNickname())).thenReturn(false);
        when(roleRepo.findByName(ActorRole.ROLE_USER.name())).thenReturn(userRole);
        when(securityUtils.generateEmailVerificationToken(request.getEmail())).thenReturn("emailVerificationToken");
        when(userService.save(user)).thenReturn(user);
        doNothing().when(emailService).sendConfirm(anyString(), anyString());

        User userResult = securityService.register(request);

        assertEquals(userResult.getStatus(), user.getStatus());
        assertEquals(userResult.getEmail(), user.getEmail());
        assertEquals(userResult.getNickname(), user.getNickname());
        assertEquals(userResult.getFirstName(), user.getFirstName());
        assertEquals(userResult.getLastName(), user.getLastName());
    }

    @Test
    public void whenEmailVerificationTokenNotExpire_statusShouldBeActive_andMethodReturnTrue() {
        User user = new User();
        user.setStatus(Status.NOT_ACTIVE);
        user.setEmailVerificationToken("testToken");


        when(securityUtils.hasTokenExpired(anyString())).thenReturn(false);
        when(userRepo.findUserByEmailVerificationToken(anyString())).thenReturn(user);
        when(userService.save(user)).thenReturn(user);

        boolean result = securityService.verifyEmailToken(anyString());

        assertTrue(result);
        assertEquals(Status.ACTIVE, user.getStatus());
    }

    @Test(expected = EmailAlreadyExistException.class)
    public void whenEmailAlreadyExist_throwExceptions() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setNickname("testNickname");
        request.setEmail("testEmail");

        when(userRepo.existsByEmail(request.getEmail())).thenReturn(true);

        securityService.register(request);
    }

    @Test(expected = NicknameAlreadyExistException.class)
    public void whenUserNameExist_throwExceptions() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setNickname("testNickname");
        request.setEmail("testEmail");

        when(userRepo.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepo.existsByNickname(request.getNickname())).thenReturn(true);

        securityService.register(request);
    }
}