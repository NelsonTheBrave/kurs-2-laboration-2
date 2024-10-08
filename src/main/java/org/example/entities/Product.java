package org.example.entities;

import java.time.LocalDateTime;

public class Product {
    public int id;
    public String name;
    public Category category;
    public int rating;
    public final LocalDateTime manufactureDate;
    public LocalDateTime lastModifiedDate;

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

    public record ProductRecord(int id, String name, Category category, int rating, LocalDateTime manufactureDate, LocalDateTime lastModifiedDate) {
    }

}
