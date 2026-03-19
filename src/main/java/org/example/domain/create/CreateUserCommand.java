package org.example.domain.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateUserCommand {
    @NotNull
    @Email
    private final String email;
    private final String name;
    private final String lastNameP;
    private final String lastNameM;
  }
