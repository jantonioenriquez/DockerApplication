package org.example.dataaccess.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.create.CreateProductCommand;
import org.example.domain.entity.Product;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductDataMapper {

    public Product createProductCommandToProduct(CreateProductCommand dataProduct) {
        return new Product(null, dataProduct.getProposalId(), dataProduct.getUpcEan(), null, null,null);
    }
}
