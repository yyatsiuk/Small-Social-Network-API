package com.yyatsiuk.social.sonet.repository;

import com.yyatsiuk.social.sonet.model.UserLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface LocationRepo extends JpaRepository<UserLocation, Long> {

    Page<UserLocation> findAllByLatitudeBetweenAndLongitudeBetweenAndUserIdNot(
            double latitudeMin, double latitudeMax, double longitudeMin,
            double longitudeMax, Long currentUserId, Pageable pageable
    );

}
