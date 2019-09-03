package com.yyatsiuk.social.sonet.tracking;

import com.yyatsiuk.social.sonet.service.UserService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.stream.Collectors.toCollection;

@Service
public class UserOnlineTracker {

    private static final Logger logger = LoggerFactory.getLogger(UserOnlineTracker.class);

    private Queue<Long> userLastTimeOnlineList;
    private final UserService userService;

    @Autowired
    UserOnlineTracker(UserService userService) {
        userLastTimeOnlineList = new ConcurrentLinkedQueue<>();
        this.userService = userService;
    }

    public void notifyForLastActivity(@NonNull Long id) {
        userLastTimeOnlineList.add(id);
    }

    public void updateUsersIsOnlineInDatabase() {
        if (!userLastTimeOnlineList.isEmpty()) {

            ConcurrentLinkedQueue<Long> queue = userLastTimeOnlineList
                    .parallelStream()
                    .distinct()
                    .collect(toCollection(ConcurrentLinkedQueue::new));

            userService.updateUsersSetIsOnlineAndLastActivityWhereIdIn
                    (userLastTimeOnlineList, LocalDateTime.now());
            userService.updateUsersSetNotIsOnlineWhereIdNotIn(userLastTimeOnlineList);
            userLastTimeOnlineList.clear();
        } else {
            userService.updateUsersSetNotOnline();
        }

    }


}
