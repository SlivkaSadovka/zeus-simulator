package org.zeus.demo.model;

import jakarta.persistence.*;
import org.zeus.demo.service.PersonalityRandomGeneratorService;

import java.time.LocalDateTime;

@Entity
@Table(name = "bots") // я не уверена, че там как табла выглядит, поэтому пока так\
public class Bot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String favoriteJoke;
    private String favoriteQuote;
    private String favoriteAnimal;

    private Status status;
    private Behavior behavior;
    private int faithLevel;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Bot(){
        faithLevel = 0;
        status = Status.UNKNOWN;
    }

    // геттеры и сеттеры

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public int getFaithLevel() {
        return faithLevel;
    }

    public void setFaithLevel(int faithLevel) {
        this.faithLevel = faithLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFavoriteJoke() {
        return favoriteJoke;
    }

    public void setFavoriteJoke(String favoriteJoke) {
        this.favoriteJoke = favoriteJoke;
    }

    public String getFavoriteQuote() {
        return favoriteQuote;
    }

    public void setFavoriteQuote(String favoriteQuote) {
        this.favoriteQuote = favoriteQuote;
    }

    public String getFavoriteAnimal() {
        return favoriteAnimal;
    }

    public void setFavoriteAnimal(String favoriteAnimal) {
        this.favoriteAnimal = favoriteAnimal;
    }
}
