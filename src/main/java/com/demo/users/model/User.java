package com.demo.users.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false, length = 100)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "employment_id", nullable = true, foreignKey = @ForeignKey(name = "fk_employment_id"))
    private Employment employment;

    @Column(name = "username", unique = true)
    private String username;
    
    @Column(name = "password")
    private String password;
}
