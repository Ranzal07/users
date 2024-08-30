package com.demo.users.model;
import jakarta.persistence.*;

@Entity
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

    // Getters and setters
    public Long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Employment getEmployment() { return employment; }
    public void setEmployment(Employment employment) { this.employment = employment; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String string) { this.password = string; }
}
