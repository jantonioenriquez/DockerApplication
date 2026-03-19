package org.example.domain.service;

import org.example.domain.create.CreateProductCommand;
import org.example.domain.create.CreateProductResponse;
import org.example.domain.query.QueryProductCommand;
import org.example.domain.query.QueryProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public interface ProductApplicationService {
    CreateProductResponse createProduct(String userId, CreateProductCommand createProductCommand);
    QueryProductResponse getProducts(QueryProductCommand queryProductCommand);

}
