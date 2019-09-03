package com.yyatsiuk.ita.sonet.service;

import com.yyatsiuk.ita.sonet.dto.request.UpdateLocationRequest;
import com.yyatsiuk.ita.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.ita.sonet.repository.LocationRepo;
import com.yyatsiuk.ita.sonet.service.impl.LocationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private LocationRepo locationRepo;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private LocationServiceImpl locationService;

    @Test
    public void calculateDistanceBetweenTwoPoint_shouldReturnCorrectResult() {
        double currentLatitude = 23.4434;
        double anotherLatitude = 23.4421;
        double currentLongitude = 45.6643;
        double anotherLongitude = 45.5632;

        final double expected = 10315.0;

        double result = locationService.calculateDistanceBetweenTwoLocations(currentLatitude, anotherLatitude, currentLongitude, anotherLongitude);

        assertEquals(expected, result, 1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void ifUserNotExist_shouldThrow_exceptions() {
        UpdateLocationRequest updLocReq = new UpdateLocationRequest();
        updLocReq.setUserId(1L);
        updLocReq.setLatitude(22.0101);
        updLocReq.setLongitude(33.0101);

        when(userService.existById(anyLong())).thenReturn(false);
        locationService.updateLocationInfo(updLocReq);
    }

}