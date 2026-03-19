package org.example.domain.service;

import jakarta.validation.Valid;
import org.example.domain.create.CreateUserCommand;
import org.example.domain.create.CreateUserResponse;
import org.example.domain.query.QueryUserResponse;
import org.example.domain.query.QueryUsersCommand;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public interface UserApplicationService {
    CreateUserResponse createUser(CreateUserCommand createUserCommand);
    QueryUserResponse getUsers(@Valid QueryUsersCommand queryUsersCommand);
}
