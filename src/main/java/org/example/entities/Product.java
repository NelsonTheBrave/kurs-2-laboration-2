package org.example.entities;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class Product {
    public int id;
    public @NotEmpty(message = "Name is required") String name;
    public Category category;
    public int rating;
    public final LocalDateTime manufactureDate;
    public LocalDateTime lastModifiedDate;

    public Product() {
        manufactureDate = LocalDateTime.now();
        lastModifiedDate = manufactureDate;
    }

    public Product(int id, String name, Category category, int rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        manufactureDate = LocalDateTime.now();
        lastModifiedDate = manufactureDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public record ProductRecord(int id, String name, Category category, int rating, LocalDateTime manufactureDate,
                                LocalDateTime lastModifiedDate) {
    }

}
