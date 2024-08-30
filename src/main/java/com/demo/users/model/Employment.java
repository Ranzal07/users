package com.demo.users.model;

import jakarta.persistence.*;

@Entity
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

    // Getters and setters
    public Long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
