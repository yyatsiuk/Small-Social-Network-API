package com.yyatsiuk.ita.sonet.service;

import com.yyatsiuk.ita.sonet.dto.model.ImageDTO;
import com.yyatsiuk.ita.sonet.dto.model.UserDTO;
import com.yyatsiuk.ita.sonet.model.Image;
import com.yyatsiuk.ita.sonet.model.User;
import com.yyatsiuk.ita.sonet.repository.UserRepo;
import com.yyatsiuk.ita.sonet.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {


    @Mock
    UserRepo userRepo;

    @Mock
    ImageService imageService;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void updateUserInfoMustReturnUpdatedUser() {
        User user = new User();
        user.setNickname("Mock nickname");

        ImageDTO image = new ImageDTO();
        image.setUrl("Test url");

        Image image1 = new Image();
        image1.setUrl("Test url");

        UserDTO userToUpdate = new UserDTO();
        userToUpdate.setId(1L);
        userToUpdate.setFirstName("First name test");
        userToUpdate.setLastName("Last name test");
        userToUpdate.setCity("City test");
        userToUpdate.setCountry("Country test");
        userToUpdate.setPlanet("Planet test");
        userToUpdate.setNickname("Nickname test");
        userToUpdate.setAvatar(image);
        userToUpdate.setBackground(image);

        when(userRepo.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(userRepo.existsByNickname(any(String.class))).thenReturn(false);
        when(imageService.findByUrl(any(String.class))).thenReturn(image1);

        User updatedUser = userService.updateUserInfo(1L, userToUpdate);

        assertThat(userToUpdate.getFirstName()).isSameAs(updatedUser.getFirstName());
        assertThat(userToUpdate.getLastName()).isSameAs(updatedUser.getLastName());
        assertThat(userToUpdate.getCity()).isSameAs(updatedUser.getCity());
        assertThat(userToUpdate.getCountry()).isSameAs(updatedUser.getCountry());
        assertThat(userToUpdate.getPlanet()).isSameAs(updatedUser.getPlanet());
        assertThat(userToUpdate.getNickname()).isSameAs(updatedUser.getNickname());
    }

}
