package com.yyatsiuk.social.sonet.service;

import com.yyatsiuk.social.sonet.dto.request.UpdateLocationRequest;
import com.yyatsiuk.social.sonet.model.UserLocation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {

    UserLocation updateLocationInfo(UpdateLocationRequest request);

    List<UserLocation> getUsersNearMe(Long userId, Pageable pageable);

    double calculateDistanceBetweenTwoLocations(
            double currentUserLatitude,
            double anotherUserLatitude,
            double currentUserLongitude,
            double anotherUserLongitude
    );

}
