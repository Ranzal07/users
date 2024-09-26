package com.demo.users.payload;

import com.demo.users.model.Level;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LevelsResponse {
    private Long lineNo;
    private Long id;
    private String name;
    private Float salaryRate;

    public static LevelsResponse fromLevel(Level level, Long index) {
        return LevelsResponse.builder()
            .lineNo(index)
            .id(level.getId())
            .name(level.getName())
            .salaryRate(level.getSalaryRate())
            .build();
    }

    public static List<LevelsResponse> fromLevelList(List<Level> levels) {
        return IntStream.range(0, levels.size())
            .mapToObj(i -> fromLevel(levels.get(i), (long) i + 1))
            .collect(Collectors.toList());
    }
}
