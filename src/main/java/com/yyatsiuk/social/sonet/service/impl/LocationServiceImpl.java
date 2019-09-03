package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.request.UpdateLocationRequest;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.UserLocation;
import com.yyatsiuk.social.sonet.repository.LocationRepo;
import com.yyatsiuk.social.sonet.service.LocationService;
import com.yyatsiuk.social.sonet.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private static final double RANGE = 00.025;
    private static final double DISTANCE_LIMIT = 2000.00;

    private final UserService userService;
    private final LocationRepo locationRepo;
    private final ModelMapper mapper;

    public LocationServiceImpl(
            LocationRepo locationRepo,
            ModelMapper mapper,
            UserService userService) {
        this.locationRepo = locationRepo;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public UserLocation updateLocationInfo(UpdateLocationRequest request) {
        if (!userService.existById(request.getUserId())) {
            throw new EntityNotFoundException
                    ("User with id: {0} doesn't exist", request.getUserId());
        }

        UserLocation userLocation = mapper.map(request, UserLocation.class);
        return locationRepo.save(userLocation);
    }

    @Override
    public List<UserLocation> getUsersNearMe(Long userId, Pageable pageable) {
        Optional<UserLocation> userLocOpt = locationRepo.findById(userId);
        if (!userLocOpt.isPresent()) {
            throw new EntityNotFoundException
                    ("User with id: {0} doesn't exist", userId);
        }

        UserLocation userLocation = userLocOpt.get();

        double myLatitude = userLocation.getLatitude();
        double myLongitude = userLocation.getLongitude();

        double latitudeMaxPoint = myLatitude + RANGE;
        double latitudeMinPoint = myLatitude - RANGE;

        double longitudeMaxPoint = myLongitude + RANGE;
        double longitudeMinPoint = myLongitude - RANGE;

        Page<UserLocation> usersLocationPages = locationRepo
                .findAllByLatitudeBetweenAndLongitudeBetweenAndUserIdNot
                        (latitudeMinPoint, latitudeMaxPoint, longitudeMinPoint, latitudeMaxPoint, userId, pageable);
        List<UserLocation> usersLocationList = usersLocationPages.getContent();

        for (UserLocation userLoc : usersLocationList) {
            userLoc.setDistance
                    (calculateDistanceBetweenTwoLocations
                            (myLatitude, userLoc.getLatitude(), myLongitude, userLoc.getLongitude()));
        }

        return usersLocationList.parallelStream()
                .filter(userFilter -> userFilter.getDistance() <= DISTANCE_LIMIT)
                .sorted(Comparator.comparing(UserLocation::getDistance))
                .collect(Collectors.toList());
    }

    public double calculateDistanceBetweenTwoLocations(
            double currentUserLatitude,
            double anotherUserLatitude,
            double currentUserLongitude,
            double anotherUserLongitude
    ) {

        final int R = 6371;

        double latDistance = Math.toRadians(anotherUserLatitude - currentUserLatitude);
        double lonDistance = Math.toRadians(anotherUserLongitude - currentUserLongitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(currentUserLatitude))
                * Math.cos(Math.toRadians(anotherUserLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;

        distance = Math.pow(distance, 2);
        return Math.round(Math.sqrt(distance));
    }
}

