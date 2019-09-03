package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.like.Like;
import com.yyatsiuk.social.sonet.model.like.LikePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, LikePrimaryKey> {

    List<Like> findAllByPostId(Long postId);

}
