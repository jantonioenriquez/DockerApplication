package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.example.domain.create.CreateUserCommand;
import org.example.domain.create.CreateUserResponse;
import org.example.domain.query.QueryUserResponse;
import org.example.domain.query.QueryUsersCommand;
import org.example.domain.service.UserApplicationService;
import org.example.domain.valueObject.DomainConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
public class UserController {
    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserCommand createUserCommand) {
        log.debug("Creating user with username: {}", createUserCommand.getEmail());
        CreateUserResponse response = userApplicationService.createUser(createUserCommand);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<QueryUserResponse> getUsers(@RequestParam(defaultValue = DomainConstants.ORDER_STATUS_DEFAULT) String order,
                                                      @RequestParam(defaultValue = DomainConstants.SORT_STATUS_DEFAULT) String sort,
                                                      @RequestParam(defaultValue = DomainConstants.SIZE_DEFAULT) int size,
                                                      @RequestParam(defaultValue = DomainConstants.PAGE_DEFAULT) int page,
                                                      @RequestParam(required = false) String email) {

        log.debug("Getting users with page: {}, size: {} ", page, size);
        QueryUsersCommand queryUsersCommand = QueryUsersCommand.builder()
                .order(order)
                .sort(sort)
                .size(size)
                .page(page)
                .email(email)
                .build();

        QueryUserResponse response = userApplicationService.getUsers(queryUsersCommand);
        return ResponseEntity.ok(response);
    }

}
