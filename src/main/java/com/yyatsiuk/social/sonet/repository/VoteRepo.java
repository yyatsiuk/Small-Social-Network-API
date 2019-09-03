package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.vote.Vote;
import com.yyatsiuk.social.sonet.model.vote.VotePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface VoteRepo extends JpaRepository<Vote, VotePrimaryKey> {

    @Query(value = "select distinct v.choice.id, count(v.choice.id) FROM Vote v where \n" +
            "v.poll.id = :pollId group by v.choice.id ")
    List<Long[]> findVariantsWithCountOfVote(@Param("pollId") Long pollId);

    Boolean existsVoteByPollIdAndUserId(Long pollId, Long userId);

    Vote findByUserIdAndPollId(Long userId, Long pollId);

}
