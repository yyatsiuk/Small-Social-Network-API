package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.model.UserDTO;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.exception.NicknameAlreadyExistException;
import com.yyatsiuk.social.sonet.model.Chat;
import com.yyatsiuk.social.sonet.model.User;
import com.yyatsiuk.social.sonet.model.follow.user.UserFollow;
import com.yyatsiuk.social.sonet.repository.UserRepo;
import com.yyatsiuk.social.sonet.service.ImageService;
import com.yyatsiuk.social.sonet.service.UserService;
import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ImageService imageService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, ImageService imageService) {
        this.userRepo = userRepo;
        this.imageService = imageService;
    }

    @Override
    public List<Chat> getAllChats(Long userId) {
        User user = getUserById(userId);

        return user.getChats();
    }

    @Override
    public List<User> getUserFollowing(Long userId) {
        User user = getUserById(userId);

        return user.getFollowing().stream().map(UserFollow::getFollowing).collect(Collectors.toList());
    }

    @Override
    public boolean existByNickname(String nickname) {
        return userRepo.existsByNickname(nickname);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id:{0} not found", id));
    }

    @Override
    public User updateUserInfo(Long id, UserDTO userToUpdate){
        User userFromBD = getUserById(id);

        if (!userFromBD.getNickname().equals(userToUpdate.getNickname())) {
            boolean exists = userRepo.existsByNickname(userToUpdate.getNickname());
            if (exists) {
                throw new NicknameAlreadyExistException("Nickname already exists");
            }
            userFromBD.setNickname(userToUpdate.getNickname());
        }

        userFromBD.setFirstName(userToUpdate.getFirstName());
        userFromBD.setLastName(userToUpdate.getLastName());
        userFromBD.setCity(userToUpdate.getCity());
        userFromBD.setCountry(userToUpdate.getCountry());
        userFromBD.setPlanet(userToUpdate.getPlanet());
        userFromBD.setAvatar(imageService.findByUrl(userToUpdate.getAvatar().getUrl()));
        userFromBD.setBackground(imageService.findByUrl(userToUpdate.getBackground().getUrl()));

        userFromBD = userRepo.save(userFromBD);

        return userFromBD;
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public User save(@NotNull User user) throws PersistentObjectException {
        return userRepo.save(user);
    }

    @Override
    public User findUserByEmail(String email) throws EntityNotFoundException {
        return userRepo.findUserByEmail(email)
                .orElseThrow(() ->
                        new EntityNotFoundException("User with email:{0} not found", email));
    }

    @Override
    public boolean existById(Long id) {
        return userRepo.existsById(id);
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userRepo.findAllbyActiveStatus();
    }

    @Override
    public Boolean isOnline(Long id) {
        User user = getUserById(id);
        return user.getIsOnline();
    }

    @Override
    public void updateUsersSetIsOnlineAndLastActivityWhereIdIn(Queue<Long> usersId, LocalDateTime lastActivity) {
        userRepo.updateUsersSetIsOnlineAndLastActivityWhereIdIn(usersId, lastActivity);
    }

    @Override
    public void updateUsersSetNotIsOnlineWhereIdNotIn(Queue<Long> usersId) {
        userRepo.updateUsersSetNotIsOnlineWhereIdNotIn(usersId);
    }

    @Override
    public void updateUsersSetNotOnline() {
        userRepo.updateUsersSetNotOnline();
    }
}
