package com.yyatsiuk.social.sonet.security.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yyatsiuk.social.sonet.security.service.LoginAttemptService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.yyatsiuk.social.sonet.security.constant.SecurityConstant.BLOCK_TIME_IN_MINUTES;
import static com.yyatsiuk.social.sonet.security.constant.SecurityConstant.MAX_ATTEMPT;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptServiceImpl() {
        super();
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(BLOCK_TIME_IN_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {

                    @SuppressWarnings("NullableProblems")
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(String ip) {
        int attempts;
        try {
            attempts = attemptsCache.get(ip);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(ip, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
