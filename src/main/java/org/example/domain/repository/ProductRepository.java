package org.example.domain.repository;

import org.example.domain.entity.Product;
import org.example.domain.query.QueryProductCommand;

import java.util.List;

public interface ProductRepository {
    Product createProduct(Product product);
    List<Product> findProducts(QueryProductCommand queryProductCommand, Boolean isPageable);
    Long countProducts(QueryProductCommand queryProductCommand);
}
