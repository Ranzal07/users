package com.demo.users.model;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentRequest {
    private Long user;
    private Long level;
    private Long position;
}