package com.yyatsiuk.social.sonet;

import com.yyatsiuk.social.sonet.config.CustomMethodSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SonetApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class[] {SonetApplication.class,CustomMethodSecurityConfig.class}, args);
    }
}
