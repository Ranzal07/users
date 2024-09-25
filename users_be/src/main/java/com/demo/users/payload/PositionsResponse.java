package com.demo.users.payload;

import com.demo.users.model.Position;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionsResponse {
    private Long lineNo;
    private Long id;
    private String name;
    private float salary;
    private String role;

    public static PositionsResponse fromPosition(Position position, Long i) {
        return PositionsResponse.builder()
            .lineNo(i)
            .id(position.getId())
            .name(position.getName())
            .salary(position.getSalary())
            .role(position.getRole())
            .build();
    }

    public static List<PositionsResponse> fromPositionList(List<Position> positions) {
        return IntStream.range(0, positions.size())
            .mapToObj(i -> fromPosition(positions.get(i), (long) i + 1))
            .collect(Collectors.toList());
    }
}
