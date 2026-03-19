package org.example.domain.repository;

import org.example.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User createUser(User user);
    Optional<User> findUserByUserId(String userId);
    List<User> findUsersCataloging(int page, int size, String sort, String order, Boolean isPageable, User filters);
    Long countUsersCataloging(User filters);
}
