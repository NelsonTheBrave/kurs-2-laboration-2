package org.example.entities;

public class ProductDTO {
    public String name;
    public Category category;
    public int rating;

    // Default constructor
    public ProductDTO() {
    }

    // Parameterized constructor
    public ProductDTO(String name, Category category, int rating) {
        this.name = name;
        this.category = category;
        this.rating = rating;
    }
}