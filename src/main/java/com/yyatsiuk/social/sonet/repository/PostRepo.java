package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.model.content.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    Post findByIdAndStatus(Long id, Status status);

    Page<Post> findAllByStatusAndOwnerId(Status status, Long idOwner,Pageable pageable);

    @Query("select post from Post post where post.owner in (select follow.following from UserFollow follow " +
            " where follow.followerId = :user_id) and post.status = 'ACTIVE' ")
    Page<Post> findNews(Pageable pageable, @Param("user_id")Long idUser);

    @Query(value = " select distinct posts.*, likes.post_id, count(likes.post_id) as count_of_likes from likes, posts " +
            "where posts.id = likes.post_id and posts.status = 'ACTIVE' " +
            "group by likes.post_id order by count_of_likes desc ", nativeQuery = true)
    Page <Post> findTopPost(Pageable pageable);

    @Query(value = "select distinct posts.* from likes, posts " +
            "where posts.id = likes.post_id and posts.status = 'ACTIVE' and likes.user_id = :user_id"+
            " order by likes.post_id desc ",
            nativeQuery = true)
    Page <Post> findLikedPosts(Pageable pageable, @Param("user_id")Long userId);

    @Query("select distinct post from Post post" +
            " join Like likes on likes.post = post.id " +
            "join UserFollow follow on follow.followingId = likes.user " +
            "where post.status='ACTIVE' and follow.followerId = :user_id " +
            "order by likes.post desc")
    Page <Post> findLikedPostsOfFollowing(Pageable pageable, @Param("user_id")Long userId);


    Page<Post> findPostsByStatusAndTextContaining(Status status, String text, Pageable pageble);

}
