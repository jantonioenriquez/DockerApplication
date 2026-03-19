package org.example.domain.handler;

import org.example.dataaccess.mapper.ProductDataMapper;
import org.example.domain.create.CreateProductCommand;
import org.example.domain.create.CreateProductResponse;
import org.example.domain.entity.Product;
import org.example.domain.exception.UserNotFoundException;
import org.example.domain.query.QueryProductCommand;
import org.example.domain.query.QueryProductResponse;
import org.example.domain.repository.ProductRepository;
import org.example.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandHandler {
    private final ProductRepository productRepository;
    private final ProductDataMapper productDataMapper;
    private final UserRepository userRepository;

    public ProductCommandHandler(ProductRepository productRepository, ProductDataMapper productDataMapper, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.productDataMapper = productDataMapper;
        this.userRepository = userRepository;
    }

    public CreateProductResponse createProduct(String userId, CreateProductCommand createProductCommand){
        var user = userRepository.findUserByUserId(userId);

        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }

        Product product = productDataMapper.createProductCommandToProduct(createProductCommand);
        product.setUser(user.get());
        product.setStatus("Activo");

        var createdProduct = productRepository.createProduct(product);

        return new CreateProductResponse(createdProduct.getId().getValue(),"ok");
    }


    public QueryProductResponse getProducts(QueryProductCommand queryProductCommand) {
        long total = productRepository.countProducts(queryProductCommand);
        var productList  = productRepository.findProducts(
                queryProductCommand,
                true
        );

        return new QueryProductResponse(productList, queryProductCommand.getPage(), queryProductCommand.getSize(), total);
    }
}
