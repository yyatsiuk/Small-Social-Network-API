package com.yyatsiuk.social.sonet.config;


import com.yyatsiuk.social.sonet.tracking.UserOnlineTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class UsersOnlineConfig {

    private static final Logger logger = LoggerFactory.getLogger(UsersOnlineConfig.class);

    private UserOnlineTracker userOnlineTracker;

    @Autowired
    UsersOnlineConfig(UserOnlineTracker userOnlineTracker) {
        this.userOnlineTracker = userOnlineTracker;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 3)
    public void scheduleTaskWithFixedDelay() {
        userOnlineTracker.updateUsersIsOnlineInDatabase();
    }
}
