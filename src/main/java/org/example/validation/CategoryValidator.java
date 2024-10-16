package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.entities.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryValidator.class);

    @Override
    public void initialize(ValidCategory constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            logger.error("Category is null");
            return false;
        }
        try {
            Category.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("Category {} doesn't match existing categories", value);
            return false;
        }
    }
}