package org.example.domain.service;

import org.example.domain.create.CreateProductCommand;
import org.example.domain.create.CreateProductResponse;
import org.example.domain.handler.ProductCommandHandler;
import org.example.domain.query.QueryProductCommand;
import org.example.domain.query.QueryProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class ProductApplicationServiceImpl implements ProductApplicationService {
    private  final ProductCommandHandler productCommandHandler;

    public ProductApplicationServiceImpl(ProductCommandHandler productCommandHandler) {
        this.productCommandHandler = productCommandHandler;
    }

    @Override
    public CreateProductResponse createProduct(String userId, CreateProductCommand createProductCommand) {
        return productCommandHandler.createProduct(userId, createProductCommand);
    }

    @Override
    public QueryProductResponse getProducts(QueryProductCommand queryProductCommand) {
        return productCommandHandler.getProducts(queryProductCommand);
    }
}
