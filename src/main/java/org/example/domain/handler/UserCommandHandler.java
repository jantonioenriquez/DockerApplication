package org.example.domain.handler;

import org.example.dataaccess.mapper.UserDataMapper;
import org.example.domain.create.CreateUserCommand;
import org.example.domain.create.CreateUserResponse;
import org.example.domain.entity.User;
import org.example.domain.query.QueryUserResponse;
import org.example.domain.query.QueryUsersCommand;
import org.example.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserCommandHandler {
    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;

    public UserCommandHandler(UserRepository userRepository, UserDataMapper userDataMapper) {
        this.userRepository = userRepository;
        this.userDataMapper = userDataMapper;
    }

    public CreateUserResponse createUser(CreateUserCommand createUserCommand){
        User user = userDataMapper.createUserCommandToUser(createUserCommand);
        var createdUser = userRepository.createUser(user);
        return new CreateUserResponse(createdUser.getId().getValue(),"Ok");
    }

    public QueryUserResponse getUsers(QueryUsersCommand queryUsersCommand) {
       var user = userDataMapper.queryUserCommandToUser(queryUsersCommand);

        long total = userRepository.countUsersCataloging(user);
        var userList = userRepository.findUsersCataloging(
                queryUsersCommand.getPage(),
                queryUsersCommand.getSize(),
                queryUsersCommand.getSort(),
                queryUsersCommand.getOrder(),
                true,
                user
        );
        return new QueryUserResponse(userList, queryUsersCommand.getPage(), queryUsersCommand.getSize(), total);
    }
}

