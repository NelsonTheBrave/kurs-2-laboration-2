package org.example.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.example.validation.ValidCategory;

public class ProductDTO {
    public @NotEmpty(message = "Name is required") String name;
    public @ValidCategory String category;
    public @Min(value = 1, message = "Rating cannot be less than 1")
    @Max(value = 5, message = "Rating cannot be more than 5") int rating;

    // Default constructor
    public ProductDTO() {
    }

    // Parameterized constructor
    public ProductDTO(String name, String category, int rating) {
        this.name = name;
        this.category = category;
        this.rating = rating;
    }
}