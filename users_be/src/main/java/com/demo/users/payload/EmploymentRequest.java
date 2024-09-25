package com.demo.users.payload;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentRequest {
    private Long userId;
    private Long levelId;
    private Long positionId;
}