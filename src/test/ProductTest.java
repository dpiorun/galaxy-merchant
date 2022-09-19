package test;

import main.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductTest {
    Product product;

    @BeforeEach
    void setup() {
        product = new Product("test");
    }

    @Test
    void itIsPossibleToCreateAProductWithAName() {
        assertEquals(product.getName(), "test");
    }

    @Test
    void itIsPossibleToAddUnitPriceForTheProduct() {
        product.setUnitPrice(1237875192.0);
        assertEquals(product.getUnitPrice(), 1237875192.0);
    }

    @Test
    void itShowsNullUnitPriceForProductWithoutPriceSet() {
        assertNull(product.getUnitPrice());
    }
}