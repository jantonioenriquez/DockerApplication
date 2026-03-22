package org.example.dataaccess.mapper;

import org.example.dataaccess.entity.UserEntity;
import org.example.domain.entity.User;
import org.example.common.application.valueObject.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserDataAccessMapper {

    public User userEntityToUser(UserEntity userEntity) {
        UserId userId = userEntity.getId() != null ? new UserId(userEntity.getId()) : null;

        return new User(
                userId,
                userEntity.getEmail(),
                userEntity.getName(),
                userEntity.getLastNameP(),
                userEntity.getLastNameM(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

    public UserEntity userToUserEntity(User user) {String idUser = user.getId() != null ? user.getId().getValue() : null;
       return UserEntity.builder()
                .id(idUser)
               .email(user.getEmail().toLowerCase())
                .name(user.getName())
                .lastNameP(user.getLastNameP())
                .lastNameM(user.getLastNameM())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}