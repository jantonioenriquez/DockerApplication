package org.example.dataaccess.adapter;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.dataaccess.entity.UserEntity;
import org.example.dataaccess.mapper.UserDataAccessMapper;
import org.example.dataaccess.repository.UserJpaRepository;
import org.example.domain.entity.User;
import org.example.domain.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private  final UserDataAccessMapper userDataAccessMapper;
    private final MongoTemplate mongoTemplate;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, UserDataAccessMapper userDataAccessMapper,
                              MongoTemplate mongoTemplate) {
        this.userJpaRepository = userJpaRepository;
        this.userDataAccessMapper = userDataAccessMapper;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User createUser(User user) {
        var userEntity = userDataAccessMapper.userToUserEntity(user);
        UserEntity createdUser = userJpaRepository.save(userEntity);
        return userDataAccessMapper.userEntityToUser(createdUser);
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        var createdUser = userJpaRepository.findById(userId);
        return createdUser.map(userDataAccessMapper::userEntityToUser);
    }

    @Override
    public Long countUsersCataloging(User filters) {
        Aggregation aggregation = findUsersAggregation(0, 0, "", "", false, filters);
        var result = mongoTemplate.aggregate(aggregation, "users", Map.class).getMappedResults();
        return result.isEmpty() ? 0 : Long.parseLong(result.get(0).get("count").toString());
    }

    @Override
    public List<User> findUsersCataloging(int page, int size, String sort, String order, Boolean isPageable, User filters) {
        Aggregation aggregation = findUsersAggregation(page, size, sort, order, isPageable, filters);
        var result = mongoTemplate.aggregate(aggregation, "users", UserEntity.class);
        return result.getMappedResults().stream().map(userDataAccessMapper::userEntityToUser).collect(Collectors.toList());
    }

    public Aggregation findUsersAggregation(int page, int size, String sort, String order, Boolean isPageable, User filters) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(filters.getEmail())) {
            criteria.and("email").regex(Pattern.quote(filters.getEmail()), "i");
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
