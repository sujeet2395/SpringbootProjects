package com.learncode.authservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<User> users;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @Override
    @Transient
    public String getAuthority() {
        return role;
    }
}
