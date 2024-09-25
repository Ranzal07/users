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
    private float salary;
    private String status;

    public static EmploymentsResponse fromEmployment(User user, Long index) {
        return EmploymentsResponse.builder()
            .lineNo(index)
            .id(user.getId())
            .fullName(user.getFirstName() + " " + user.getLastName())
            .level(user.getEmployment().getLevel())
            .position(user.getEmployment().getPosition())
            .salary(user.getEmployment().getPosition().getSalary() * user.getEmployment().getLevel().getSalaryRate())
            .status(user.getEmployment().getStatus())
            .build();
    }

    public static List<EmploymentsResponse> fromEmploymentList(List<User> users) {
        return IntStream.range(0, users.size())
            .mapToObj(i -> fromEmployment(users.get(i), (long) i + 1))
            .collect(Collectors.toList());
    }
}
