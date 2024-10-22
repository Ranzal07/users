package com.demo.users.payload;

import com.demo.users.model.Level;
import com.demo.users.model.Position;
import com.demo.users.model.User;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentsResponse {
    private Long lineNo;
    private Long id;
    private String fullName;
    private Level level;
    private Position position;
    private String salary;
    private String status;

    public static EmploymentsResponse fromEmployment(User user, Long index, User authUser) {
        String formattedSalary = getSalary(user, authUser);
        return EmploymentsResponse.builder()
            .lineNo(index)
            .id(user.getId())
            .fullName(user.getFirstName() + " " + user.getLastName())
            .level(user.getEmployment().getLevel())
            .position(user.getEmployment().getPosition())
            .salary(formattedSalary)
            .status(user.getEmployment().getStatus())
            .build();
    }

    public static List<EmploymentsResponse> fromEmploymentList(List<User> users, User authUser) {
        return IntStream.range(0, users.size())
            .mapToObj(i -> fromEmployment(users.get(i), (long) i + 1, authUser))
            .collect(Collectors.toList());
    }

    public static String getSalary(User user, User authUser){
        String salary = "CONFIDENTIAL";

        if (user.equals(authUser) || 
            ("President".equals(authUser.getEmployment().getPosition().getName()) ||
            ("HR".equals(authUser.getEmployment().getPosition().getName()) && !"President".equals(user.getEmployment().getPosition().getName()))
            )
        ) {
            salary = String.format("%,.2f", 
                user.getEmployment().getPosition().getSalary() * 
                user.getEmployment().getLevel().getSalaryRate());
        }
        return salary;
    }
}
