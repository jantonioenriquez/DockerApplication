package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.common.application.rest.ApplicationController;
import org.example.domain.create.CreateProductCommand;
import org.example.domain.create.CreateProductResponse;
import org.example.domain.query.QueryProductCommand;
import org.example.domain.query.QueryProductResponse;
import org.example.domain.service.ProductApplicationService;
import org.example.domain.valueObject.DomainConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/products", produces = "application/vnd.api.v1+json")
public class ProductController extends ApplicationController {

    private final ProductApplicationService productApplicationService;

    public ProductController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestHeader("userid") String userId, @RequestBody CreateProductCommand createProductCommand) {
        log.info("Creating product: {}", createProductCommand.getUpcEan());
        CreateProductResponse response = productApplicationService.createProduct(userId, createProductCommand);
        return ResponseEntity.ok(response);
    }



    @GetMapping
    public ResponseEntity<QueryProductResponse> getProducts(@RequestParam(defaultValue = DomainConstants.SIZE_DEFAULT) int size,
                                                            @RequestParam(defaultValue = DomainConstants.PAGE_DEFAULT) int page,
                                                            @RequestParam(defaultValue = DomainConstants.ORDER_STATUS_DEFAULT) String order,
                                                            @RequestParam(defaultValue = "createdAt") String sort,
                                                            @RequestHeader(required = false, value = "userid") String userId
    ) {
        QueryProductCommand queryProductCommand = QueryProductCommand.builder()
                .size(size)
                .page(page)
                .order(order)
                .sort(sort)
                .userId(userId)
                .build();

        QueryProductResponse response = productApplicationService.getProducts(queryProductCommand);
        return ResponseEntity.ok(response);
    }


}
