package com.demo.users.payload;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.util.List;

import com.demo.users.model.User;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponse {
    private Long lineNo;
    private Long id;
    private String firstName;
    private String lastName;
    private String jobTitle;

    public static UsersResponse fromUser(User user, Long i) {
        return UsersResponse.builder()
            .lineNo(i)
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .jobTitle(user.getEmployment().getLevel().getName() + " " + user.getEmployment().getPosition().getName())
            .build();
    }

    public static List<UsersResponse> fromUserList(List<User> users) {
        return IntStream.range(0, users.size())
            .mapToObj(i -> fromUser(users.get(i), (long) i + 1))
            .collect(Collectors.toList());
    }
}
