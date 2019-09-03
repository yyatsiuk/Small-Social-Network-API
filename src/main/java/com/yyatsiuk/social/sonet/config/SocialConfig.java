package com.yyatsiuk.social.sonet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@PropertySource("classpath:security_resource/security.properties")
@Configuration
public class SocialConfig implements SocialConfigurer {

    @Value("${facebook.client-id}")
    private String appId;

    @Value("${facebook.client-secret}")
    private String appSecret;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(appId, appSecret);
        facebookConnectionFactory.setScope("public_profile,email");
        cfConfig.addConnectionFactory(facebookConnectionFactory);
    }

    @Override
    public UserIdSource getUserIdSource() {
        return null;
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }
}
