package org.example.dataaccess.adapter;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.example.dataaccess.entity.ProductEntity;
import org.example.dataaccess.mapper.ProductDataAccessMapper;
import org.example.dataaccess.repository.ProductJpaRepository;
import org.example.domain.entity.Product;
import org.example.domain.query.QueryProductCommand;
import org.example.domain.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
   private final ProductJpaRepository productJpaRepository;
    private final ProductDataAccessMapper productDataAccessMapper;
    private final MongoTemplate mongoTemplate;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository, ProductDataAccessMapper productDataAccessMapper,
                                 MongoTemplate mongoTemplate) {
        this.productJpaRepository = productJpaRepository;
        this.productDataAccessMapper = productDataAccessMapper;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        ProductEntity productEntity = productDataAccessMapper.productToProductEntity(product);
        ProductEntity savedProductEntity = productJpaRepository.save(productEntity);
        return productDataAccessMapper.productEntityToProduct(savedProductEntity);
    }

    @Override
    public Long countProducts(QueryProductCommand filters) {
        Aggregation aggregation = findProductsAggregation(filters, false);
        var result = mongoTemplate.aggregate(aggregation, "products", Map.class).getMappedResults();
        return result.isEmpty() ? 0 : Long.parseLong(result.get(0).get("count").toString());
    }

    @Override
    public List<Product> findProducts(QueryProductCommand queryProductCommand, Boolean isPageable ) {
        Aggregation aggregation = findProductsAggregation(queryProductCommand, isPageable);
        var result = mongoTemplate.aggregate(aggregation, "products", ProductEntity.class);
        return result.getMappedResults().stream().map(productDataAccessMapper::productEntityToProduct).collect(Collectors.toList());
    }

    public Aggregation findProductsAggregation(QueryProductCommand filters, Boolean isPageable) {
       var sort = filters.getSort();
       var order = filters.getOrder();
       var page = filters.getPage();
       var size = filters.getSize();

        Criteria criteria = new Criteria();

        if (StringUtils.isNotBlank(filters.getProposalId())) {
            criteria.and("proposalId").is(filters.getProposalId());
        }

        if (StringUtils.isNotBlank(filters.getUpcEan())) {
            criteria.and("upcEan").is(filters.getUpcEan());
        }

        if (StringUtils.isNotBlank(filters.getUserId())) {
            criteria.and("user._id").is(new ObjectId(filters.getUserId()));
        }

        MatchOperation matchOperation = Aggregation.match(criteria);

        if (isPageable) {
            Collation collation = Collation.of("en");
            AggregationOptions options = AggregationOptions.builder().collation(collation).build();

            var sortOperation = sort != null ? order.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Aggregation.sort(Sort.Direction.ASC, sort)
                    : Aggregation.sort(Sort.Direction.DESC, sort) : null;

            long skip = page * size;
            AggregationOperation skipOperation = Aggregation.skip(skip);
            AggregationOperation limitOperation = Aggregation.limit(size);

            return Aggregation.newAggregation(matchOperation, sortOperation, skipOperation, limitOperation).withOptions(options);
        } else {
            GroupOperation countOperation = Aggregation.group().count().as("count");
            return Aggregation.newAggregation(matchOperation, countOperation);

        }
    }
}
