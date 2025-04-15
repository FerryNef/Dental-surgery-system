package com.ads.dentalapp.model;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Entity
@Table(name="users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(updatable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // 🔐 should be hashed before saving

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {}

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
