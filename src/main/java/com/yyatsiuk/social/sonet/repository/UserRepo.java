package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Queue;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findOptionalByEmail(String email) throws DataAccessException;

    User findUserByEmailVerificationToken(String token) throws DataAccessException;

    boolean existsByEmail(String email) throws DataAccessException;

    Optional<User> findUserByEmail(String email) throws DataAccessException;

    boolean existsByNickname(String nickname) throws DataAccessException;


    @Query(value = "SELECT * FROM db_sonet.users join db_sonet.actors on " +
            "db_sonet.users.actor_id = db_sonet.actors.id where " +
            "db_sonet.actors.status = 'ACTIVE' ", nativeQuery = true)
    List<User> findAllbyActiveStatus();

    @Modifying
    @Query("update User u set u.isOnline = true, u.lastActivity = :lastActivity where u.id IN :usersId")
    @Transactional
    void updateUsersSetIsOnlineAndLastActivityWhereIdIn(@Param("usersId") Queue<Long> usersId, @Param("lastActivity") LocalDateTime lastActivity);

    @Modifying
    @Query("update User u set u.isOnline = false where u.id NOT IN :usersId")
    @Transactional
    void updateUsersSetNotIsOnlineWhereIdNotIn(@Param("usersId") Queue<Long> usersId);

    @Modifying
    @Query("update User u set u.isOnline = false")
    @Transactional
    void updateUsersSetNotOnline();

}


