package com.yyatsiuk.social.sonet.dto.response;

import com.yyatsiuk.social.sonet.dto.model.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements DTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String avatar;

}
