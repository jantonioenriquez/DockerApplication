package org.example.dataaccess.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.create.CreateUserCommand;
import org.example.domain.entity.User;
import org.example.domain.query.QueryUsersCommand;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDataMapper {

    public User createUserCommandToUser(CreateUserCommand createUserCommand){
        return new User(null,
                createUserCommand.getEmail().toLowerCase(),
                createUserCommand.getName(),
                createUserCommand.getLastNameP(),
                createUserCommand.getLastNameM(),
               null,
                null
        );
    }

    public User queryUserCommandToUser(QueryUsersCommand queryUsersCommand){
        return new User(
                null,
                queryUsersCommand.getEmail(),
                null,
                null,
                null,
                null
             );
    }

}
