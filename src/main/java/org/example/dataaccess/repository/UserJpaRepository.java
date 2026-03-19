package org.example.dataaccess.repository;

import org.example.dataaccess.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserJpaRepository extends MongoRepository<UserEntity, String> {

}
