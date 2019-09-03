package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.OperationName;
import com.yyatsiuk.social.sonet.dto.OperationStatus;
import com.yyatsiuk.social.sonet.dto.response.OperationStatusResponse;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.exception.ProfileNotActiveException;
import com.yyatsiuk.social.sonet.security.dto.*;
import com.yyatsiuk.social.sonet.security.jwt.JwtTokenProvider;
import com.yyatsiuk.social.sonet.security.service.FacebookSecurityService;
import com.yyatsiuk.social.sonet.security.service.GoogleSecurityService;
import com.yyatsiuk.social.sonet.security.service.UserSecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserSecurityService userSecurityService;
    private final GoogleSecurityService googleSecurityService;
    private final FacebookSecurityService facebookSecurityService;
    private final ModelMapper mapper;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserSecurityService userSecurityService,
            GoogleSecurityService googleSecurityService,
            FacebookSecurityService facebookSecurityService,
            @Lazy ModelMapper mapper
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userSecurityService = userSecurityService;
        this.googleSecurityService = googleSecurityService;
        this.facebookSecurityService = facebookSecurityService;
        this.mapper = mapper;
    }


    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest requestUser) {
        try {
            return authenticate(requestUser);
        } catch (DisabledException ex) {
            throw new ProfileNotActiveException("Email not verified");
        } catch (AuthenticationException | EntityNotFoundException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/google")
    public ResponseEntity authenticateGoogleUser(@RequestBody GoogleUserInfo googleUser) {
        LoginRequest loginUserRequest = googleSecurityService
                .authenticateGoogleUser(googleUser);

        return authenticate(loginUserRequest);
    }

    @PostMapping("/facebook")
    public ResponseEntity authenticateFacebookUser(@RequestBody FacebookAccessTokenRequest facebookRequest) {
        LoginRequest loginUserRequest = facebookSecurityService
                .authenticateFacebookUser(facebookRequest);

        return authenticate(loginUserRequest);
    }

    @PostMapping("/register")
    public ResponseEntity registration(@Valid @RequestBody RegisterUserRequest requestUser) {
        RegisterUserResponse response = mapper
                .map(userSecurityService.register(requestUser), RegisterUserResponse.class);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/email-verification")
    public OperationStatusResponse verifyEmailToken(@RequestParam(value = "token") String token) {
        OperationStatusResponse response = new OperationStatusResponse();
        response.setName(OperationName.VERIFY_EMAIL.name());

        boolean isVerify = userSecurityService.verifyEmailToken(token);

        if (isVerify) {
            response.setResult(OperationStatus.SUCCESS.name());

        } else {
            response.setResult(OperationStatus.ERROR.name());
        }

        return response;
    }

    @PostMapping("/request-password-reset")
    public OperationStatusResponse passwordReset(@RequestBody String email) {
        OperationStatusResponse response = new OperationStatusResponse();
        response.setName(OperationName.RESTORE_PASSWORD_REQUEST.name());

        boolean isSuccess = userSecurityService.resetPasswordRequest(email);

        if (!isSuccess) {
            response.setResult(OperationStatus.ERROR.name());
            return response;
        }

        response.setResult(OperationStatus.SUCCESS.name());

        return response;
    }

    @PostMapping("/password-reset")
    public OperationStatusResponse resetPassword(@RequestBody PasswordResetRequest request) {
        OperationStatusResponse returnValue = new OperationStatusResponse();

        boolean operationResult = userSecurityService.resetPassword(
                request.getToken(),
                request.getPassword());

        returnValue.setName(OperationName.RESTORE_PASSWORD.name());
        returnValue.setResult(OperationStatus.SUCCESS.name());

        if (!operationResult) {
            returnValue.setResult(OperationStatus.ERROR.name());
            return returnValue;
        }

        return returnValue;
    }

    private ResponseEntity authenticate(LoginRequest loginRequest) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

        SecuredUserDTO securedUserDTO = userSecurityService
                .findByEmail(loginRequest.getEmail());

        String token = jwtTokenProvider.
                createToken(loginRequest.getEmail(), securedUserDTO.getRoles());

        securedUserDTO.setToken(token);

        return ResponseEntity.ok(mapper.map(securedUserDTO, LoginUserResponse.class));
    }

}
