package com.example.backendglobaldirectory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean approved;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_employment")
    private LocalDateTime dateOfEmployment;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image", referencedColumnName = "id")
    private Image profileImage;

    private List<String> skills;

    private List<String> previousExperience;

    private List<String> hobbies;

    private String team;

    private String department;

    @Column(name = "job_title")
    private String jobTitle;

    public boolean hasRole(String roleName) {
        return this.role.name().equals(roleName);
    }
}
