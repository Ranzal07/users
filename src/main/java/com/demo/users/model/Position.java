package com.demo.users.model;
import jakarta.persistence.*;

@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "salary", nullable = false)
    private Float salary;

    @Column(name = "role")
    private String role;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Float getSalary() { return salary; }
    public void setSalary(Float salary) { this.salary = salary; }
}
