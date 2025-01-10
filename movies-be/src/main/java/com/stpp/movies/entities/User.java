package com.stpp.movies.entities;

import com.stpp.movies.enumerators.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "users", schema = "public")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @Enumerated(value = EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @OneToMany(mappedBy = "user"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
  private List<Discussion> discussions;

  @OneToMany(mappedBy = "user"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
  private List<Comment> comments;

  @Column(nullable = false, updatable = false)
  private OffsetDateTime dateCreated;

  @Column(nullable = false)
  @LastModifiedDate
  private OffsetDateTime dateModified;

  @PrePersist
  public void prePersist() {
    this.setDateCreated(OffsetDateTime.now());
    this.setDateModified(OffsetDateTime.now());
  }

  @PreUpdate
  public void preUpdate() {
    this.setDateModified(OffsetDateTime.now());
  }

  @PreRemove
  public void preRemove() {
    User placeholderUser = new User();
    placeholderUser.setId(0L); // Assuming the placeholder user has ID 0

    for (Discussion discussion : discussions) {
      discussion.setUser(placeholderUser);
    }

    for (Comment comment : comments) {
      comment.setUser(placeholderUser);
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
  }

  @Override
  public String getUsername() {
    return id.toString();
  }

  public String realUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
