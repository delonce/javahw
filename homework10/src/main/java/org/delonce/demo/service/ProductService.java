package org.delonce.demo.service;

import org.delonce.demo.models.Product;
import org.delonce.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Long countAllProducts() {
        return productRepository.count();
    }

    public Long getAvgSum() {
        Iterable<Product> products = productRepository.findAll();

        Long totalSum = StreamSupport.stream(products.spliterator(), false)
                .mapToLong(Product::getTotal)
                .sum();

        return totalSum / countAllProducts();
    }
}

