package org.statsserver.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

class ProductTest {
    private static Validator validator;

    @Test
    public void whenUsingAnnotations_thenOk() throws IOException {
        // Test to check if it's possible to map a JSON string directly into a class instance
        String SOURCE_JSON = """
        {
            "id": "957c43f2-fa2e-42f9-bf75-6e3d5bb6960a",
            "name": "The Best Product",
            "brand": {
                "id": "9bcd817d-0141-42e6-8f04-e5aaab0980b6",
                "name": "ACME Products",
                "owner": {
                    "id": "b21a80b1-0c09-4be3-9ebd-ea3653511c13",
                    "name": "Ultimate Corp, Inc."
                }
            } \s
        }""";

        Product product = new ObjectMapper()
                .readerFor(Product.class)
                .readValue(SOURCE_JSON);

        Assertions.assertEquals(product.getName(), "The Best Product");
        Assertions.assertEquals(product.getBrandName(), "ACME Products");
        Assertions.assertEquals(product.getOwnerName(), "Ultimate Corp, Inc.");
    }

    @Test
    public void validation() {
        Product product = new Product("123abc", null, "Nike", "bla");
        try(ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        Assertions.assertEquals(violations.size(), 1);
    }
}