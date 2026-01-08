package org.zeus.demo.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bots")
@Setter
@Getter
public class Bot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String firstName;
    private String lastName;
    private String favoriteJoke;
    private String favoriteQuote;
    private String favoriteBook;

    private Status status;
    private Behavior behavior;
    private int faithLevel;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Bot() {
        faithLevel = 0;
        status = Status.UNKNOWN;
    }

    // геттеры и сеттеры

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}