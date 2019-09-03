package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.model.UserDTO;
import com.yyatsiuk.social.sonet.model.Chat;
import com.yyatsiuk.social.sonet.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;

public interface UserService {

    List<Chat> getAllChats(Long userId);

    User getUserById(Long id);

    boolean existByEmail(String email);

    boolean existById(Long id);

    User save(@NotNull User user);

    User updateUserInfo(Long id, UserDTO userDTO);

    User findUserByEmail(String email);

    List<User> getUserFollowing(Long userId);

    boolean existByNickname(String nickname);

    List<User> getAllActiveUsers();

    Boolean isOnline(Long id);

    void updateUsersSetIsOnlineAndLastActivityWhereIdIn(Queue<Long> usersId, LocalDateTime lastActivity);

    void updateUsersSetNotIsOnlineWhereIdNotIn(Queue<Long> usersId);

    void updateUsersSetNotOnline();


}
