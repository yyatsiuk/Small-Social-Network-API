package com.yyatsiuk.social.sonet.config;

import com.yyatsiuk.social.sonet.security.CustomMethodSecurityExpressionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class CustomMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

   private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        CustomMethodSecurityExpressionHandler handler = new  CustomMethodSecurityExpressionHandler();
        handler.setApplicationContext(applicationContext);
        return handler;
    }

    @Bean
    public MethodSecurityExpressionHandler expressionHandler() {
        return createExpressionHandler();
    }
}

