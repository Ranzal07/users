package com.demo.users.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "employment")
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "level_id", nullable = true, foreignKey = @ForeignKey(name = "fk_level_id"))
    private Level level;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = true, foreignKey = @ForeignKey(name = "fk_position_id"))
    private Position position;

    @Column(name = "status")
    private String status;
}
