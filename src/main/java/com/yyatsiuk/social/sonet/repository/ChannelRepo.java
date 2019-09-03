package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.Chat;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepo extends JpaRepository<Channel, Long> {

    boolean existsByChatAndName(Chat chat, String name) throws DataAccessException;
}
