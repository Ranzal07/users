package com.demo.users.payload;

import com.demo.users.model.User;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private Boolean canEdit;

    public static AuthUserResponse fromUser(User user) {
        return AuthUserResponse.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .jobTitle(user.getEmployment().getLevel().getName() + " " + user.getEmployment().getPosition().getName())
            .canEdit(canEditAuthUser(user))
            .build();
    }

    private static Boolean canEditAuthUser(User user){
        Boolean canEdit = false;
        if("President".equals(user.getEmployment().getPosition().getName()) ||
            "HR".equals(user.getEmployment().getPosition().getName())
            ){
            canEdit = true;
        }
        return canEdit;
    }
}
