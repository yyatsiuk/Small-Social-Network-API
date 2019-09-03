package com.yyatsiuk.social.sonet.config;

import com.yyatsiuk.social.sonet.security.EventSendingAuthenticationFailureHandler;
import com.yyatsiuk.social.sonet.security.jwt.JwtConfigurer;
import com.yyatsiuk.social.sonet.security.jwt.JwtTokenProvider;
import com.yyatsiuk.social.sonet.security.service.impl.CustomAuthenticationProvider;
import com.yyatsiuk.social.sonet.security.service.impl.SecurityUserDetailsService;
import com.yyatsiuk.social.sonet.tracking.UserOnlineTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${web.origins}")
    private String[] origins;

    private static final String[] PERMIT_ALL_ENDPOINT =
            {
                    "/api/auth/**",
                    "/v2/api-docs",
                    "/configuration/**",
                    "/swagger*/**",
                    "/webjars/**",
                    "/api/posts/get-top-rated",
                    "/api/ws/**",
                    "/actuator/**"
            };
    private static final String ADMIN_ENDPOINT = "/api/admins/**";
    private static final String MODERATOR_ENDPOINT = "/api/moderators/**";
    private static final String USER_ENDPOINT = "/api/users/**";
    private static final String COMMENTS_ENDPOINT = "/api/comments/**";
    private static final String CHATS_ENDPOINT = "/api/chats/**";
    private static final String POSTS_ENDPOINT = "/api/posts/**";
    private static final String POLLS_ENDPOINT = "/api/polls/**";
    private static final String VOTES_ENDPOINT = "/api/votes/**";
    private static final String GROUPS_ENDPOINT = "/api/groups/**";
    private static final String LIKES_ENDPOINT = "/api/likes/**";
    private static final String IMAGES_ENDPOINT = "/api/images/**";
    private static final String LOCATIONS_ENDPOINT = "/api/locations/**";

    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityUserDetailsService securityUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserOnlineTracker userOnlineTracker;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider,
                          SecurityUserDetailsService securityUserDetailsService,
                          PasswordEncoder passwordEncoder,
                          CustomAuthenticationProvider customAuthenticationProvider,
                          UserOnlineTracker userOnlineTracker
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityUserDetailsService = securityUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.userOnlineTracker = userOnlineTracker;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(customAuthenticationProvider)
                .userDetailsService(securityUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    AuthenticationFailureHandler eventAuthenticationFailureHandler() {
        return new EventSendingAuthenticationFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .formLogin()
                .failureHandler(eventAuthenticationFailureHandler()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(PERMIT_ALL_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(MODERATOR_ENDPOINT).hasAnyRole("MODERATOR", "ADMIN")
                .antMatchers(USER_ENDPOINT).hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(COMMENTS_ENDPOINT).hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(CHATS_ENDPOINT).hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(POSTS_ENDPOINT).hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(GROUPS_ENDPOINT).hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(LIKES_ENDPOINT).hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(IMAGES_ENDPOINT).hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(LOCATIONS_ENDPOINT).hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(POLLS_ENDPOINT).hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers(VOTES_ENDPOINT).hasAnyRole("USER", "MODERATOR", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider,userOnlineTracker ));
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(origins));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
