package org.example.dataaccess.repository;

import org.example.dataaccess.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ProductJpaRepository extends MongoRepository<ProductEntity, String> {

    @Query("{'proposalId': ?0 }")
    Optional<ProductEntity> findByProposalId(String proposalId);

}
