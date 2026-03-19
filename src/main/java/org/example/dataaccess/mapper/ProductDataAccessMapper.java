package org.example.dataaccess.mapper;

import org.example.dataaccess.entity.ProductEntity;
import org.example.domain.entity.Product;
import org.example.domain.valueObject.ProductId;
import org.springframework.stereotype.Component;

@Component
public class ProductDataAccessMapper {

    private  final UserDataAccessMapper userDataAccessMapper;

    public ProductDataAccessMapper(UserDataAccessMapper userDataAccessMapper) {
        this.userDataAccessMapper = userDataAccessMapper;
    }


    public Product productEntityToProduct(ProductEntity productEntity) {
        ProductId id = productEntity.getId() != null
                ? new ProductId(productEntity.getId())
                : null;

        var user = userDataAccessMapper.userEntityToUser(productEntity.getUser());
        return new Product(id, productEntity.getProposalId(), productEntity.getUpcEan(),productEntity.getCreatedAt(),
                productEntity.getStatus(), user);
    }

    public ProductEntity productToProductEntity(Product product) {
          String id = product.getId() != null
                ? product.getId().getValue()
                : null;

        var userEntity = userDataAccessMapper.userToUserEntity(product.getUser());

        return ProductEntity.builder()
                .id(id)
                .proposalId(product.getProposalId())
                .upcEan(product.getUpcEan())
                .createdAt(product.getCreatedAt())
                .status(product.getStatus())
                .user(userEntity)
                .build();
    }

}
