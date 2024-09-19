package com.stpp.movies.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stpp.movies.configurations.LocalDateDeserializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "movies", schema = "public")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String genre;

    @Min(1)
    @Max(10)
    private Float rating;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(columnDefinition = "bytea")
    private byte[] poster;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    @LastModifiedDate
    private OffsetDateTime dateModified;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Discussion> discussions;

    @PrePersist
    public void prePersist() {
        this.setDateCreated(OffsetDateTime.now());
        this.setDateModified(OffsetDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        this.setDateModified(OffsetDateTime.now());
    }
}
