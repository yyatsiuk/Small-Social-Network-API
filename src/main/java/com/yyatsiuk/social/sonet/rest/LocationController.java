package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.model.UserWithLocationDTO;
import com.yyatsiuk.social.sonet.dto.request.UpdateLocationRequest;
import com.yyatsiuk.social.sonet.model.UserLocation;
import com.yyatsiuk.social.sonet.service.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/locations/users")
public class LocationController {

    private final LocationService locationService;
    private final ModelMapper mapper;

    public LocationController(LocationService locationService, ModelMapper mapper) {
        this.locationService = locationService;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("#request.userId == authentication.principal.id")
    public ResponseEntity updateLocation(@RequestBody UpdateLocationRequest request) {
        UserLocation userLocation = locationService.updateLocationInfo(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<UserWithLocationDTO>> getUsersNearMe(@PathVariable Long userId, Pageable pageable) {

        List<UserLocation> userLocationList = locationService.getUsersNearMe(userId, pageable);

        List<UserWithLocationDTO> response = new LinkedList<>();
        UserWithLocationDTO userWithLocationDTO;
        for (UserLocation userLocation : userLocationList) {
            userWithLocationDTO = mapper.map(userLocation.getUser(), UserWithLocationDTO.class);
            userWithLocationDTO.setDistance(userLocation.getDistance());
            response.add(userWithLocationDTO);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
