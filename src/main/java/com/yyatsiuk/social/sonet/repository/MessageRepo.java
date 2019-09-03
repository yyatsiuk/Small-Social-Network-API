package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.Channel;
import com.yyatsiuk.social.sonet.model.content.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

    Message getTop1By();

    List<Message> findMessagesByChannel(Channel channel);

    Message getTop1ByChannelOrderByCreationTimeDesc(Channel channel);

}
