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

    public static AuthUserResponse fromUser(User user) {
        return AuthUserResponse.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .jobTitle(user.getEmployment().getLevel().getName() + " " + user.getEmployment().getPosition().getName())
            .build();
    }
}
