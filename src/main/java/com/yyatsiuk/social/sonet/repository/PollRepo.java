package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepo extends JpaRepository<Poll, Long> {

    Poll findPollById(Long id);

    List<Poll> findPollsByPostId(Long id);

}
