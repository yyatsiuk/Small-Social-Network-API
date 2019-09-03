package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {

    @Query(value = "SELECT count(actor_id) FROM actor_images WHERE actor_id=?1 AND image_id=?2", nativeQuery = true)
    Long getImagesCount(Long actorId, Long imageId);

    Image findImageByUrl(String Url);

    @Query(value = "SELECT * FROM images i " +
            "INNER JOIN actor_images a_i ON i.id=a_i.image_id " +
            "INNER JOIN actors a ON a_i.actor_id=a.id " +
            "WHERE actor_id=?1 AND image_status='ACTIVE'",
            countQuery = "SELECT COUNT(*) FROM images i " +
                    "INNER JOIN actor_images a_i ON i.id=a_i.image_id " +
                    "INNER JOIN actors a ON a_i.actor_id=a.id " +
                    "WHERE actor_id=?1 AND image_status='ACTIVE'",
            nativeQuery = true)
    Page<Image> getActorImages(Long actorId, Pageable pageable);

    @Query(value = "SELECT * FROM images i " +
            "INNER JOIN actor_images a_i ON i.id=a_i.image_id " +
            "INNER JOIN actors a ON a_i.actor_id=a.id " +
            "WHERE actor_id=?1 AND caption LIKE CONCAT('%', ?2, '%') AND image_status='ACTIVE'",
            countQuery = "SELECT COUNT(*) FROM images i " +
                    "INNER JOIN actor_images a_i ON i.id=a_i.image_id " +
                    "INNER JOIN actors a ON a_i.actor_id=a.id " +
                    "WHERE actor_id=?1 AND caption LIKE CONCAT('%', ?2, '%') AND image_status='ACTIVE'",
            nativeQuery = true)
    Page<Image> getActorImagesByCaptionLike(Long actorId, String caption, Pageable pageable);
}
