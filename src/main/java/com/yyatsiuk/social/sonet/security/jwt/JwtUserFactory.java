package com.yyatsiuk.social.sonet.security.jwt;

import com.yyatsiuk.social.sonet.model.Role;
import com.yyatsiuk.social.sonet.model.Status;
import com.yyatsiuk.social.sonet.security.dto.JwtUser;
import com.yyatsiuk.social.sonet.security.dto.SecuredUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {

    public JwtUserFactory(){

    }
    public static JwtUser userToJwtUser(SecuredUserDTO user){
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus().toString().equalsIgnoreCase(Status.ACTIVE.toString()),
                true,
                true,
                true,
                mapToGrandAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrandAuthorities(List<Role> userRoles){
        return userRoles.stream().map(role ->
                new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}