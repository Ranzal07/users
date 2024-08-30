package com.demo.users.model;

import jakarta.persistence.*;

@Entity
@Table(name = "level")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "salary_rate", nullable = false)
    private Float salaryRate;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }    

    public Float getSalaryRate() { return salaryRate; }
    public void setSalaryRate(Float salaryRate) { this.salaryRate = salaryRate; }
}
