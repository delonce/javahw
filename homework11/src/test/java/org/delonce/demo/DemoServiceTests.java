package org.delonce.demo;
import org.delonce.demo.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ProductService.class})
public class ProductServiceTests {
    @Autowired
    private ProductService productService;

    @Test
    void getCountProductTest() {
        Long count = productService.countAllProducts();

        Assertions.assertEquals("1", count);
    }
}
