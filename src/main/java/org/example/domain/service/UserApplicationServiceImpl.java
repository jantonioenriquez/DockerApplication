package org.example.domain.service;

import org.example.domain.create.CreateUserCommand;
import org.example.domain.create.CreateUserResponse;
import org.example.domain.handler.UserCommandHandler;
import org.example.domain.query.QueryUserResponse;
import org.example.domain.query.QueryUsersCommand;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationServiceImpl implements  UserApplicationService {
    private final UserCommandHandler userCommandHandler;

    public UserApplicationServiceImpl(UserCommandHandler userCommandHandler) {
        this.userCommandHandler = userCommandHandler;
    }

    @Override
    public CreateUserResponse createUser(CreateUserCommand createUserCommand) {
        return userCommandHandler.createUser(createUserCommand);
    }

    @Override
    public QueryUserResponse getUsers(QueryUsersCommand queryUsersCommand) {
        return userCommandHandler.getUsers(queryUsersCommand);
    }
}
